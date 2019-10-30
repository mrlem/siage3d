package org.mrlem.k3d.core.common.io

import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.ShortBuffer

fun FloatArray.toBuffer(): FloatBuffer {
    val buffer = FloatBuffer.allocate(this.size)
    buffer.put(this).flip()

    return buffer
}

fun IntArray.toBuffer(): IntBuffer {
    val buffer = IntBuffer.allocate(this.size)
    buffer.put(this).flip()

    return buffer
}

fun ShortArray.toBuffer(): ShortBuffer {
    val buffer = ShortBuffer.allocate(this.size)
    buffer.put(this).flip()

    return buffer
}
