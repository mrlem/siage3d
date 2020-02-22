#version 320 es
precision highp float;

// types

struct LightColor {
    vec3 ambient;
    vec3 diffuse;
};

struct DirectionLight {
    vec3 direction;
    LightColor color;
};

struct PointLight {
    vec3 position;
    LightColor color;
};

struct BlendMap {
    sampler2D blendMap;
    sampler2D backgroundTexture;
    sampler2D rTexture;
    sampler2D gTexture;
    sampler2D bTexture;
};

struct Material {
    float ambient;
    BlendMap diffuse;
    float reflectivity;
    float shineDamper;
    float scale;
};

struct Fog {
    vec3 color;
    float density;
    float gradient;
};

// uniforms
uniform DirectionLight directionLight;
#define MAX_LIGHTS 3
uniform PointLight pointLights[MAX_LIGHTS];
uniform Material material;
uniform Fog fog;

// attributes

in vec3 _worldPosition;
in vec2 _textureCoords;
in vec3 _surfaceNormal;
in vec3 _toCamera;
in float _visibility;

out vec4 outColor;

// protos

vec4 calcDirectionLight(DirectionLight light, vec3 unitNormal, vec3 unitToCamera);
vec4 calcPointLight(PointLight light, vec3 unitNormal, vec3 unitToCamera);
vec4 calcLight(LightColor color, vec3 unitNormal, vec3 unitToCamera, vec3 unitLight);
vec4 getTextureColor();

// main

void main(void) {
    vec3 unitNormal = normalize(_surfaceNormal);
    vec3 unitToCamera = normalize(_toCamera);

    outColor += calcDirectionLight(directionLight, unitNormal, unitToCamera);   // direction light
    for (int i=0 ; i<MAX_LIGHTS ; i++) {                                        // point lights
        outColor += calcPointLight(pointLights[i], unitNormal, unitToCamera);
    }
    outColor = mix(vec4(fog.color, 1.0), outColor, _visibility);                // fog
}

// functions

vec4 calcDirectionLight(DirectionLight light, vec3 unitNormal, vec3 unitToCamera) {
    vec3 unitLight = normalize(-light.direction);
    return calcLight(light.color, unitNormal, unitToCamera, unitLight);
}

vec4 calcPointLight(PointLight light, vec3 unitNormal, vec3 unitToCamera) {
    vec3 unitLight = normalize(light.position - _worldPosition.xyz);
    return calcLight(light.color, unitNormal, unitToCamera, unitLight);
}

vec4 calcLight(LightColor color, vec3 unitNormal, vec3 unitToCamera, vec3 unitLight) {
    // ambient
    vec4 result = vec4(color.ambient, 1.0) * material.ambient * getTextureColor();

    // diffuse
    float brightness = dot(unitNormal, unitLight);
    result += vec4(brightness * color.diffuse, 1.0) * getTextureColor();

    // specular
    vec3 specular = vec3(0.0);
    if (material.reflectivity > 0.0) {
        vec3 lightDirection = -unitLight;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

        float specularFactor = dot(reflectedLightDirection, unitToCamera);
        specularFactor = max(specularFactor, 0.0);
        specularFactor = pow(specularFactor, material.shineDamper);      // damped factor

        specular = specularFactor * material.reflectivity * color.diffuse;
    }
    result += vec4(specular, 1.0);

    return result;
}

vec4 getTextureColor() {
    vec4 blendColor = texture(material.diffuse.blendMap, _textureCoords);
    float backgroundAmount = 1.0 - blendColor.r - blendColor.g - blendColor.b;

    vec2 tiledCoords = _textureCoords / material.scale;
    vec4 backgroundColor = texture(material.diffuse.backgroundTexture, tiledCoords) * backgroundAmount;
    vec4 rColor = texture(material.diffuse.rTexture, tiledCoords) * blendColor.r;
    vec4 gColor = texture(material.diffuse.gTexture, tiledCoords) * blendColor.g;
    vec4 bColor = texture(material.diffuse.bTexture, tiledCoords) * blendColor.b;
    return backgroundColor + rColor + gColor + bColor;
}
