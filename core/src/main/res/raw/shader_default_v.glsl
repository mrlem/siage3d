#version 320 es

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 _textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCamera;
out float visibility;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;
uniform vec3 lightPosition;
uniform float useFakeLighting;

uniform float fogDensity;
uniform float fogGradient;

void main(void) {

    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
    vec4 positionRelativeToCamera = viewMatrix * worldPosition;

    gl_Position = projectionMatrix * positionRelativeToCamera;
    _textureCoords = textureCoords;

    vec3 actualNormal = normal;
    if (useFakeLighting > 0.5) {
        actualNormal = vec3(0.0, 1.0, 0.0);
    }

    surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;
    toLightVector = lightPosition - worldPosition.xyz;
    toCamera = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;

    float distance = length(positionRelativeToCamera.xyz);
    visibility = exp(-pow(distance * fogDensity, fogGradient));
    visibility = clamp(visibility, 0.0, 1.0);
}