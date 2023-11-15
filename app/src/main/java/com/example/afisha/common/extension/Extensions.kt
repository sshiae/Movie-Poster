package com.example.afisha.common.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Используется для установки заголовка для фрагмента
 */
fun Fragment.setTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar?.show()
    title.let {
        (activity as AppCompatActivity).supportActionBar?.title = it
    }
}

/**
 * Используется для установки видимости кнопки "Назад"
 */
fun Fragment.setDisplayHomeAsUpEnabled(enabled: Boolean) {
    val actionBar = (activity as? AppCompatActivity)?.supportActionBar
    actionBar?.setDisplayHomeAsUpEnabled(enabled)
}

/**
 * Используется для подписки на [FLow] с компонентами для отсеивания одинаковых значений
 */
suspend fun Flow<CombinedLoadStates>.collectDistinct(block: (LoadState) -> Unit) {
    map { it.refresh }.distinctUntilChanged().collect(block)
}