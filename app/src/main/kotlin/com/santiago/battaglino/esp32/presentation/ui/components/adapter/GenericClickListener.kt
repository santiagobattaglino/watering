package com.santiago.battaglino.esp32.presentation.ui.components.adapter

class GenericClickListener<T : Any>(private val clickListener: (T) -> Unit) {
    fun onClick(data: T) = clickListener(data)
}