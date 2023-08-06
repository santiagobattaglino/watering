package com.santiago.battaglino.esp32.util

fun Collection<Float>.median(): Float {
    val sorted = this.sorted()

    return if (sorted.size % 2 == 0) {
        ((sorted[sorted.size / 2] + sorted[sorted.size / 2 - 1]) / 2)
    } else {
        (sorted[sorted.size / 2])
    }
}