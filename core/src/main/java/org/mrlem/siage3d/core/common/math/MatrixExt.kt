package org.mrlem.siage3d.core.common.math

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.Camera
import org.mrlem.siage3d.core.scene.position

fun Matrix4f.fromCamera(camera: Camera): Matrix4f = identity()
    .rotateXYZ(
        Math.toRadians(camera.pitch.toDouble()).toFloat(),
        Math.toRadians(camera.yaw.toDouble()).toFloat(),
        Math.toRadians(camera.roll.toDouble()).toFloat()
    )
    .translate(camera.position().negate())

fun Matrix4f.directionUp(direction: Vector3f = Vector3f()): Vector3f = direction.set(m10(), m11(), m12())
