package com.example.afisha.ui.main

import androidx.lifecycle.viewModelScope
import com.example.afisha.base.ui.BaseViewModel
import com.example.afisha.common.LoadableData
import com.example.afisha.common.emitError
import com.example.afisha.common.emitLoading
import com.example.afisha.common.emitSuccess
import com.example.afisha.domain.interactor.AfishaInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    private val interactor: AfishaInteractor,
    @Assisted private val readCountriesJson: (() -> String)
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(readCountriesJson: (() -> String)): MainViewModel
    }

    /**
     * [Flow] for the initial initialization of the application
     */
    private val initStateFlow: MutableStateFlow<LoadableData<Boolean>> =
        MutableStateFlow(LoadableData.Loading())
    val initState = initStateFlow.asStateFlow()

    override fun firstLoad() {
        viewModelScope.launch {
            try {
                initStateFlow.emitLoading()

                if (!interactor.existsCountries()) {
                    interactor.insertCountries(readCountriesJson.invoke())
                    initStateFlow.emitSuccess(true)
                } else {
                    initStateFlow.emitSuccess(false)
                }

            } catch (e: Exception) {
                initStateFlow.emitError(e)
            }
        }
    }
}