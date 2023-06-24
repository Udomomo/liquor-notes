package com.udomomo.liquornotes.domains

class Star private constructor(
    val value: Double,
) {
    companion object {
        fun of(value: Double): Star {
            if (value < 0 || value > 10) throw IllegalArgumentException("value is out of range")
            if (value.toString()
                    .split(".")[1].length != 1
            ) {
                throw IllegalArgumentException("value should have exactly one decimal place")
            }

            return Star(value)
        }
    }
}
