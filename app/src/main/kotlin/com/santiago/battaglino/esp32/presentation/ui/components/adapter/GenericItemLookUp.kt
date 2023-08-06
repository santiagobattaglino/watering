package com.santiago.battaglino.esp32.presentation.ui.components.adapter

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class GenericItemLookUp(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as GenericRecyclerViewAdapter.GenericRecyclerViewHolder).getItemDetails()
        }
        return null
    }
}