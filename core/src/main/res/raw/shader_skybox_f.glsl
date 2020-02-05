#version 320 es
precision mediump float;

in vec3 textureCoords;

out vec4 outColor;

uniform samplerCube skybox;
uniform vec3 fogColor;

const float lowerLimit = 0.0;
const float upperLimit = 0.2;

void main()
{
    vec4 skyColor = texture(skybox, textureCoords);

    float factor = (textureCoords.y - lowerLimit) / (upperLimit - lowerLimit);
    factor = clamp(factor, 0.0, 1.0);
    outColor = mix(vec4(fogColor, 1.0), skyColor, factor);
}
