package org.mrlem.siage3d.core.common.math

import org.joml.Matrix4f
import org.mrlem.siage3d.core.scene.Camera
import org.mrlem.siage3d.core.scene.dsl.position

fun Matrix4f.fromCamera(camera: Camera) = identity()
    .rotateXYZ(
        Math.toRadians(camera.pitch.toDouble()).toFloat(),
        Math.toRadians(camera.yaw.toDouble()).toFloat(),
        Math.toRadians(camera.roll.toDouble()).toFloat()
    )
    .translate(camera.position().negate())
