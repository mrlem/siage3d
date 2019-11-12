#version 320 es

in vec3 position;

out vec3 textureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {

    textureCoords = position;
    gl_Position = projectionMatrix * viewMatrix * vec4(position, 1.0);

}
