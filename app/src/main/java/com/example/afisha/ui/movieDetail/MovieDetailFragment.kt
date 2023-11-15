package com.example.afisha.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.afisha.AfishaScreens
import com.example.afisha.R
import com.example.afisha.base.ui.BaseFragment
import com.example.afisha.base.ui.BaseViewModel.Companion.provideFactory
import com.example.afisha.common.LoadableData
import com.example.afisha.common.extension.setDisplayHomeAsUpEnabled
import com.example.afisha.common.extension.setTitle
import com.example.afisha.databinding.MovieDetailActivityBinding
import com.example.afisha.domain.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailFragment(
    private val movie: Movie
) : BaseFragment() {

    @Inject
    lateinit var factory: MovieDetailViewModel.Factory

    private lateinit var binding: MovieDetailActivityBinding

    override val viewModel: MovieDetailViewModel by viewModels {
        provideFactory {
            factory.create(movie)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(movie.name)
        setDisplayHomeAsUpEnabled(true)
    }

    override fun subscribe() {
        super.subscribe()
        with(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch { viewModel.movieState.collect(::onViewState) }
                }
            }
        }
    }

    private fun onViewState(state: LoadableData<Movie>) {
        when (state) {
            is LoadableData.Success -> {
                viewModel.hideLoading()
                renderLine(movie)
            }
            is LoadableData.Loading -> viewModel.showLoading()
            is LoadableData.Error -> {
                viewModel.hideLoading()
                showMessage(state.error.message!!, MessageType.ERROR)
            }
        }
    }

    private fun renderLine(movie: Movie) {
        with(binding) {
            Picasso.get().load(movie.poster.url).into(binding.ivPreview)
            tvTitle.text = movie.name
            tvSubtitle.text = getString(R.string.movie_detail_subtitle_text,
                movie.premiere, movie.totalSeriesLength, movie.ageRating)
            tvGenres.text = getString(R.string.movie_detail_genres_text,
                movie.year, movie.genres.joinToString(", ") { it.name })
            tvRating.text = getString(R.string.movie_detail_rating_text, movie.rating.imdb)
            tvActors.text = getString(R.string.movie_detail_actors_text,
                movie.persons?.joinToString(", ") { it.name!! })
            tvPremier.text = getString(R.string.movie_detail_premier_text, movie.premiere)
            tvDescription.text = movie.description
            tvTrailer.setOnClickListener {
                val url = getOneUrlTrailer(movie)
                if (url != null) {
                    router.navigateTo(AfishaScreens.ExternalScreen(url))
                }
            }
        }
    }

    private fun getOneUrlTrailer(movie: Movie): String? {
        return movie.trailers?.getOrNull(0)?.url
    }
}