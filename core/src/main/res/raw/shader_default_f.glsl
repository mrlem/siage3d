#version 320 es
precision mediump float;

const float ambientLight = 0.2;

// types

struct PointLight {
    vec3 color;
    vec3 position;
};

struct Material {
    float shineDamper;
    float reflectivity;
};

// uniforms

uniform sampler2D textureSampler;
uniform float tileSize;
uniform PointLight light;
uniform Material material;
uniform vec3 fogColor;

// attributes

in vec3 _worldPosition;
in vec2 _textureCoords;
in vec3 _surfaceNormal;
in vec3 _toCamera;
in float _visibility;

out vec4 outColor;

// protos

vec4 calcDiffuseLight(vec3 unitNormal, vec3 unitLightVector);
vec4 calcSpecularLight(vec3 unitNormal, vec3 unitLightVector);
vec4 getTextureColor();

// main

void main(void) {
    vec3 unitNormal = normalize(_surfaceNormal);
    vec3 unitLightVector = normalize(light.position - _worldPosition.xyz);

    outColor += calcDiffuseLight(unitNormal, unitLightVector);  // diffuse
    outColor += calcSpecularLight(unitNormal, unitLightVector); // specular
    outColor = mix(vec4(fogColor, 1.0), outColor, _visibility);  // fog
}

// functions

vec4 calcDiffuseLight(vec3 unitNormal, vec3 unitLightVector) {
    float dotProduct = dot(unitNormal, unitLightVector);
    float brightness = max(dotProduct, ambientLight);           // ambient light

    return vec4(brightness * light.color, 1.0) * getTextureColor();
}

vec4 calcSpecularLight(vec3 unitNormal, vec3 unitLightVector) {
    vec3 specular = vec3(0.0);
    if (material.reflectivity > 0.0) {
        vec3 unitToCamera = normalize(_toCamera);
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

        float specularFactor = dot(reflectedLightDirection, unitToCamera);
        specularFactor = max(specularFactor, 0.0);
        specularFactor = pow(specularFactor, material.shineDamper);      // damped factor

        specular = specularFactor * material.reflectivity * light.color;
    }

    return vec4(specular, 1.0);
}

vec4 getTextureColor() {
    // texture color
    vec2 tiledCoords = _textureCoords * tileSize;
    vec4 textureColor = texture(textureSampler, tiledCoords);

    // handle transparency
    if (textureColor.a < 0.5) {
        discard;
    }

    return textureColor;
}
