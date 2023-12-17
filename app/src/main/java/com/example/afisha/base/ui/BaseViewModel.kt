package com.example.afisha.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Base implementation of [ViewModel].
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * [Flow] State for the search string.
     */
    private val searchStringStateFlow: MutableStateFlow<String> = MutableStateFlow(DEFAULT_SEARCH_STATE)
    val searchStringState = searchStringStateFlow.asStateFlow()

    /**
     * [Flow] State for the loading indicator.
     */
    private val loadingStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(DEFAULT_LOADING_STATE)
    val loadingState = loadingStateFlow.asStateFlow()

    /**
     * Used to show loading state.
     */
    fun showLoading() {
        loadingStateFlow.update { true }
    }

    /**
     * Used to hide loading state.
     */
    fun hideLoading() {
        loadingStateFlow.update { false }
    }

    /**
     * Used to set the search string state.
     */
    fun setSearchString(searchString: String) {
        searchStringStateFlow.update { searchString }
    }

    /**
     * Initial data loading.
     */
    abstract fun firstLoad()

    companion object {
        const val DEFAULT_SEARCH_STATE = ""
        const val DEFAULT_LOADING_STATE = true

        /**
         * Used to provide a factory.
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