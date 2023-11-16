package com.example.afisha.ui.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afisha.base.ui.BasePagingAdapter
import com.example.afisha.databinding.MovieTopListItemBinding
import com.example.afisha.domain.model.Movie
import com.example.afisha.ui.top.uiState.MovieState
import com.squareup.picasso.Picasso


class MovieTopAdapter (
    onItemClicked: (Movie) -> Unit
) : BasePagingAdapter<Movie, MovieState, MovieTopAdapter.ItemViewHolder>(
    createDiffCallback(
        { oldItem, newItem -> oldItem.title == newItem.title },
        { oldItem, newItem -> oldItem == newItem }
    ),
    onItemClicked
) {

    override fun createViewHolder(parent: ViewGroup): ItemViewHolder =
        ItemViewHolder(
            MovieTopListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun bindViewHolder(holder: ItemViewHolder, item: MovieState) = holder.bind(item)

    class ItemViewHolder(
        private val binding: MovieTopListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieState) = with(binding) {
            loadImage(movie.posterUrl)
            tvTitle.text = movie.title
            tvGenres.text = movie.genres
            tvDescription.text = movie.description
            tvRating.text = movie.rating
            tvRating.setTextColor(movie.colorRating)
        }

        private fun loadImage(url: String) {
            Picasso.get().load(url).into(binding.ivPreview)
        }
    }
}