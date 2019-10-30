#version 320 es
precision mediump float;

in vec2 _textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCamera;
in float visibility;

out vec4 outColor;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

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
        vec3 finalSpecular = dampedFactor * reflectivity * lightColor;
    }

    // transparency calc
    vec4 textureColor = texture(textureSampler, _textureCoords);
    if (textureColor.a < 0.5) {
        discard;
    }

    outColor = (vec4(diffuse, 1.0) * textureColor + vec4(finalSpecular, 1.0));
    outColor = mix(vec4(skyColor, 1.0), outColor, visibility);
}