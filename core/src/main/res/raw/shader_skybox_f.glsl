#version 320 es
precision mediump float;

in vec3 textureCoords;

out vec4 outColor;

uniform samplerCube skybox;

void main()
{
    outColor = texture(skybox, textureCoords);
}
