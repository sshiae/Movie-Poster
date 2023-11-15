package com.example.afisha.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Базовая реализация [ViewModel]
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * [Flow] Состояние для строки поиска
     */
    private val searchStringStateFlow: MutableStateFlow<String> = MutableStateFlow(DEFAULT_SEARCH_STATE)
    val searchStringState
        get() = searchStringStateFlow.asStateFlow()

    /**
     * [Flow] Состояние для индикатора загрузки
     */
    private val loadingStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(DEFAULT_LOADING_STATE)
    val loadingState
        get() = loadingStateFlow.asStateFlow()

    /**
     * Используется для показа состояния загрузки
     */
    fun showLoading() {
        loadingStateFlow.update { true }
    }

    /**
     * Используется для скрытия состояния загрузки
     */
    fun hideLoading() {
        loadingStateFlow.update { false }
    }

    /**
     * Используется для установки состояние строки поиска
     */
    fun setSearchString(searchString: String) {
        searchStringStateFlow.update { searchString }
    }

    /**
     * Первоначальная загрузка данных
     */
    abstract fun firstLoad()

    companion object {
        const val DEFAULT_SEARCH_STATE = ""
        const val DEFAULT_LOADING_STATE = true

        /**
         * Используется для предоставления фабрики
         */
        fun <T : ViewModel> provideFactory(
            creator: () -> T
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return creator() as T
            }
        }
    }
}