package org.mrlem.siage3d.core.common.io

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

fun FloatArray.toBuffer(): FloatBuffer {
    val buffer = ByteBuffer.allocateDirect(size * 4).order(ByteOrder.nativeOrder())
    return buffer.asFloatBuffer().put(this).also {
        it.position(0)
    }
}

fun ShortArray.toBuffer(): ShortBuffer {
    val buffer = ByteBuffer.allocateDirect(size * 2).order(ByteOrder.nativeOrder())
    return buffer.asShortBuffer().put(this).also {
        it.position(0)
    }
}
