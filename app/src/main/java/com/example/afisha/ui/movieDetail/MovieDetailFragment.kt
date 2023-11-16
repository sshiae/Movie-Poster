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
import com.example.afisha.databinding.MovieDetailFragmentBinding
import com.example.afisha.domain.model.Movie
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment() {

    @Inject
    lateinit var factory: MovieDetailViewModel.Factory

    private lateinit var binding: MovieDetailFragmentBinding

    override val viewModel: MovieDetailViewModel by viewModels {
        provideFactory {
            factory.create(
                requireArguments().getInt(EXTRA_MOVIE_ID)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(requireArguments().getString(EXTRA_MOVIE)!!)
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
                renderLine(state.value)
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
                getOneCountry(movie), getMovieLength(movie), movie.ageRating)
            tvGenres.text = getString(R.string.movie_detail_genres_text, movie.year, getGenres(movie))
            tvRating.text = getString(R.string.movie_detail_rating_text, movie.rating.imdb)
            tvActors.text = getString(R.string.movie_detail_actors_text, getActors(movie))
            tvPremier.text = getString(R.string.movie_detail_premier_text, getOneCountry(movie))
            tvDescription.text = movie.description
            tvDirector.text = getString(R.string.movie_detail_director_text, getDirector(movie))
            tvTrailer.setOnClickListener {
                val url = getOneUrlTrailer(movie)
                if (url != null) {
                    router.navigateTo(AfishaScreens.ExternalScreen(url))
                }
            }
        }
    }

    private fun getOneUrlTrailer(movie: Movie): String? {
        return movie.videos?.trailers?.getOrNull(0)?.url
    }

    private fun getDirector(movie: Movie): String {
        val filtered = movie.persons?.filter { it.enProfession?.lowercase() == DIRECTOR_PROFESSION_NAME }
        val person = filtered?.getOrNull(0)
        return person?.name ?: UNDEFINED_VALUE
    }

    private fun getActors(movie: Movie): String {
        val actors = movie.persons?.filter { it.enProfession?.lowercase() == ACTOR_PROFESSION_NAME }
        val actorsString = actors?.joinToString(", ") { it.name!! }
        return actorsString ?: UNDEFINED_VALUE
    }

    private fun getOneCountry(movie: Movie): String {
        return movie.countries.getOrNull(0)?.name ?: (movie.premiere ?: UNDEFINED_VALUE)
    }

    private fun getMovieLength(movie: Movie): Int {
        return movie.movieLength ?:
            movie.seriesLength ?:
            movie.totalSeriesLength ?:
            0
    }

    private fun getGenres(movie: Movie): String {
        return movie.genres.joinToString(", ") { it.name }
    }

    companion object {
        private const val UNDEFINED_VALUE = "Неопределено"
        private const val DIRECTOR_PROFESSION_NAME = "director"
        private const val ACTOR_PROFESSION_NAME = "actor"

        private const val EXTRA_MOVIE = "EXTRA_MOVIE"
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun getNewInstance(movie: String, movieId: Int): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MOVIE, movie)
                    putInt(EXTRA_MOVIE_ID, movieId)
                }
            }
        }
    }
}