package com.example.afisha.ui.top

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.afisha.R
import com.example.afisha.base.ui.BasePagingAdapter
import com.example.afisha.databinding.MovieTopListItemBinding
import com.example.afisha.domain.model.Genre
import com.example.afisha.domain.model.Movie
import com.squareup.picasso.Picasso


class MovieTopAdapter (
    private val context: Context,
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
            context,
            MovieTopListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun bindViewHolder(holder: ItemViewHolder, item: Movie) = holder.bind(item)

    class ItemViewHolder(
        private val context: Context,
        private val binding: MovieTopListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) = with(binding) {
            loadImage(movie.poster.url)
            tvTitle.text = movie.name
            tvGenres.text = getGenresAsString(movie.genres)
            tvDescription.text = movie.shortDescription
            tvRating.text = movie.rating.imdb
            tvRating.setTextColor(getColorForRating(movie.rating.imdb.toDouble()))
        }

        private fun loadImage(url: String) {
            Picasso.get().load(url).into(binding.ivPreview)
        }

        private fun getGenresAsString(genres: List<Genre>): String {
            return genres.joinToString(", ") { it.name }
        }

        private fun getColorForRating(rating: Double): Int {
            return if (rating >= 7.0) {
                ContextCompat.getColor(context, R.color.rating_good_color)
            } else if (rating >= 5.0) {
                ContextCompat.getColor(context, R.color.rating_ok_color)
            } else {
                ContextCompat.getColor(context, R.color.rating_bad_color)
            }
        }
    }
}