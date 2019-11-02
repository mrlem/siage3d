package org.mrlem.k3d.core.common.gl

// TODO - replace with std android color class? seems hard
// TODO - replace matrices / vectors with android matrices+ext & float arrays?

class Color(
    red: Float,
    green: Float,
    blue: Float
) {

    val components = floatArrayOf(red, green, blue)

    fun set(red: Float, green: Float, blue: Float) {
        components[0] = red
        components[1] = green
        components[2] = blue
    }

}
