package org.mrlem.siage3d.core.common.math

import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.graph.nodes.cameras.CameraNode

private val rotation = Quaternionf()
private val inverseTranslation = Vector3f()

fun Matrix4f.fromCamera(camera: CameraNode): Matrix4f = identity()
    .rotation(camera.localTransform.getUnnormalizedRotation(rotation))
    .translate(camera.translation.negate(inverseTranslation))

fun Matrix4f.directionUp(direction: Vector3f = Vector3f()): Vector3f = direction.set(m10(), m11(), m12())
