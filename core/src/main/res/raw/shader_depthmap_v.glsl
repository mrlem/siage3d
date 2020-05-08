#version 320 es
precision highp float;

// uniforms

uniform mat4 projectionViewMatrix;
uniform mat4 transformationMatrix;

// attributes

in vec3 position;

// main

void main(void) {
    gl_Position = projectionViewMatrix * transformationMatrix * vec4(position, 1.0);
}
