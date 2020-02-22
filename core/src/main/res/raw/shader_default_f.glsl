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

struct Material {
    sampler2D diffuse;
    float ambient;
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

vec4 calcDirectionLight(DirectionLight light, vec3 normal, vec3 viewDir);
vec4 calcPointLight(PointLight light, vec3 normal, vec3 viewDir);
vec4 calcLight(LightColor color, vec3 normal, vec3 viewDir, vec3 lightDir);
vec4 getTextureColor();

// main

void main(void) {
    vec3 normal = normalize(_surfaceNormal);
    vec3 viewDir = normalize(_toCamera);

    outColor += calcDirectionLight(directionLight, normal, viewDir);    // direction light
    for (int i=0 ; i<MAX_LIGHTS ; i++) {                                // point lights
        outColor += calcPointLight(pointLights[i], normal, viewDir);
    }
    outColor = mix(vec4(fog.color, 1.0), outColor, _visibility);        // fog
}

// functions

vec4 calcDirectionLight(DirectionLight light, vec3 normal, vec3 viewDir) {
    vec3 lightDir = normalize(-light.direction);
    return calcLight(light.color, normal, viewDir, lightDir);
}

vec4 calcPointLight(PointLight light, vec3 normal, vec3 viewDir) {
    vec3 lightDir = normalize(light.position - _worldPosition.xyz);
    return calcLight(light.color, normal, viewDir, lightDir);
}

vec4 calcLight(LightColor color, vec3 normal, vec3 viewDir, vec3 lightDir) {
    // ambient
    vec4 result = vec4(color.ambient, 1.0) * material.ambient * getTextureColor();

    // diffuse
    float brightness = dot(normal, lightDir);
    result += vec4(brightness * color.diffuse, 1.0) * getTextureColor();

    // specular
    vec3 specular = vec3(0.0);
    if (material.reflectivity > 0.0) {
        vec3 lightDirection = -lightDir;
        vec3 reflectedLightDirection = reflect(lightDirection, normal);

        float specularFactor = dot(reflectedLightDirection, viewDir);
        specularFactor = max(specularFactor, 0.0);
        specularFactor = pow(specularFactor, material.shineDamper);     // damped factor

        specular = specularFactor * material.reflectivity * color.diffuse;
    }
    result += vec4(specular, 1.0);

    return result;
}

vec4 getTextureColor() {
    // texture color
    vec2 tiledCoords = _textureCoords / material.scale;
    vec4 textureColor = texture(material.diffuse, tiledCoords);

    // handle transparency
    if (textureColor.a < 0.5) {
        discard;
    }

    return textureColor;
}
