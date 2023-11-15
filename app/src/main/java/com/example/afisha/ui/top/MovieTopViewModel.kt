package com.example.afisha.ui.top

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.afisha.base.ui.BaseViewModel
import com.example.afisha.common.LoadableData
import com.example.afisha.common.emitError
import com.example.afisha.common.emitLoading
import com.example.afisha.common.emitSuccess
import com.example.afisha.domain.interactor.AfishaInteractor
import com.example.afisha.domain.model.Country
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
import javax.inject.Singleton

@Singleton
class MovieTopViewModel @AssistedInject constructor(
    private val interactor: AfishaInteractor,
    @Assisted private val country: Country
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(country: Country): MovieTopViewModel
    }

    /**
     * [Flow] для получения списка фильмов, сортированных по рейтингу
     */
    private val topOfMoviesStateFlow: MutableStateFlow<LoadableData<PagingData<Movie>>> =
        MutableStateFlow(LoadableData.Loading())
    val topOfMoviesState = topOfMoviesStateFlow.asStateFlow()

    /**
     * [Channel] для UI событий
     */
    private val uiEventChannel: Channel<MovieUiEvent> = Channel(Channel.BUFFERED)
    val uiEventFlow = uiEventChannel.receiveAsFlow()

    override fun firstLoad() {
        viewModelScope.launch {
            try {
                topOfMoviesStateFlow.emitLoading()
                interactor.getMoviesSortedByRating(country.name)
                    .collect { movies ->
                        topOfMoviesStateFlow.emitSuccess(movies)
                    }
            } catch (e: Exception) {
                topOfMoviesStateFlow.emitError(e)
            }
        }
    }

    /**
     * Вызывается после нажатия на элемент списка
     */
    fun onItemClicked(movie: Movie) {
        uiEventChannel.trySend(MovieUiEvent.OpenMovieDetailFragment(movie))
    }
}