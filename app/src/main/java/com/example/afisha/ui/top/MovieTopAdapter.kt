package com.example.afisha.ui.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afisha.base.ui.BasePagingAdapter
import com.example.afisha.databinding.MovieTopListItemBinding
import com.example.afisha.domain.model.Genre
import com.example.afisha.domain.model.Movie
import com.squareup.picasso.Picasso

class MovieTopAdapter (
    onItemClicked: (Movie) -> Unit
) : BasePagingAdapter<Movie, MovieTopAdapter.ItemViewHolder>(
    createDiffCallback(
        { oldItem, newItem -> oldItem.name == newItem.name },
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

    override fun bindViewHolder(holder: ItemViewHolder, item: Movie) = holder.bind(item)

    class ItemViewHolder(private val binding: MovieTopListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) = with(binding) {
            loadImage(movie.poster.url)
            tvTitle.text = movie.name
            tvGenres.text = getGenresAsString(movie.genres)
            tvDescription.text = movie.shortDescription
            tvRating.text = movie.rating.imdb
        }

        private fun loadImage(url: String) {
            Picasso.get().load(url).into(binding.ivPreview)
        }

        private fun getGenresAsString(genres: List<Genre>): String {
            return genres.joinToString(", ") { it.name }
        }
    }
}