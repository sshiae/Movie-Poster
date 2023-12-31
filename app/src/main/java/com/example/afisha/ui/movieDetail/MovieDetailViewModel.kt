package com.example.afisha.ui.movieDetail

import androidx.lifecycle.viewModelScope
import com.example.afisha.base.ui.BaseViewModel
import com.example.afisha.common.LoadableData
import com.example.afisha.common.emitError
import com.example.afisha.common.emitLoading
import com.example.afisha.common.emitSuccess
import com.example.afisha.domain.interactor.AfishaInteractor
import com.example.afisha.domain.model.Movie
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel @AssistedInject constructor(
    private val interactor: AfishaInteractor,
    @Assisted private val movieId: Int
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieDetailViewModel
    }

    /**
     * [Flow] for information about the film
     */
    private val movieStateFlow: MutableStateFlow<LoadableData<Movie>> =
        MutableStateFlow(LoadableData.Loading())
    val movieState = movieStateFlow.asStateFlow()

    override fun firstLoad() {
        viewModelScope.launch {
            try {
                movieStateFlow.emitLoading()
                val movie = interactor.getMovieById(movieId)
                movieStateFlow.emitSuccess(movie)
            } catch (e: Exception) {
                movieStateFlow.emitError(e)
            }
        }
    }
}