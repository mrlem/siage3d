package org.mrlem.k3d.core.common.math

import org.joml.Matrix4f
import org.mrlem.k3d.core.scene.Camera

fun Matrix4f.fromCamera(camera: Camera) = identity()
    .rotate(Math.toRadians(camera.pitch.toDouble()).toFloat(), 1f, 0f, 0f)
    .rotate(Math.toRadians(camera.yaw.toDouble()).toFloat(), 0f, 1f, 0f)
    .rotate(Math.toRadians(camera.roll.toDouble()).toFloat(), 0f, 0f, 1f)
    .translate(-camera.position.x, -camera.position.y, -camera.position.z)
