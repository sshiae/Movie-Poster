package com.example.afisha.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afisha.AfishaScreens
import com.example.afisha.R
import com.example.afisha.base.ui.BaseFragment
import com.example.afisha.base.ui.BaseViewModel.Companion.provideFactory
import com.example.afisha.common.LoadableData
import com.example.afisha.common.extension.collectDistinct
import com.example.afisha.common.extension.setDisplayHomeAsUpEnabled
import com.example.afisha.common.extension.setTitle
import com.example.afisha.databinding.CountryFragmentBinding
import com.example.afisha.domain.model.Country
import com.example.afisha.ui.country.uiEvent.CountryUiEvent
import com.example.afisha.ui.country.uiState.CountryState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CountryFragment : BaseFragment() {

    @Inject
    lateinit var factory: CountryViewModel.Factory

    private lateinit var binding: CountryFragmentBinding
    private lateinit var adapter: CountryAdapter

    override val viewModel: CountryViewModel by viewModels {
        provideFactory {
            factory.create()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CountryFragmentBinding.inflate(layoutInflater)
        adapter = CountryAdapter(viewModel::onItemClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAdapter()
        setTitle(getString(R.string.country_fragment_title))
        setDisplayHomeAsUpEnabled(false)
    }

    override fun subscribe() {
        super.subscribe()
         with(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch { viewModel.countriesState.collect(::onViewState) }
                    launch { viewModel.uiEventFlow.collect(::onUiEvent) }
                    launch { adapter.loadStateFlow.collectDistinct(::onLoadState) }
                }
            }
        }
    }

    private fun onViewState(state: LoadableData<PagingData<Country>>) {
        when (state) {
            is LoadableData.Success -> {
                viewModel.hideLoading()
                val mappedData = state.value.map { mapToCountryState(it) }
                adapter.submitData(viewLifecycleOwner.lifecycle, mappedData)
            }
            is LoadableData.Loading -> viewModel.showLoading()
            is LoadableData.Error -> {
                viewModel.hideLoading()
                showMessage(state.error.message!!, MessageType.ERROR)
            }
        }
    }

    private fun onLoadState(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> viewModel.showLoading()
            is LoadState.NotLoading -> viewModel.hideLoading()
            is LoadState.Error -> {
                viewModel.hideLoading()
                showMessage(loadState.error.message!!, MessageType.ERROR)
            }
        }
    }

    private fun onUiEvent(state: CountryUiEvent) {
        when (state) {
            is CountryUiEvent.OpenMovieTopScreen ->
                router.navigateTo(AfishaScreens.MovieTopScreen(state.country))
        }
    }

    private fun mapToCountryState(country: Country): CountryState {
        return CountryState(
            id = country.id,
            title = country.name,
            original = country
        )
    }

    private fun bindAdapter() {
        with (binding.list) {
            rvList.adapter = adapter
            rvList.layoutManager = LinearLayoutManager(rvList.context)
            val decoration = DividerItemDecoration(rvList.context, DividerItemDecoration.VERTICAL)
            rvList.addItemDecoration(decoration)
        }
    }
}