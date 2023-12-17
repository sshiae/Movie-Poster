package com.example.afisha.base.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.afisha.R
import com.github.terrakok.cicerone.Router
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Base implementation of [Fragment].
 */
abstract class BaseFragment : BaseChainScreenFragment() {

    /**
     * ViewModel
     */
    abstract val viewModel: BaseViewModel

    @Inject
    lateinit var router: Router

    private var searchText = DEFAULT_SEARCH_TEXT

    private val handler: Handler = Handler(Looper.getMainLooper())
    private val searchRunnable: Runnable = Runnable {
        viewModel.setSearchString(searchText)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        initializeSearchBar()
    }

    override fun onStart() {
        super.onStart()
        viewModel.firstLoad()
    }

    /**
     * Used to subscribe to [ViewModel].
     */
    protected open fun subscribe() {
        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch { viewModel.loadingState.collect(::onLoadingState) }
                }
            }
        }
    }

    /**
     * Initialize the search bar if it exists in the layout.
     */
    protected open fun initializeSearchBar() {
        requireView().findViewById<TextInputEditText>(R.id.searchBar)?.doAfterTextChanged {
            searchText = it?.toString() ?: DEFAULT_SEARCH_TEXT
            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, 1000L)
        }
    }

    /**
     * Used to display a message with a specific type.
     */
    protected open fun showMessage(message: String, type: MessageType) {
        MaterialAlertDialogBuilder(requireContext())
            .setIcon(getIconByType(type))
            .setTitle(getTitleByType(type))
            .setMessage(message)
            .setPositiveButton(BTN_OK_TEXT) { dlg, _ -> dlg.dismiss() }
            .show()
    }

    private fun onLoadingState(loading: Boolean) {
        requireView().findViewById<ConstraintLayout>(R.id.loading).isVisible = loading
    }

    private fun getTitleByType(type: MessageType): String {
        return when (type) {
            MessageType.ERROR -> ERROR_DIALOG_TITLE
            MessageType.WARNING -> WARNING_DIALOG_TITLE
            MessageType.INFO -> INFO_DIALOG_TITLE
        }
    }

    private fun getIconByType(type: MessageType): Int {
        return when (type) {
            MessageType.ERROR -> R.drawable.ic_close
            MessageType.WARNING -> R.drawable.ic_warning
            MessageType.INFO -> 0
        }
    }

    /**
     * Message types.
     */
    enum class MessageType {
        ERROR,
        INFO,
        WARNING
    }

    companion object {
        const val BTN_OK_TEXT = "OK"
        const val INFO_DIALOG_TITLE = "Message"
        const val ERROR_DIALOG_TITLE = "Error"
        const val WARNING_DIALOG_TITLE = "Warning"
        const val DEFAULT_SEARCH_TEXT = ""
    }
}