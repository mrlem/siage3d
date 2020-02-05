#version 320 es
precision mediump float;

in vec2 _textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCamera;
in float visibility;

out vec4 outColor;

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

void main(void) {

    // diffuse lighting calc
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float dotProduct = dot(unitNormal, unitLightVector);
    float brightness = max(dotProduct, 0.2); // ambient light
    vec3 diffuse = brightness * lightColor;

    // specular lighting calc
    vec3 finalSpecular = vec3(0.0);
    if (reflectivity > 0.0) {
        vec3 unitToCamera = normalize(toCamera);
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitToCamera);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shineDamper);
        finalSpecular = dampedFactor * reflectivity * lightColor;
    }

    // blending calc
    vec4 blendColor = texture(blendMap, _textureCoords);
    float backgroundAmount = 1.0 - blendColor.r - blendColor.g - blendColor.b;

    vec2 tiledCoords = _textureCoords * tileSize;
    vec4 backgroundColor = texture(backgroundTexture, tiledCoords) * backgroundAmount;
    vec4 rColor = texture(rTexture, tiledCoords) * blendColor.r;
    vec4 gColor = texture(gTexture, tiledCoords) * blendColor.g;
    vec4 bColor = texture(bTexture, tiledCoords) * blendColor.b;
    vec4 totalColor = backgroundColor + rColor + gColor + bColor;

    outColor = (vec4(diffuse, 1.0) * totalColor + vec4(finalSpecular, 1.0));
    outColor = mix(vec4(fogColor, 1.0), outColor, visibility);
}