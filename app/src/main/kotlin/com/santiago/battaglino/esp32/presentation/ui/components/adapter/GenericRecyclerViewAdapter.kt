package com.santiago.battaglino.esp32.presentation.ui.components.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class GenericRecyclerViewAdapter<T : Any, VM : ViewDataBinding>(
    @LayoutRes val layoutID: Int,
    private val bindingInterface: GenericRecyclerBindingInterface<VM, T>,
    private val clickListener: GenericClickListener<T>? = null
) :
    ListAdapter<T, GenericRecyclerViewAdapter.GenericRecyclerViewHolder>(
        GenericDiffUtilCallback()
    ) {
    var selectionTracker: SelectionTracker<Long>? = null

    @Suppress("UNCHECKED_CAST")
    class GenericRecyclerViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun <T : Any, VM : ViewDataBinding> bind(
            item: T,
            bindingInterface: GenericRecyclerBindingInterface<VM, T>,
            clickListener: GenericClickListener<T>?,
            position: Int,
            b: Boolean
        ) {
            bindingInterface.bindData(binding as VM, item, clickListener, position)
            itemView.isActivated = b
        }

        fun getItemDetails() = object :
            ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition() = bindingAdapterPosition
            override fun getSelectionKey() = itemId
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            layoutID,
            parent,
            false
        )
        return GenericRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(
            item,
            bindingInterface,
            clickListener,
            position,
            selectionTracker?.isSelected(position.toLong()) ?: false
        )
    }

}

interface GenericRecyclerBindingInterface<VM : ViewDataBinding, T : Any> {
    fun bindData(viewBinding: VM, data: T, clickListener: GenericClickListener<T>?, position: Int)
}