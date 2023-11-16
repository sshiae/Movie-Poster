package com.example.afisha.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afisha.base.ui.BasePagingAdapter
import com.example.afisha.databinding.CountryListItemBinding
import com.example.afisha.domain.model.Country
import com.example.afisha.ui.country.uiState.CountryState

class CountryAdapter(
    onItemClicked: (Country) -> Unit
) : BasePagingAdapter<Country, CountryState, CountryAdapter.ItemViewHolder>(
    createDiffCallback(
        { oldItem, newItem -> oldItem.id == newItem.id },
        { oldItem, newItem -> oldItem == newItem }
    ),
    onItemClicked
) {

    override fun createViewHolder(parent: ViewGroup): ItemViewHolder =
        ItemViewHolder(
            CountryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun bindViewHolder(holder: ItemViewHolder, item: CountryState) = holder.bind(item)

    class ItemViewHolder(private val binding: CountryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: CountryState) {
            binding.title.text = country.title
        }
    }
}