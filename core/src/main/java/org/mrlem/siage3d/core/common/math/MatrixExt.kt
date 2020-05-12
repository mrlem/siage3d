package org.mrlem.siage3d.core.common.math

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.graph.nodes.cameras.CameraNode

fun Matrix4f.fromCamera(camera: CameraNode): Matrix4f = identity()
    .rotateXYZ(
        Math.toRadians(camera.pitch.toDouble()).toFloat(),
        Math.toRadians(camera.yaw.toDouble()).toFloat(),
        Math.toRadians(camera.roll.toDouble()).toFloat()
    )
    .translate(camera.position().negate())

fun Matrix4f.directionUp(direction: Vector3f = Vector3f()): Vector3f = direction.set(m10(), m11(), m12())
