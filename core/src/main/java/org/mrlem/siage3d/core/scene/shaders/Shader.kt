package org.mrlem.siage3d.core.scene.shaders

import android.opengl.GLES30.*
import org.joml.Matrix4f
import org.joml.Vector3f
import java.nio.FloatBuffer
import kotlin.system.exitProcess

abstract class Shader(
    vertexSource: String,
    fragmentSource: String,
    attributes: List<AttributeDefinition>,
    uniforms: List<UniformDefinition>
) {

    private val programId: Int
    private val vertexShaderId: Int
    private val fragmentShaderId: Int

    init {
        vertexShaderId = load(vertexSource, GL_VERTEX_SHADER)
        fragmentShaderId = load(fragmentSource, GL_FRAGMENT_SHADER)

        programId = glCreateProgram()
        glAttachShader(programId, vertexShaderId)
        glAttachShader(programId, fragmentShaderId)
        bindAttributes(attributes)
        glLinkProgram(programId)
        glValidateProgram(programId)
        getUniformLocations(uniforms)
    }

    fun use(block: Shader.() -> Unit ) {
        val previousProgramId = activeProgramId
        val alreadyActive = (programId == previousProgramId)

        if (!alreadyActive) {
            activeProgramId = programId
            glUseProgram(programId)
        }

        this.block()

        if (!alreadyActive) {
            glUseProgram(previousProgramId)
            activeProgramId = previousProgramId
        }
    }

    fun clear() {
        glUseProgram(0)
        glDetachShader(programId, vertexShaderId)
        glDetachShader(programId, fragmentShaderId)
        glDeleteShader(vertexShaderId)
        glDeleteShader(fragmentShaderId)
        glDeleteProgram(programId)
    }

    protected fun loadInt(uniform: UniformDefinition, value: Int) {
        glUniform1i(uniform.location, value)
    }

    protected fun loadFloat(uniform: UniformDefinition, value: Float) {
        glUniform1f(uniform.location, value)
    }

    protected fun loadVector(uniform: UniformDefinition, vector: Vector3f) {
        glUniform3f(uniform.location, vector.x, vector.y, vector.z)
    }

    protected fun loadBoolean(uniform: UniformDefinition, boolean: Boolean) {
        glUniform1f(uniform.location, if (boolean) 1f else 0f )
    }

    protected fun loadMatrix(uniform: UniformDefinition, matrix: Matrix4f) {
        matrix.get(matrixBuffer)
        glUniformMatrix4fv(uniform.location, 1, false, matrixBuffer)
    }

    private fun bindAttributes(attributes: List<AttributeDefinition>) {
        attributes.forEach { attribute -> bindAttribute(attribute)}
    }

    private fun bindAttribute(attribute: AttributeDefinition) {
        glBindAttribLocation(programId, attribute.index, attribute.id)
    }

    private fun getUniformLocations(uniforms: List<UniformDefinition>) {
        uniforms.forEach { uniform -> uniform.location = getUniformLocation(uniform.id) }
    }

    private fun getUniformLocation(uniformName: String) = glGetUniformLocation(programId, uniformName)

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

    interface ProjectionAware {
        fun loadProjectionMatrix(matrix: Matrix4f)
    }

    interface ViewAware {
        fun loadViewMatrix(matrix: Matrix4f)
    }

    interface TransformationAware {
        fun loadTransformationMatrix(matrix: Matrix4f)
    }

    interface AttributeDefinition {
        val id: String
        val index: Int
    }

    interface UniformDefinition {
        val id: String
        var location: Int
    }

    companion object {
        private val matrixBuffer = FloatBuffer.allocate(16)

        private val shaders = mutableListOf<Shader>()
        lateinit var defaultShader: DefaultShader
        lateinit var skyboxShader: SkyboxShader

        private var activeProgramId = 0

        fun init() {
            defaultShader = DefaultShader()
                .also { shaders.add(it) }
            skyboxShader = SkyboxShader()
                .also { shaders.add(it) }
        }

        fun notifyProjectionMatrix(matrix: Matrix4f) {
            shaders.forEach {
                if (it is ProjectionAware) {
                    it.use { it.loadProjectionMatrix(matrix) }
                }
            }
        }

        fun notifyViewMatrix(matrix: Matrix4f) {
            shaders.forEach {
                if (it is ViewAware) {
                    it.use { it.loadViewMatrix(matrix) }
                }
            }
        }
    }

}