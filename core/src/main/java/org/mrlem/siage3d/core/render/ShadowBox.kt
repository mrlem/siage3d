package org.mrlem.siage3d.core.render

import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.mrlem.siage3d.core.scene.Camera
import org.mrlem.siage3d.core.scene.position
import kotlin.math.tan

/**
 * Represents the 3D cuboidal area of the world in which objects will cast
 * shadows (basically represents the orthographic projection area for the shadow
 * render pass). It is updated each frame to optimise the area, making it as
 * small as possible (to allow for optimal shadow map resolution) while not
 * being too small to avoid objects not having shadows when they should.
 * Everything inside the cuboidal area represented by this object will be
 * rendered to the shadow map in the shadow render pass. Everything outside the
 * area won't be.
 *
 * @property lightViewMatrix basically the "view matrix" of the light. Can be used to transform a
 * point from world space into "light" space (i.e. changes a point's coordinates from being in
 * relation to the world's axis to being in terms of the light's local axis).
 *
 * @see xxx link to ThinMatrix video FIXME
 */
class ShadowBox(private val lightViewMatrix: Matrix4f) {

    private var minX = 0f
    private var maxX = 0f
    private var minY = 0f
    private var maxY = 0f
    private var minZ = 0f
    private var maxZ = 0f
    private var farHeight = 0f
    private var farWidth = 0f
    private var nearHeight = 0f
    private var nearWidth = 0f
    private var previousCamera: Camera? = null

    /**
     * Updates the bounds of the shadow box based on the light direction and the
     * camera's view frustum, to make sure that the box covers the smallest area
     * possible while still ensuring that everything inside the camera's view
     * (within a certain range) will cast shadows.
     */
    fun update(camera: Camera) {
        if (camera != this.previousCamera) {
            previousCamera = camera
            calculateWidthsAndHeights(camera)
        }

        val rotation: Matrix4f = calculateCameraRotationMatrix(camera)
        val forwardVector = rotation.transform(FORWARD, Vector4f())
            .let { Vector3f(it.x, it.y, it.z) }
        val toFar = Vector3f(forwardVector)
            .also { it.normalize(SHADOW_DISTANCE) }
        val toNear = Vector3f(forwardVector)
            .also { it.normalize(camera.near) }
        val centerNear: Vector3f = toNear.add(camera.position(), Vector3f())
        val centerFar: Vector3f = toFar.add(camera.position(), Vector3f())
        val points = calculateFrustumVertices(
            rotation, forwardVector, centerNear,
            centerFar
        )
        var first = true
        for (point in points) {
            if (first) {
                minX = point.x
                maxX = point.x
                minY = point.y
                maxY = point.y
                minZ = point.z
                maxZ = point.z
                first = false
                continue
            }
            if (point.x > maxX) {
                maxX = point.x
            } else if (point.x < minX) {
                minX = point.x
            }
            if (point.y > maxY) {
                maxY = point.y
            } else if (point.y < minY) {
                minY = point.y
            }
            if (point.z > maxZ) {
                maxZ = point.z
            } else if (point.z < minZ) {
                minZ = point.z
            }
        }
        maxZ += OFFSET
    }

    /**
     * Calculates the center of the "view cuboid" in light space first, and then
     * converts this to world space using the inverse light's view matrix.
     *
     * @return The center of the "view cuboid" in world space.
     */
    val center: Vector3f
        get() {
            val x = (minX + maxX) / 2f
            val y = (minY + maxY) / 2f
            val z = (minZ + maxZ) / 2f
            val cen = Vector4f(x, y, z, 1f)
            val invertedLight = lightViewMatrix.invert()
            return invertedLight.transform(cen, Vector4f())
                .let { Vector3f(it.x, it.y, it.z) }
        }

    /**
     * @return The width of the "view cuboid" (orthographic projection area).
     */
    val width: Float
        get() = maxX - minX

    /**
     * @return The height of the "view cuboid" (orthographic projection area).
     */
    val height: Float
        get() = maxY - minY

    /**
     * @return The length of the "view cuboid" (orthographic projection area).
     */
    val length: Float
        get() = maxZ - minZ

    /**
     * Calculates the position of the vertex at each corner of the view frustum
     * in light space (8 vertices in total, so this returns 8 positions).
     *
     * @param rotation
     * - camera's rotation.
     * @param forwardVector
     * - the direction that the camera is aiming, and thus the
     * direction of the frustum.
     * @param centerNear
     * - the center point of the frustum's near plane.
     * @param centerFar
     * - the center point of the frustum's (possibly adjusted) far
     * plane.
     * @return The positions of the vertices of the frustum in light space.
     */
    private fun calculateFrustumVertices(
        rotation: Matrix4f, forwardVector: Vector3f,
        centerNear: Vector3f, centerFar: Vector3f
    ): Array<Vector4f> {
        val upVector = rotation.transform(UP, Vector4f())
            .let { Vector3f(it.x, it.y, it.z) }
        val rightVector = forwardVector.cross(upVector, Vector3f())
        val downVector = Vector3f(-upVector.x, -upVector.y, -upVector.z)
        val leftVector = Vector3f(-rightVector.x, -rightVector.y, -rightVector.z)
        val farTop: Vector3f = centerFar.add(
            Vector3f(
                upVector.x * farHeight,
                upVector.y * farHeight, upVector.z * farHeight
            ),
            Vector3f()
        )
        val farBottom: Vector3f = centerFar.add(
            Vector3f(
                downVector.x * farHeight,
                downVector.y * farHeight, downVector.z * farHeight
            ),
            Vector3f()
        )
        val nearTop: Vector3f = centerNear.add(
            Vector3f(
                upVector.x * nearHeight,
                upVector.y * nearHeight, upVector.z * nearHeight
            ),
            Vector3f()
        )
        val nearBottom: Vector3f = centerNear.add(
            Vector3f(
                downVector.x * nearHeight,
                downVector.y * nearHeight, downVector.z * nearHeight
            ),
            Vector3f()
        )
        return arrayOf(
            calculateLightSpaceFrustumCorner(farTop, rightVector, farWidth),
            calculateLightSpaceFrustumCorner(farTop, leftVector, farWidth),
            calculateLightSpaceFrustumCorner(farBottom, rightVector, farWidth),
            calculateLightSpaceFrustumCorner(farBottom, leftVector, farWidth),
            calculateLightSpaceFrustumCorner(nearTop, rightVector, nearWidth),
            calculateLightSpaceFrustumCorner(nearTop, leftVector, nearWidth),
            calculateLightSpaceFrustumCorner(nearBottom, rightVector, nearWidth),
            calculateLightSpaceFrustumCorner(nearBottom, leftVector, nearWidth)
        )
    }

    /**
     * Calculates one of the corner vertices of the view frustum in world space
     * and converts it to light space.
     *
     * @param startPoint
     * - the starting center point on the view frustum.
     * @param direction
     * - the direction of the corner from the start point.
     * @param width
     * - the distance of the corner from the start point.
     * @return - The relevant corner vertex of the view frustum in light space.
     */
    private fun calculateLightSpaceFrustumCorner(
        startPoint: Vector3f, direction: Vector3f,
        width: Float
    ): Vector4f {
        val point: Vector3f = startPoint.add(
            Vector3f(direction.x * width, direction.y * width, direction.z * width),
            Vector3f()
        )
        val point4f = Vector4f(point.x, point.y, point.z, 1f)
        lightViewMatrix.transform(point4f)
        return point4f
    }

    /**
     * @return The rotation of the camera represented as a matrix.
     */
    private fun calculateCameraRotationMatrix(camera: Camera): Matrix4f {
        val rotation = Matrix4f()
            .rotate(Math.toRadians(-camera.yaw.toDouble()).toFloat(), Vector3f(0f, 1f, 0f))
        rotation.rotate(
            Math.toRadians(-camera.pitch.toDouble()).toFloat(),
            Vector3f(1f, 0f, 0f)
        )
        return rotation
    }

    /**
     * Calculates the width and height of the near and far planes of the
     * camera's view frustum. However, this doesn't have to use the "actual" far
     * plane of the view frustum. It can use a shortened view frustum if desired
     * by bringing the far-plane closer, which would increase shadow resolution
     * but means that distant objects wouldn't cast shadows.
     */
    private fun calculateWidthsAndHeights(camera: Camera) {
        farWidth = (SHADOW_DISTANCE * tan(
            Math.toRadians(camera.fov)
        )).toFloat()
        nearWidth = (camera.near * tan(Math.toRadians(camera.fov))).toFloat()
        farHeight = farWidth / camera.aspectRatio
        nearHeight = nearWidth / camera.aspectRatio
    }

    companion object {
        private const val OFFSET = 10f
        private val UP = Vector4f(0f, 1f, 0f, 0f)
        private val FORWARD = Vector4f(0f, 0f, (-1).toFloat(), 0f)
        private const val SHADOW_DISTANCE = 100f
    }
}