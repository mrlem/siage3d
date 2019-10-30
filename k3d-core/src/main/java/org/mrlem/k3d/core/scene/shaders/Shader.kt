package org.mrlem.k3d.core.scene.shaders

import android.opengl.GLES30.*
import org.joml.Matrix4f
import org.joml.Vector3f
import java.nio.FloatBuffer
import kotlin.system.exitProcess

abstract class Shader(vertexSource: String, fragmentSource: String) {

    private val programId: Int
    private val vertexShaderId: Int
    private val fragmentShaderId: Int

    var started = false
        private set

    init {
        vertexShaderId = load(vertexSource, GL_VERTEX_SHADER)
        fragmentShaderId = load(fragmentSource, GL_FRAGMENT_SHADER)

        programId = glCreateProgram()
        glAttachShader(programId, vertexShaderId)
        glAttachShader(programId, fragmentShaderId)
        bindAttributes()
        glLinkProgram(programId)
        glValidateProgram(programId)
        getAllUniformLocations()
    }

    fun use(block: Shader.() -> Unit ) {
        start()
        block.invoke(this)
        stop()
    }

    private fun start() {
        glUseProgram(programId)
        started = true
    }

    private fun stop() {
        started = false
        glUseProgram(0)
    }

    fun clear() {
        stop()
        glDetachShader(programId, vertexShaderId)
        glDetachShader(programId, fragmentShaderId)
        glDeleteShader(vertexShaderId)
        glDeleteShader(fragmentShaderId)
        glDeleteProgram(programId)
    }

    abstract fun getAllUniformLocations()

    fun getUniformLocation(uniformName: String) = glGetUniformLocation(programId, uniformName)

    fun loadInt(location: Int, value: Int) {
        glUniform1i(location, value)
    }

    fun loadFloat(location: Int, value: Float) {
        glUniform1f(location, value)
    }

    fun loadVector(location: Int, vector: Vector3f) {
        glUniform3f(location, vector.x, vector.y, vector.z)
    }

    fun loadBoolean(location: Int, boolean: Boolean) {
        glUniform1f(location, if (boolean) 1f else 0f )
    }

    fun loadMatrix(location: Int, matrix: Matrix4f) {
        matrix.get(matrixBuffer)
        glUniformMatrix4fv(location, 1, false,
            matrixBuffer
        )
    }

    abstract fun bindAttributes()

    fun bindAttribute(attribute: Int, variableName: String) {
        glBindAttribLocation(programId, attribute, variableName)
    }

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

    companion object {
        private val matrixBuffer = FloatBuffer.allocate(16)

        lateinit var defaultShader: DefaultShader
    }

}