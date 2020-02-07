#version 320 es
precision mediump float;

const float ambientLight = 0.2f;

// uniforms

uniform sampler2D blendMap;
uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
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
    vec4 blendColor = texture(blendMap, _textureCoords);
    float backgroundAmount = 1.0 - blendColor.r - blendColor.g - blendColor.b;

    vec2 tiledCoords = _textureCoords * tileSize;
    vec4 backgroundColor = texture(backgroundTexture, tiledCoords) * backgroundAmount;
    vec4 rColor = texture(rTexture, tiledCoords) * blendColor.r;
    vec4 gColor = texture(gTexture, tiledCoords) * blendColor.g;
    vec4 bColor = texture(bTexture, tiledCoords) * blendColor.b;
    return backgroundColor + rColor + gColor + bColor;
}
