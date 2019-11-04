package org.mrlem.k3d.core.common.math

import org.joml.Matrix4f
import org.mrlem.k3d.core.scene.Camera

fun Matrix4f.fromCamera(camera: Camera) = identity()
    .rotateXYZ(
        Math.toRadians(camera.pitch.toDouble()).toFloat(),
        Math.toRadians(camera.yaw.toDouble()).toFloat(),
        Math.toRadians(camera.roll.toDouble()).toFloat()
    )
    .translate(-camera.position.x, -camera.position.y, -camera.position.z)
