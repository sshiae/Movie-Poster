package com.example.afisha.ui.top

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.afisha.base.ui.BaseViewModel
import com.example.afisha.common.LoadableData
import com.example.afisha.common.emitError
import com.example.afisha.common.emitLoading
import com.example.afisha.common.emitSuccess
import com.example.afisha.domain.interactor.AfishaInteractor
import com.example.afisha.domain.model.Movie
import com.example.afisha.ui.top.uiEvent.MovieUiEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MovieTopViewModel @AssistedInject constructor(
    private val interactor: AfishaInteractor,
    @Assisted private val country: String
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(country: String): MovieTopViewModel
    }

    /**
     * [[Flow] to get a list of movies sorted by rating
     */
    private val topOfMoviesStateFlow: MutableStateFlow<LoadableData<PagingData<Movie>>> =
        MutableStateFlow(LoadableData.Loading())
    val topOfMoviesState = topOfMoviesStateFlow.asStateFlow()

    /**
     * [Channel] for UI events
     */
    private val uiEventChannel: Channel<MovieUiEvent> = Channel(Channel.BUFFERED)
    val uiEventFlow = uiEventChannel.receiveAsFlow()

    override fun firstLoad() {
        viewModelScope.launch {
            try {
                topOfMoviesStateFlow.emitLoading()
                interactor.getMoviesSortedByRating(country)
                    .collect { movies ->
                        topOfMoviesStateFlow.emitSuccess(movies)
                    }
            } catch (e: Exception) {
                topOfMoviesStateFlow.emitError(e)
            }
        }
    }

    /**
     * Called after clicking on a list item
     */
    fun onItemClicked(movie: Movie) {
        uiEventChannel.trySend(MovieUiEvent.OpenMovieDetailFragment(movie))
    }
}