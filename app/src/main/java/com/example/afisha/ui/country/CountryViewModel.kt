package com.example.afisha.ui.country

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.afisha.base.ui.BaseViewModel
import com.example.afisha.common.LoadableData
import com.example.afisha.common.emitError
import com.example.afisha.common.emitLoading
import com.example.afisha.common.emitSuccess
import com.example.afisha.domain.interactor.AfishaInteractor
import com.example.afisha.domain.model.Country
import com.example.afisha.ui.country.uiEvent.CountryUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val interactor: AfishaInteractor
) : BaseViewModel() {

    /**
     * [Flow] для получения списка стран
     */
    private val countriesStateFlow: MutableStateFlow<LoadableData<PagingData<Country>>> =
        MutableStateFlow(LoadableData.Loading())
    val countriesState = countriesStateFlow.asStateFlow()

    /**
     * [Channel] для UI событий
     */
    private val uiEventChannel: Channel<CountryUiEvent> = Channel(Channel.BUFFERED)
    val uiEventFlow = uiEventChannel.receiveAsFlow()

    override fun firstLoad() {
        viewModelScope.launch {
            searchStringState.collectLatest { searchString ->
                getCountries(searchString)
            }
        }
    }

    /**
     * Вызывается после нажатия по элементу
     */
    fun onItemClicked(item: Country) {
        uiEventChannel.trySend(CountryUiEvent.OpenMovieTopScreen(item))
    }

    /**
     * Используется для получения стран по поисковой строке
     */
    private fun getCountries(searchString: String) {
        viewModelScope.launch {
            try {
                countriesStateFlow.emitLoading()
                interactor.getAllCountries(searchString)
                    .cachedIn(viewModelScope)
                    .collect { data ->
                        countriesStateFlow.emitSuccess(data)
                    }
            } catch (e: Exception) {
                countriesStateFlow.emitError(e)
            }
        }
    }
}