package org.mrlem.siage3d.core.scene.graph.resources.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.siage3d.core.common.gl.Program
import org.mrlem.siage3d.core.common.io.caches.AbstractCache.Ref
import org.mrlem.siage3d.core.common.io.caches.ProgramCache
import org.mrlem.siage3d.core.scene.graph.nodes.lights.DirectionLightNode
import org.mrlem.siage3d.core.scene.graph.nodes.lights.PointLightNode

abstract class Shader(
    vertexSourceResId: Int,
    fragmentSourceResId: Int,
    attributes: List<Program.AttributeDefinition>,
    uniforms: List<Program.UniformDefinition>
) : Comparable<Shader> {

    protected var programRef: Ref<Program> = ProgramCache.ref(vertexSourceResId, fragmentSourceResId, attributes, uniforms)

    override fun compareTo(other: Shader) = programRef.value.id.compareTo(other.programRef.value.id)

    fun use() {
        programRef.value.use()
    }

    interface ProjectionAware {
        fun loadProjectionMatrix(matrix: Matrix4f)
    }

    interface ViewAware {
        fun loadViewMatrix(matrix: Matrix4f)
    }

    interface LightAware {
        fun loadPointLight(light: PointLightNode, index: Int)
        fun loadDirectionLight(light: DirectionLightNode)
    }

    interface FogAware {
        fun loadFogColor(color: Vector3f)
        fun loadFogGradient(gradient: Float)
        fun loadFogDensity(density: Float)
    }

    interface TransformationAware {
        fun loadTransformationMatrix(matrix: Matrix4f)
    }

    companion object {

        val shaders by lazy { listOf<Shader>(
            textureShader,
            multiTextureShader,
            cubemapTextureShader
        ) }

        val textureShader by lazy { TextureShader() }
        val multiTextureShader by lazy { MultiTextureShader() }
        val cubemapTextureShader by lazy { CubemapTextureShader() }

        ///////////////////////////////////////////////////////////////////////////
        // Shader notification methods
        ///////////////////////////////////////////////////////////////////////////

        fun notifyProjectionMatrix(matrix: Matrix4f) {
            shaders.forEach {
                if (it is ProjectionAware) {
                    it.use()
                    it.loadProjectionMatrix(matrix)
                }
            }
        }

        fun notifyViewMatrix(matrix: Matrix4f) {
            shaders.forEach {
                if (it is ViewAware) {
                    it.use()
                    it.loadViewMatrix(matrix)
                }
            }
        }

        fun notifyPointLight(light: PointLightNode, index: Int) {
            shaders.forEach {
                if (it is LightAware) {
                    it.use()
                    it.loadPointLight(light, index)
                }
            }
        }

        fun notifyDirectionLight(light: DirectionLightNode) {
            shaders.forEach {
                if (it is LightAware) {
                    it.use()
                    it.loadDirectionLight(light)
                }
            }
        }

        fun notifyFog(color: Vector3f, gradient: Float, density: Float) {
            shaders.forEach {
                if (it is FogAware) {
                    it.use()
                    it.loadFogColor(color)
                    it.loadFogGradient(gradient)
                    it.loadFogDensity(density)
                }
            }

        }
    }

}
