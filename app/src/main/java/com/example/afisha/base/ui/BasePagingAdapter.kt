package com.example.afisha.base.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Base paging adapter.
 */
abstract class BasePagingAdapter<O, T, VH>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val onItemClicked: ((O) -> Unit)? = null
) : PagingDataAdapter<T, VH>(diffCallback)
    where T : BaseUiState<O>,
          VH : RecyclerView.ViewHolder {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewHolder = createViewHolder(parent)
        if (onItemClicked != null) {
            viewHolder.itemView.setOnClickListener {
                val position = viewHolder.bindingAdapterPosition
                val item = getItem(position)
                if (item != null) {
                    onItemClicked.invoke(item.original)
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            bindViewHolder(holder, item)
        }
    }

    /**
     * Create ViewHolder.
     */
    abstract fun createViewHolder(parent: ViewGroup): VH

    /**
     * Set up the created ViewHolder.
     */
    abstract fun bindViewHolder(holder: VH, item: T)

    companion object {

        /**
         * Used to create DiffCallback for comparing list items.
         */
        inline fun <reified T> createDiffCallback(
            crossinline areItemsTheSame: (T, T) -> Boolean,
            crossinline areContentsTheSame: (T, T) -> Boolean
        ): DiffUtil.ItemCallback<T> {
            return object : DiffUtil.ItemCallback<T>() {
                override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
                    return areItemsTheSame(oldItem, newItem)
                }

                override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
                    return areContentsTheSame(oldItem, newItem)
                }
            }
        }
    }
}