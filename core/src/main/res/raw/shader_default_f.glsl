#version 320 es
precision mediump float;

const float ambientLight = 0.2;

// uniforms

uniform sampler2D textureSampler;
uniform float tileSize;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 fogColor;

// attributes

in vec2 _textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCamera;
in float visibility;

out vec4 outColor;

// protos

vec4 calcDiffuseLight(vec3 unitNormal, vec3 unitLightVector);
vec4 calcSpecularLight(vec3 unitNormal, vec3 unitLightVector);
vec4 getTextureColor();

// main

void main(void) {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    outColor += calcDiffuseLight(unitNormal, unitLightVector);  // diffuse
    outColor += calcSpecularLight(unitNormal, unitLightVector); // specular
    outColor = mix(vec4(fogColor, 1.0), outColor, visibility);  // fog
}

// functions

vec4 calcDiffuseLight(vec3 unitNormal, vec3 unitLightVector) {
    float dotProduct = dot(unitNormal, unitLightVector);
    float brightness = max(dotProduct, ambientLight);           // ambient light

    return vec4(brightness * lightColor, 1.0) * getTextureColor();
}

vec4 calcSpecularLight(vec3 unitNormal, vec3 unitLightVector) {
    vec3 specular = vec3(0.0);
    if (reflectivity > 0.0) {
        vec3 unitToCamera = normalize(toCamera);
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitToCamera);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shineDamper);
        specular = dampedFactor * reflectivity * lightColor;
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
