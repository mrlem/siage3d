package org.mrlem.siage3d.core.render

import org.joml.Matrix4f
import org.mrlem.siage3d.core.scene.Scene
import org.mrlem.siage3d.core.scene.lights.DirectionLight
import org.mrlem.siage3d.core.scene.shaders.Shader

// TODO - critical - check what gets rendered + use shadow map in main scene renderer
// TODO - optional - multiple shadow sources

class ShadowSceneRenderer(scene: Scene) : SceneRenderer(scene) {

    private val lightViewMatrix = Matrix4f()
    private val shadowBox = ShadowBox(lightViewMatrix)
	private val projectionMatrix = Matrix4f()
	private val projectionViewMatrix = Matrix4f()
	private val toDepthMapSpaceMatrix = Matrix4f()

    override fun render() {
		// update shadowBox
		shadowBox.update(scene.camera)

		// update light view matrix
		val directionLight = scene.lights
			.filterIsInstance(DirectionLight::class.java)
			.firstOrNull() ?: return

		lightViewMatrix
			.set(directionLight.localTransform)
			.translateLocal(shadowBox.center.negate())

		// render to shadow-map
        directionLight.shadowMap.use {
			// use depth-only shader
            Shader.depthMapShader.use()

			// apply camera projection
			updateOrthoProjectionMatrix(shadowBox.width, shadowBox.height, shadowBox.length)
			projectionViewMatrix
				.set(projectionMatrix)
				.mul(lightViewMatrix)
			Shader.depthMapShader.loadProjectionViewMatrix(projectionViewMatrix)

			// render objects (material are ignored here)
            gatherObjects(scene, mutableListOf()).also { objects ->
                objects.forEach { it.render(Shader.depthMapShader) }
            }
        }
    }

	/**
	 * Creates the orthographic projection matrix. This projection matrix
	 * basically sets the width, length and height of the "view cuboid", based
	 * on the values that were calculated in the [ShadowBox] class.
	 *
	 * @param width shadow box width.
	 * @param height shadow box height.
	 * @param length shadow box length.
	 */
	private fun updateOrthoProjectionMatrix(
		width: Float,
		height: Float,
		length: Float
	) {
		projectionMatrix
			.identity()
			.m00(2f / width)
			.m11(2f / height)
			.m22(-2f / length)
			.m33(1f)
	}

	private fun updateToDepthMapSpaceMatrix() {
		toDepthMapSpaceMatrix
			.set(offset)
			.mul(projectionMatrix)
	}

	companion object {

		private val offset = Matrix4f().apply {
			translate(0.5f, 0.5f, 0.5f)
			scale(0.5f)
		}

	}

}
