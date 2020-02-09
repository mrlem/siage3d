#version 320 es

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec3 _worldPosition;
out vec2 _textureCoords;
out vec3 _surfaceNormal;
out vec3 _toCamera;
out float _visibility;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;
uniform float fogDensity;
uniform float fogGradient;
uniform float useFakeLighting;

void main(void) {

    // position
    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
    _worldPosition = vec3(worldPosition);

    vec4 positionRelativeToCamera = viewMatrix * worldPosition;
    gl_Position = projectionMatrix * positionRelativeToCamera;

    // texture coords
    _textureCoords = textureCoords;

    // normal
    vec3 actualNormal = normal;
    if (useFakeLighting > 0.5) {
        actualNormal = vec3(0.0, 1.0, 0.0);
    }
    _surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;
    _toCamera = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;

    // visibility: based on distance/fog
    float distance = length(positionRelativeToCamera.xyz);
    _visibility = exp(-pow(distance * fogDensity, fogGradient));
    _visibility = clamp(_visibility, 0.0, 1.0);
}
