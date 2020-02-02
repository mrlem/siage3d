package org.mrlem.siage3d.core.scene.dsl

import org.joml.Vector3f
import org.mrlem.siage3d.core.common.io.AssetManager.texture2D
import org.mrlem.siage3d.core.common.io.AssetManager.textureCubemap
import org.mrlem.siage3d.core.scene.*
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

fun textureMaterial(texture: Int, tileSize: Float = 1f) = TextureMaterial(texture2D(texture), tileSize)

fun multiTextureMaterial(
    blendMap: Int,
    backgroundTexture: Int,
    redTexture: Int,
    greenTexture: Int,
    blueTexture: Int,
    tileSize: Float = 1f
) = MultiTextureMaterial(
    texture2D(blendMap),
    texture2D(backgroundTexture),
    texture2D(redTexture),
    texture2D(greenTexture),
    texture2D(blueTexture),
    tileSize
)

fun box() = Box()

fun triangle() = Triangle()

fun square() = Square()

fun grid(size: Float, vertexCount: Int) = Grid(size, vertexCount)

///////////////////////////////////////////////////////////////////////////
// Scene scope functions
///////////////////////////////////////////////////////////////////////////

fun Scene.camera(init: Camera.() -> Unit) = Camera()
    .apply { init() }
    .also { camera = it }

fun Scene.sky(color: Vector3f, cubemap: Int? = null) = (cubemap?.let {
    Sky.Skybox(textureCubemap(cubemap), color)
} ?: Sky.SkyColor(color))
    .also { sky = it }

///////////////////////////////////////////////////////////////////////////
// Camera scope functions
///////////////////////////////////////////////////////////////////////////

fun Camera.position(x: Float, y: Float, z: Float) = position(Vector3f(x, y, z))

///////////////////////////////////////////////////////////////////////////
// Node scope functions
///////////////////////////////////////////////////////////////////////////

fun Node.position(x: Float, y: Float, z: Float) = position(Vector3f(x, y, z))

fun <T : Node> T.position(position: Vector3f) = this
    .also {
        localTransform.setTranslation(position)
    }

fun <T : Node> T.scale(scale: Float) = this
    .also {
        localTransform.scale(scale)
    }

fun <T : Node> T.rotate(x: Float, y: Float, z: Float) = this
    .also {
        localTransform.setRotationXYZ(x, y, z)
    }

///////////////////////////////////////////////////////////////////////////
// Group node scope functions
///////////////////////////////////////////////////////////////////////////

fun GroupNode.groupNode(init: GroupNode.() -> Unit) = GroupNode()
    .apply { init() }
    .also { add(it) }

fun GroupNode.objectNode(name: String, shape: Shape, material: Material) = ObjectNode(shape, material, name)
    .also { add(it) }
