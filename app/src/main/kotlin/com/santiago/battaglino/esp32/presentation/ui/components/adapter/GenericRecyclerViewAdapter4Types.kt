package com.santiago.battaglino.esp32.presentation.ui.components.adapter

import android.util.Log
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

class GenericRecyclerViewAdapter4Types<T : Any, VM : ViewDataBinding, VM2 : ViewDataBinding, VM3 : ViewDataBinding, VM4 : ViewDataBinding>(
    @LayoutRes val layoutID: Int,
    @LayoutRes val layout2ID: Int,
    @LayoutRes val layout3ID: Int,
    @LayoutRes val layout4ID: Int,
    private val bindingInterface: GenericRecyclerBindingInterface4Types<VM, VM2, VM3, VM4, T>,
    private val clickListener: GenericClickListener<T>? = null
) : ListAdapter<T, GenericRecyclerViewAdapter4Types.GenericRecyclerViewHolder4Types>(
    GenericDiffUtilCallback()
) {

    companion object {
        const val TYPE_DEFAULT = 0
        const val TYPE_1 = 1
        const val TYPE_2 = 2
        const val TYPE_3 = 3
        const val TYPE_4 = 4
    }

    private val tag = javaClass.simpleName
    private var selectionTracker: SelectionTracker<Long>? = null

    /**
     * Add binding types here
     */
    @Suppress("UNCHECKED_CAST")
    class GenericRecyclerViewHolder4Types(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun <T : Any, VM : ViewDataBinding, VM2 : ViewDataBinding, VM3 : ViewDataBinding, VM4 : ViewDataBinding> bind(
            item: T,
            bindingInterface: GenericRecyclerBindingInterface4Types<VM, VM2, VM3, VM4, T>,
            clickListener: GenericClickListener<T>?,
            position: Int,
            b: Boolean
        ) {
            when (binding) {
                // MESSAGES
                /*is RowMessageBinding -> bindingInterface.bindData(
                    binding as VM, item, clickListener, position
                )
                is RowMessageFromMeBinding -> bindingInterface.bindData2(
                    binding as VM2, item, clickListener, position
                )
                is RowMessageMediaBinding -> bindingInterface.bindData3(
                    binding as VM3, item, clickListener, position
                )
                is RowMessageFromMeMediaBinding -> bindingInterface.bindData4(
                    binding as VM4, item, clickListener, position
                )*/

                // DEFAULT
                else -> bindingInterface.bindData(
                    binding as VM, item, clickListener, position
                )
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
                when (item.type) {
                    Message.TEXT -> if (!item.fromMe) TYPE_1 else TYPE_2
                    Message.MEDIA_IMAGE -> if (!item.fromMe) TYPE_3 else TYPE_4
                    Message.MEDIA_VIDEO -> if (!item.fromMe) TYPE_3 else TYPE_4
                    else -> TYPE_1
                }
            }
            is Follower -> {
                try {
                    val nextName = (getItem(position - 1) as Follower).name
                    if (item.name.first() != nextName.first()) {
                        Log.e(
                            tag,
                            "TYPE_1 position $position ${item.name.first()} != ${nextName.first()}"
                        )
                        TYPE_1
                    } else {
                        Log.e(
                            tag,
                            "TYPE_2 position $position ${item.name.first()} == ${nextName.first()}"
                        )
                        TYPE_2
                    }
                } catch (e: Exception) {
                    Log.e(tag, "catch (e: Exception) TYPE_DEFAULT position $position ${e.message}")
                    TYPE_DEFAULT
                }
            }*/
            else -> {
                Log.e(tag, "else TYPE_DEFAULT position $position")
                TYPE_DEFAULT
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): GenericRecyclerViewHolder4Types {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_1 -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater, layoutID, parent, false
                )
                GenericRecyclerViewHolder4Types(binding)
            }

            TYPE_2 -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater, layout2ID, parent, false
                )
                GenericRecyclerViewHolder4Types(binding)
            }

            TYPE_3 -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater, layout3ID, parent, false
                )
                GenericRecyclerViewHolder4Types(binding)
            }

            TYPE_4 -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater, layout4ID, parent, false
                )
                GenericRecyclerViewHolder4Types(binding)
            }

            else -> { // TYPE_DEFAULT
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater, layoutID, parent, false
                )
                GenericRecyclerViewHolder4Types(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: GenericRecyclerViewHolder4Types, position: Int) {
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

interface GenericRecyclerBindingInterface4Types<VM : ViewDataBinding, VM2 : ViewDataBinding, VM3 : ViewDataBinding, VM4 : ViewDataBinding, T : Any> {
    fun bindData(
        binder: VM, model: T, clickListener: GenericClickListener<T>?, position: Int
    )

    fun bindData2(
        binder: VM2, model: T, clickListener: GenericClickListener<T>?, position: Int
    )

    fun bindData3(
        binder: VM3, model: T, clickListener: GenericClickListener<T>?, position: Int
    )

    fun bindData4(
        binder: VM4, model: T, clickListener: GenericClickListener<T>?, position: Int
    )
}