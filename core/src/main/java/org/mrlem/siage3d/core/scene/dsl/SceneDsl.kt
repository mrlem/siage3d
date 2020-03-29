package org.mrlem.siage3d.core.scene.dsl

import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import org.joml.Vector3f
import org.mrlem.k3d.core.R
import org.mrlem.siage3d.core.common.io.AssetManager.texture2D
import org.mrlem.siage3d.core.common.io.AssetManager.textureCubemap
import org.mrlem.siage3d.core.common.io.loaders.HeightMapLoader
import org.mrlem.siage3d.core.scene.*
import org.mrlem.siage3d.core.scene.lights.DirectionLight
import org.mrlem.siage3d.core.scene.lights.PointLight
import org.mrlem.siage3d.core.scene.materials.Material
import org.mrlem.siage3d.core.scene.materials.MultiTextureMaterial
import org.mrlem.siage3d.core.scene.materials.TextureMaterial
import org.mrlem.siage3d.core.scene.shapes.*
import org.mrlem.siage3d.core.scene.sky.Sky

/**
 * Used to mark builders as part of a DSL definition, and thus prevent some badly scoped calls.
 */
@DslMarker private annotation class SceneDsl

///////////////////////////////////////////////////////////////////////////
// Scene
///////////////////////////////////////////////////////////////////////////

fun scene(init: SceneBuilder.() -> Unit) = SceneBuilder().apply(init)

@SceneDsl
class SceneBuilder : GroupNodeBuilder("scene") {
    private var camera: Camera? = null
    private var sky: Sky? = null
    private val pointLights = mutableListOf<PointLight>()
    private val directionLights = mutableListOf<DirectionLight>()

    fun camera(name: String? = null, init: CameraBuilder.() -> Unit) {
        camera = CameraBuilder(name).apply(init).build()
    }

    fun sky(init: SkyBuilder.() -> Unit) {
        sky = SkyBuilder().apply(init).build()
    }

    fun pointLight(name: String?, init: PointLightBuilder.() -> Unit) {
        pointLights.add(PointLightBuilder(name).apply(init).build())
    }

    fun directionLight(name: String?, init: DirectionLightBuilder.() -> Unit) {
        directionLights.add(DirectionLightBuilder(name).apply(init).build())
    }

    override fun build() = Scene(name).apply {
        this@SceneBuilder.camera?.let { camera = it }
        this@SceneBuilder.sky?.let { sky = it }
        this@SceneBuilder.pointLights.let { lights.addAll(it) }
        this@SceneBuilder.directionLights.let { lights.addAll(it) }
        this@SceneBuilder.children.forEach { add(it) }
    }
}

abstract class NodeBuilder(protected var name: String? = null) {
    private var position = Vector3f()
    private var rotation = Vector3f()
    private var scale = Vector3f(1f, 1f, 1f)

    fun position(x: Float, y: Float, z: Float) {
        position.set(x, y, z)
    }

    fun rotation(yaw: Float, pitch: Float, roll: Float) {
        rotation.set(pitch, yaw, roll)
    }

    fun scale(scale: Float) {
        this.scale.set(scale)
    }

    fun scale(scaleX: Float, scaleY: Float, scaleZ: Float) {
        scale.set(scaleX, scaleY, scaleZ)
    }

    protected fun applyTransformsTo(node: Node) {
        node.localTransform
            .scale(scale)
            .rotateXYZ(
                Math.toRadians(rotation.x.toDouble()).toFloat(),
                Math.toRadians(rotation.y.toDouble()).toFloat(),
                Math.toRadians(rotation.z.toDouble()).toFloat()
            )
            .setTranslation(position)
    }
}

@SceneDsl
class CameraBuilder(name: String?) : NodeBuilder(name) {
    fun build() = Camera(name).apply {
        applyTransformsTo(this)
    }
}

@SceneDsl
class SkyBuilder() : NodeBuilder() {
    @ArrayRes private var cubemap: Int? = null
    private val color = Vector3f()

    fun cubemap(@ArrayRes cubemapResId: Int) {
        cubemap = cubemapResId
    }

    fun color(r: Float, g: Float, b: Float) {
        color.set(r, g, b)
    }

    fun build() = cubemap
        ?.let { Sky.Skybox(textureCubemap(it), color) }
        ?: Sky.SkyColor(color)
}

///////////////////////////////////////////////////////////////////////////
// Lights
///////////////////////////////////////////////////////////////////////////

abstract class LightBuilder(name: String?) : NodeBuilder(name) {
    protected val ambient = Vector3f()
    protected val diffuse = Vector3f()

    fun ambient(r: Float, g: Float, b: Float) {
        ambient.set(r, g, b)
    }

    fun diffuse(r: Float, g: Float, b: Float) {
        diffuse.set(r, g, b)
    }
}

@SceneDsl
class PointLightBuilder(name: String?) : LightBuilder(name) {
    private var constant: Float? = null
    private var linear: Float? = null
    private var quadratic: Float? = null

    fun constant(value: Float) {
        constant = value
    }

    fun linear(value: Float) {
        linear = value
    }

    fun quadratic(value: Float) {
        quadratic = value
    }

    fun build() = PointLight(name, ambient, diffuse).apply {
        this@PointLightBuilder.constant?.let { constant = it }
        this@PointLightBuilder.linear?.let { linear = it }
        this@PointLightBuilder.quadratic?.let { quadratic = it }
        applyTransformsTo(this)
    }
}

@SceneDsl
class DirectionLightBuilder(name: String?) : LightBuilder(name) {
    fun build() = DirectionLight(name, ambient, diffuse).apply {
        applyTransformsTo(this)
    }
}

///////////////////////////////////////////////////////////////////////////
// Objects & groups
///////////////////////////////////////////////////////////////////////////

@SceneDsl
open class GroupNodeBuilder(name: String?) : NodeBuilder(name) {
    protected val children = mutableListOf<Node>()
    val lastNode get() = children.lastOrNull()

    fun groupNode(name: String?, init: GroupNodeBuilder.() -> Unit) {
        children.add(GroupNodeBuilder(name).apply(init).build())
    }

    fun objectNode(name: String?, shape: Shape, init: ObjectNodeBuilder.() -> Unit) {
        children.add(ObjectNodeBuilder(name, shape).apply(init).build())
    }

    fun terrainNode(name: String?, @DrawableRes heightMapResId: Int, init: TerrainNodeBuilder.() -> Unit) {
        children.add(TerrainNodeBuilder(name, HeightMapLoader().load(heightMapResId)).apply(init).build())
    }

    open fun build() = GroupNode(name).apply {
        this@GroupNodeBuilder.children.forEach { add(it) }
        applyTransformsTo(this)
    }
}

@SceneDsl
open class ObjectNodeBuilder(name: String?, protected val shape: Shape) : NodeBuilder(name) {
    protected var material: Material = TextureMaterial(texture2D(R.drawable.white))

    fun textureMaterial(
        @DrawableRes texture: Int,
        scale: Float = 1f,
        ambient: Float = 0f,
        shineDamper: Float = 1f,
        reflectivity: Float= 0f
    ) {
        material = TextureMaterial(
            texture2D(texture),
            scale,
            ambient,
            shineDamper,
            reflectivity
        )
    }

    fun multiTextureMaterial(
        @DrawableRes blendMap: Int,
        @DrawableRes backgroundTexture: Int,
        @DrawableRes redTexture: Int,
        @DrawableRes greenTexture: Int,
        @DrawableRes blueTexture: Int,
        scale: Float = 1f,
        ambient: Float = 1f,
        shineDamper: Float = 1f,
        reflectivity: Float= 0f
    ) {
        material = MultiTextureMaterial(
            texture2D(blendMap),
            texture2D(backgroundTexture),
            texture2D(redTexture),
            texture2D(greenTexture),
            texture2D(blueTexture),
            scale,
            ambient,
            shineDamper,
            reflectivity
        )
    }

    open fun build() = ObjectNode(shape, material, name).apply {
        applyTransformsTo(this)
    }
}

@SceneDsl
class TerrainNodeBuilder(
    name: String?,
    heightMap: Terrain.HeightMap,
    maxHeight: Float = 0.1f
) : ObjectNodeBuilder(name, Terrain(heightMap, maxHeight)) {

    override fun build() = TerrainNode(shape as Terrain, material, name).apply {
        applyTransformsTo(this)
    }

}
