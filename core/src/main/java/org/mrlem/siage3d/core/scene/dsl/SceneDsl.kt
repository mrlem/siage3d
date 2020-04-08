package org.mrlem.siage3d.core.scene.dsl

import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import org.joml.Vector3f
import org.mrlem.siage3d.core.R
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
class SceneBuilder(
    private val materials: MutableList<Material> = mutableListOf(TextureMaterial("default", texture2D(R.drawable.white)))
) : GroupNodeBuilder(materials, "scene") {
    private var camera: Camera? = null
    private var sky: Sky? = null
    private val pointLights = mutableListOf<PointLight>()
    private val directionLights = mutableListOf<DirectionLight>()

    fun camera(name: String = "camera", init: CameraBuilder.() -> Unit): Camera {
        return CameraBuilder(name).apply(init).build()
            .also { camera = it }
    }

    fun sky(name: String = "sky", init: SkyBuilder.() -> Unit): Sky {
        return SkyBuilder(name).apply(init).build()
            .also { sky = it }
    }

    fun pointLight(name: String = "point-light", init: PointLightBuilder.() -> Unit): PointLight {
        return PointLightBuilder(name).apply(init).build()
            .also { pointLights.add(it) }
    }

    fun directionLight(name: String = "direction-light", init: DirectionLightBuilder.() -> Unit): DirectionLight {
        return DirectionLightBuilder(name).apply(init).build()
            .also { directionLights.add(it) }
    }

    fun material(name: String = "material", init: MaterialBuilder.() -> Unit): Material {
        return MaterialBuilder(name).apply(init).build()
            .also { materials.add(it) }
    }

    override fun build() = Scene(name).apply {
        this@SceneBuilder.camera?.let { camera = it }
        this@SceneBuilder.sky?.let { sky = it }
        materials.addAll(this@SceneBuilder.materials)
        lights.addAll(pointLights)
        lights.addAll(directionLights)
        this@SceneBuilder.children.forEach { add(it) }
    }
}

abstract class NodeBuilder(protected var name: String)

abstract class SpatialNodeBuilder(name: String) : NodeBuilder(name) {
    private var position = Vector3f()
    private var rotation = Vector3f()
    private var scale = Vector3f(1f, 1f, 1f)

    fun position(x: Float, y: Float, z: Float) {
        position.set(x, y, z)
    }

    fun rotation(yaw: Float, pitch: Float, roll: Float) {
        rotation.set(pitch, yaw, roll)
    }

    fun scale(value: Float) {
        scale.set(value)
    }

    fun scale(scaleX: Float, scaleY: Float, scaleZ: Float) {
        scale.set(scaleX, scaleY, scaleZ)
    }

    protected fun applyTransformsTo(node: SpatialNode) {
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
class CameraBuilder(name: String) : SpatialNodeBuilder(name) {
    fun build() = Camera(name).apply {
        applyTransformsTo(this)
    }
}

@SceneDsl
class SkyBuilder(name: String) : NodeBuilder(name) {
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

abstract class LightBuilder(name: String) : SpatialNodeBuilder(name) {
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
class PointLightBuilder(name: String) : LightBuilder(name) {
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
class DirectionLightBuilder(name: String) : LightBuilder(name) {
    fun build() = DirectionLight(name, ambient, diffuse).apply {
        applyTransformsTo(this)
    }
}

///////////////////////////////////////////////////////////////////////////
// Objects & groups
///////////////////////////////////////////////////////////////////////////

@SceneDsl
open class GroupNodeBuilder(private val materials: List<Material>, name: String) : SpatialNodeBuilder(name) {
    protected val children = mutableListOf<Node>()

    fun groupNode(name: String = "group", init: GroupNodeBuilder.() -> Unit): GroupNode {
        return GroupNodeBuilder(materials, name).apply(init).build()
            .also { children.add(it) }
    }

    fun objectNode(name: String = "object", shape: Shape, init: ObjectNodeBuilder.() -> Unit): ObjectNode {
        return ObjectNodeBuilder(materials, name, shape).apply(init).build()
            .also { children.add(it) }
    }

    fun terrainNode(name: String = "terrain", @DrawableRes heightMapResId: Int, maxHeight: Float, init: TerrainNodeBuilder.() -> Unit): TerrainNode {
        return TerrainNodeBuilder(materials, name, HeightMapLoader().load(heightMapResId), maxHeight).apply(init).build()
            .also { children.add(it) }
    }

    open fun build() = GroupNode(name).apply {
        this@GroupNodeBuilder.children.forEach { add(it) }
        applyTransformsTo(this)
    }
}

@SceneDsl
open class ObjectNodeBuilder(private val materials: List<Material>, name: String, protected val shape: Shape) : SpatialNodeBuilder(name) {
    protected var material: Material = materials.first()

    fun material(name: String) {
        materials.firstOrNull { it.name == name }
            ?.let { material = it }
    }

    fun material(init: MaterialBuilder.() -> Unit): Material {
        return MaterialBuilder(name).apply(init).build()
            .also { material = it }
    }

    open fun build() = ObjectNode(shape, material, name).apply {
        applyTransformsTo(this)
    }
}

@SceneDsl
class TerrainNodeBuilder(
    materials: List<Material>,
    name: String,
    heightMap: Terrain.HeightMap,
    maxHeight: Float
) : ObjectNodeBuilder(materials, name, Terrain(heightMap, maxHeight)) {

    override fun build() = TerrainNode(shape as Terrain, material, name).apply {
        applyTransformsTo(this)
    }
}

///////////////////////////////////////////////////////////////////////////
// Materials
///////////////////////////////////////////////////////////////////////////

@SceneDsl
class MaterialBuilder(name: String) : NodeBuilder(name) {
    private var scale: Float? = null
    private var ambient: Float? = null
    private var shineDamper: Float? = null
    private var reflectivity: Float? = null

    @DrawableRes private var textureId: Int? = null

    @DrawableRes private var textureMapId: Int? = null
    @DrawableRes private var backgroundTextureId: Int? = null
    @DrawableRes private var redTextureId: Int? = null
    @DrawableRes private var greenTextureId: Int? = null
    @DrawableRes private var blueTextureId: Int? = null

    fun scale(value: Float) {
        scale = value
    }

    fun ambient(value: Float) {
        ambient = value
    }

    fun shineDamper(value: Float) {
        shineDamper = value
    }

    fun reflectivity(value: Float) {
        reflectivity = value
    }

    fun texture(@DrawableRes texture: Int) {
        textureId = texture
        textureMapId = null
        backgroundTextureId = null
        redTextureId = null
        greenTextureId = null
        blueTextureId = null
    }

    fun textureMap(
        @DrawableRes textureMap: Int,
        @DrawableRes backgroundTexture: Int,
        @DrawableRes redTexture: Int,
        @DrawableRes greenTexture: Int,
        @DrawableRes blueTexture: Int
    ) {
        textureMapId = textureMap
        backgroundTextureId = backgroundTexture
        redTextureId = redTexture
        greenTextureId = greenTexture
        blueTextureId = blueTexture
        textureId = null
    }

    fun build(): Material = when {
        textureId != null ->
            TextureMaterial(
                name,
                texture2D(textureId!!),
                scale ?: 1f,
                ambient ?: 1f,
                shineDamper ?: 1f,
                reflectivity ?: 0f
            )
        textureMapId != null ->
            MultiTextureMaterial(
                name,
                texture2D(textureMapId!!),
                texture2D(backgroundTextureId!!),
                texture2D(redTextureId!!),
                texture2D(greenTextureId!!),
                texture2D(blueTextureId!!),
                scale ?: 1f,
                ambient ?: 1f,
                shineDamper ?: 1f,
                reflectivity ?: 0f
            )
        else -> TextureMaterial(name, texture2D(R.drawable.white))
    }
}
