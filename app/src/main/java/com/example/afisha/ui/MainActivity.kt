package com.example.afisha.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.afisha.AfishaScreens
import com.example.afisha.R
import com.example.afisha.base.ui.BaseFragment
import com.example.afisha.base.ui.BaseViewModel.Companion.provideFactory
import com.example.afisha.common.LoadableData
import com.example.afisha.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var factory: MainViewModel.Factory

    private lateinit var binding: ActivityMainBinding
    private lateinit var loadingConstraintLayout: ConstraintLayout

    private val navigator = AppNavigator(this, R.id.container)
    private val viewModel: MainViewModel by viewModels {
        provideFactory {
            factory.create {
                readJsonFile(R.raw.countries)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        loadingConstraintLayout = findViewById(R.id.loading)
        subscribe()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onSupportNavigateUp(): Boolean {
        router.exit()
        return super.onSupportNavigateUp()
    }

    private fun subscribe() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch { viewModel.initState.collect(::onViewState) }
            }
        }
    }

    private fun onViewState(state: LoadableData<Boolean>) {
        when (state) {
            is LoadableData.Success -> {
                viewModel.hideLoading()
                router.newChain(AfishaScreens.CountryScreen())
            }
            is LoadableData.Loading -> viewModel.showLoading()
            is LoadableData.Error -> {
                viewModel.hideLoading()
                showErrorMessage(state.error.message!!)
            }
        }
    }

    private fun readJsonFile(resourceId: Int): String {
        val inputStream = resources.openRawResource(resourceId)
        return inputStream.bufferedReader().use { it.readText() }
    }

    private fun showErrorMessage(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(ERROR_DIALOG_TITLE)
            .setMessage(message)
            .setPositiveButton(BaseFragment.BTN_OK_TEXT) { dlg, _ -> dlg.dismiss() }
            .show()
    }

    companion object {
        private const val ERROR_DIALOG_TITLE = "Ошибка"
    }
}