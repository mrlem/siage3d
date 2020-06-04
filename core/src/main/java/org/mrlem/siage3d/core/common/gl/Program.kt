package org.mrlem.siage3d.core.common.gl

import android.opengl.GLES30.*
import org.joml.Matrix4f
import org.joml.Vector3f
import java.nio.FloatBuffer
import kotlin.system.exitProcess

/**
 * OpenGL program reference: used to perform rendering.
 *
 * @param vertexSource vertex source code string.
 * @param fragmentSource fragment source code string.
 * @param attributes list of all program attributes.
 * @param uniforms list of all program uniforms.
 */
open class Program(
    vertexSource: String,
    fragmentSource: String,
    attributes: List<AttributeDefinition>,
    uniforms: List<UniformDefinition>
) {

    val id: Int
    private val vertexShaderId: Int
    private val fragmentShaderId: Int

    init {
        vertexShaderId = load(vertexSource, GL_VERTEX_SHADER)
        fragmentShaderId = load(fragmentSource, GL_FRAGMENT_SHADER)

        id = glCreateProgram()
        glAttachShader(id, vertexShaderId)
        glAttachShader(id, fragmentShaderId)
        bindAttributes(attributes)
        glLinkProgram(id)
        glValidateProgram(id)
        getUniformLocations(uniforms)
    }

    fun use() {
        val alreadyActive = (id == activeProgramId)

        if (!alreadyActive) {
            activeProgramId = id
            glUseProgram(id)
        }
    }

    fun destroy() {
        glUseProgram(0)
        glDetachShader(id, vertexShaderId)
        glDetachShader(id, fragmentShaderId)
        glDeleteShader(vertexShaderId)
        glDeleteShader(fragmentShaderId)
        glDeleteProgram(id)
    }

    fun loadInt(uniform: UniformDefinition, value: Int) {
        glUniform1i(uniform.location, value)
    }

    fun loadFloat(uniform: UniformDefinition, value: Float) {
        glUniform1f(uniform.location, value)
    }

    fun loadVector(uniform: UniformDefinition, vector: Vector3f) {
        glUniform3f(uniform.location, vector.x, vector.y, vector.z)
    }

    fun loadBoolean(uniform: UniformDefinition, boolean: Boolean) {
        glUniform1f(uniform.location, if (boolean) 1f else 0f )
    }

    fun loadMatrix(uniform: UniformDefinition, matrix: Matrix4f) {
        glUniformMatrix4fv(uniform.location, 1, false, matrix.get(matrixBuffer))
    }

    private fun bindAttributes(attributes: List<AttributeDefinition>) {
        attributes.forEach { attribute -> bindAttribute(attribute)}
    }

    private fun bindAttribute(attribute: AttributeDefinition) {
        glBindAttribLocation(id, attribute.index, attribute.id)
    }

    private fun getUniformLocations(uniforms: List<UniformDefinition>) {
        uniforms.forEach { uniform -> uniform.location = getUniformLocation(uniform.id) }
    }

    private fun getUniformLocation(uniformName: String) = glGetUniformLocation(id, uniformName)

    private fun load(source: String, type: Int): Int {
        val shaderId = glCreateShader(type)
        glShaderSource(shaderId, source)
        glCompileShader(shaderId)
        val status = intArrayOf(0)
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, status, 0)
        if (status[0] == GL_FALSE) {
            println("error: ${glGetShaderInfoLog(shaderId)}")
            println("error: could not compile shader")
            glDeleteShader(shaderId)
            exitProcess(-1)
        }

        return shaderId
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constants: shader interface definition
    ///////////////////////////////////////////////////////////////////////////

    interface AttributeDefinition {
        val id: String
        val index: Int
    }

    interface UniformDefinition {
        val id: String
        var location: Int
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constants: attributes
    ///////////////////////////////////////////////////////////////////////////

    enum class Attribute(
        override val id: String,
        override val index: Int
    ) : AttributeDefinition {

        POSITIONS("position", 0),
        TEXCOORDS("textureCoords", 1),
        NORMALS("normal", 2)

    }

    companion object {

        private val matrixBuffer = FloatBuffer.allocate(16)

        private var activeProgramId = 0

    }

}
