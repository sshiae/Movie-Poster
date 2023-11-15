package com.example.afisha.ui.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afisha.AfishaScreens
import com.example.afisha.R
import com.example.afisha.base.ui.BaseFragment
import com.example.afisha.base.ui.BaseViewModel.Companion.provideFactory
import com.example.afisha.common.LoadableData
import com.example.afisha.common.extension.collectDistinct
import com.example.afisha.common.extension.setDisplayHomeAsUpEnabled
import com.example.afisha.common.extension.setTitle
import com.example.afisha.databinding.MovieTopActivityBinding
import com.example.afisha.domain.model.Country
import com.example.afisha.domain.model.Movie
import com.example.afisha.ui.top.uiEvent.MovieUiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieTopFragment(
    private val country: Country
) : BaseFragment() {

    @Inject
    lateinit var factory: MovieTopViewModel.Factory

    private lateinit var binding: MovieTopActivityBinding
    private lateinit var adapter: MovieTopAdapter

    override val viewModel: MovieTopViewModel by viewModels {
        provideFactory {
            factory.create(country)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieTopActivityBinding.inflate(layoutInflater)
        adapter = MovieTopAdapter(viewModel::onItemClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAdapter()
        setTitle(getString(R.string.movie_top_fragment_title))
        setDisplayHomeAsUpEnabled(true)
    }

    override fun subscribe() {
        super.subscribe()
        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch { viewModel.topOfMoviesState.collect(::onViewState) }
                    launch { viewModel.uiEventFlow.collect(::onEvent) }
                    launch { adapter.loadStateFlow.collectDistinct(::onLoadState) }
                }
            }
        }
    }

    private fun onViewState(state: LoadableData<PagingData<Movie>>) {
        when (state) {
            is LoadableData.Success -> {
                viewModel.hideLoading()
                adapter.submitData(viewLifecycleOwner.lifecycle, state.value)
            }
            is LoadableData.Loading -> viewModel.showLoading()
            is LoadableData.Error -> {
                viewModel.hideLoading()
                showMessage(state.error.message!!, MessageType.ERROR)
            }
        }
    }

    private fun onEvent(uiEvent: MovieUiEvent) {
        when (uiEvent) {
            is MovieUiEvent.OpenMovieDetailFragment ->
                router.navigateTo(AfishaScreens.MovieDetailScreen(uiEvent.movie))
        }
    }

    private fun onLoadState(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                viewModel.showLoading()
                hidePlug()
            }
            is LoadState.NotLoading -> {
                viewModel.hideLoading()
                if (adapter.itemCount == 0) {
                    showPlug()
                } else {
                    hidePlug()
                }
            }
            is LoadState.Error -> {
                viewModel.hideLoading()
                showPlug()
                showMessage(loadState.error.message!!, MessageType.ERROR)
            }
        }
    }

    private fun showPlug() {
        requireView().findViewById<ConstraintLayout>(R.id.plug)?.let {
            it.isVisible = true
        }
    }

    private fun hidePlug() {
        requireView().findViewById<ConstraintLayout>(R.id.plug)?.let {
            it.isVisible = false
        }
    }

    private fun bindAdapter() {
        with (binding.list) {
            rvList.adapter = adapter
            rvList.layoutManager = LinearLayoutManager(rvList.context)
        }
    }
}