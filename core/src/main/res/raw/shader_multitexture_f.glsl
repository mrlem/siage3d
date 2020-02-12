#version 320 es
precision highp float;

// types

struct PointLight {
    vec3 position;
    vec3 ambient;
    vec3 diffuse;
};

struct BlendMap {
    sampler2D blendMap;
    sampler2D backgroundTexture;
    sampler2D rTexture;
    sampler2D gTexture;
    sampler2D bTexture;
};

struct Material {
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
#define MAX_LIGHTS 3
uniform PointLight lights[MAX_LIGHTS];
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

vec4 calcPointLight(PointLight light, vec3 unitNormal);
vec4 getTextureColor();

// main

void main(void) {
    vec3 unitNormal = normalize(_surfaceNormal);

    for (int i=0 ; i<MAX_LIGHTS ; i++) {                                    // point lights
        outColor += calcPointLight(lights[i], unitNormal);
    }
    outColor = mix(vec4(fog.color, 1.0), outColor, _visibility);            // fog
}

// functions

vec4 calcPointLight(PointLight light, vec3 unitNormal) {
    vec3 unitLightVector = normalize(light.position - _worldPosition.xyz);

    // ambient
    vec4 result = vec4(light.ambient, 1.0) * getTextureColor();

    // diffuse
    float brightness = dot(unitNormal, unitLightVector);
    result += vec4(brightness * light.diffuse, 1.0) * getTextureColor();

    // specular
    vec3 specular = vec3(0.0);
    if (material.reflectivity > 0.0) {
        vec3 unitToCamera = normalize(_toCamera);
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

        float specularFactor = dot(reflectedLightDirection, unitToCamera);
        specularFactor = max(specularFactor, 0.0);
        specularFactor = pow(specularFactor, material.shineDamper);      // damped factor

        specular = specularFactor * material.reflectivity * light.diffuse;
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
