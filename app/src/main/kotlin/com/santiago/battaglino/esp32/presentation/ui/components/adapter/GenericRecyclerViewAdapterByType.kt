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
import com.santiago.battaglino.esp32.databinding.*

class GenericRecyclerViewAdapterByType<T : Any, VM : ViewDataBinding, VM2 : ViewDataBinding>(
    @LayoutRes val layoutID: Int,
    @LayoutRes val layout2ID: Int,
    private val bindingInterface: GenericRecyclerBindingInterfaceByType<VM, VM2, T>,
    private val clickListener: GenericClickListener<T>? = null
) : ListAdapter<T, GenericRecyclerViewAdapterByType.GenericRecyclerViewHolderByType>(
    GenericDiffUtilCallback()
) {

    companion object {
        const val TYPE_DEFAULT = 0
        const val TYPE_1 = 1
        const val TYPE_2 = 2
    }

    private val tag = javaClass.simpleName
    private var selectionTracker: SelectionTracker<Long>? = null

    /**
     * Add binding types here
     */
    @Suppress("UNCHECKED_CAST")
    class GenericRecyclerViewHolderByType(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun <T : Any, VM : ViewDataBinding, VM2 : ViewDataBinding> bind(
            item: T,
            bindingInterface: GenericRecyclerBindingInterfaceByType<VM, VM2, T>,
            clickListener: GenericClickListener<T>?,
            position: Int,
            b: Boolean
        ) {
            when (binding) {
                /*is RowMessageBinding -> bindingInterface.bindData(
                    binding as VM, item, clickListener, position
                )
                is RowMessageFromMeBinding -> bindingInterface.bindData2(
                    binding as VM2, item, clickListener, position
                )*/
            }
            itemView.isActivated = b
        }

        fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition() = bindingAdapterPosition
            override fun getSelectionKey() = itemId
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            /*is Message -> {
                if (!item.fromMe) TYPE_1 else TYPE_2
            }
            is Follower -> {
                try {
                    val nextName = (getItem(position - 1) as Follower).name
                    if (item.name.first() != nextName.first()) {
                        TYPE_1
                    } else {
                        TYPE_2
                    }
                } catch (e: Exception) {
                    TYPE_DEFAULT
                }
            }*/
            else -> {
                TYPE_DEFAULT
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): GenericRecyclerViewHolderByType {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_1 -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater, layoutID, parent, false
                )
                GenericRecyclerViewHolderByType(binding)
            }

            TYPE_2 -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater, layout2ID, parent, false
                )
                GenericRecyclerViewHolderByType(binding)
            }

            else -> { // TYPE_DEFAULT
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater, layoutID, parent, false
                )
                GenericRecyclerViewHolderByType(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: GenericRecyclerViewHolderByType, position: Int) {
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

interface GenericRecyclerBindingInterfaceByType<VM : ViewDataBinding, VM2 : ViewDataBinding, T : Any> {
    fun bindData(
        binder: VM, model: T, clickListener: GenericClickListener<T>?, position: Int
    )

    fun bindData2(
        binder2: VM2, model: T, clickListener: GenericClickListener<T>?, position: Int
    )
}