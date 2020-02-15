package org.mrlem.siage3d.core.scene.dsl

import androidx.annotation.RawRes
import org.joml.Vector3f
import org.mrlem.siage3d.core.common.io.AssetManager.texture2D
import org.mrlem.siage3d.core.common.io.AssetManager.textureCubemap
import org.mrlem.siage3d.core.common.io.loaders.HeightMapLoader
import org.mrlem.siage3d.core.scene.*
import org.mrlem.siage3d.core.scene.lights.PointLight
import org.mrlem.siage3d.core.scene.materials.Material
import org.mrlem.siage3d.core.scene.materials.MultiTextureMaterial
import org.mrlem.siage3d.core.scene.materials.TextureMaterial
import org.mrlem.siage3d.core.scene.shapes.*
import org.mrlem.siage3d.core.scene.sky.Sky

///////////////////////////////////////////////////////////////////////////
// Root scope functions
///////////////////////////////////////////////////////////////////////////

fun scene(init: Scene.() -> Unit) = Scene().apply {
    init()
}

fun color(r: Float, g: Float, b: Float) = Vector3f(r, g, b)

fun position(x: Float, y: Float, z: Float) = Vector3f(x, y, z)

fun textureMaterial(texture: Int, scale: Float = 1f, ambient: Float = 0f) = TextureMaterial(texture2D(texture), scale, ambient)

fun multiTextureMaterial(
    blendMap: Int,
    backgroundTexture: Int,
    redTexture: Int,
    greenTexture: Int,
    blueTexture: Int,
    scale: Float = 1f,
    ambient: Float = 1f
) = MultiTextureMaterial(
    texture2D(blendMap),
    texture2D(backgroundTexture),
    texture2D(redTexture),
    texture2D(greenTexture),
    texture2D(blueTexture),
    scale,
    ambient
)

fun box() = Box()

fun triangle() = Triangle()

fun square() = Square()

fun grid(size: Float, vertexCount: Int) = Grid(size, vertexCount)

fun terrain(size: Float, heightMap: Terrain.HeightMap, maxHeight: Float) = Terrain(size, heightMap, maxHeight)

fun heightMap(@RawRes resId: Int) = HeightMapLoader().load(resId)

///////////////////////////////////////////////////////////////////////////
// Scene scope functions
///////////////////////////////////////////////////////////////////////////

fun Scene.camera(name: String) = Camera(name)
    .also { camera = it }

fun Scene.light(name: String, ambient: Vector3f, diffuse: Vector3f) = PointLight(name, ambient, diffuse)
    .also { lights.add(it) }

fun Scene.sky(color: Vector3f, cubemap: Int? = null) = (cubemap?.let {
    Sky.Skybox(textureCubemap(cubemap), color)
} ?: Sky.SkyColor(color))
    .also { sky = it }

///////////////////////////////////////////////////////////////////////////
// Node scope functions
///////////////////////////////////////////////////////////////////////////

fun <T : Node> T.translate(x: Float, y: Float, z: Float) = this
    .also { localTransform.setTranslation(x, y, z) }

fun <T : Node> T.translate(position: Vector3f) = this
    .also { localTransform.setTranslation(position) }

fun <T : Node> T.scale(scale: Float) = this
    .also { localTransform.scale(scale) }

fun <T : Node> T.scale(scaleX: Float, scaleY: Float, scaleZ: Float) = this
    .also { localTransform.scale(scaleX, scaleY, scaleZ) }

fun <T : Node> T.rotate(x: Float, y: Float, z: Float) = this
    .also { localTransform.setRotationXYZ(x, y, z) }

fun <T : Node> T.position(): Vector3f = localTransform.getTranslation(Vector3f())

fun <T : Node> T.position(x: Float, y: Float, z: Float) = this
    .apply { localTransform.setTranslation(x, y, z) }

fun <T : Node> T.position(position: Vector3f) = this
    .apply { localTransform.setTranslation(position) }

///////////////////////////////////////////////////////////////////////////
// Group node scope functions
///////////////////////////////////////////////////////////////////////////

fun GroupNode.groupNode(init: GroupNode.() -> Unit) = GroupNode()
    .apply { init() }
    .also { add(it) }

fun GroupNode.objectNode(name: String, shape: Shape, material: Material) = ObjectNode(shape, material, name)
    .also { add(it) }
