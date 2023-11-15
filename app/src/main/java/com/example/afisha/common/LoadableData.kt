package com.example.afisha.common

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Интерфейс для обретки загружаемых данных
 */
sealed interface LoadableData<T> {

    /**
     * Данные текущего состояние
     */
    val value: T?

    /**
     * Допуступны ли данные текущего состояние
     */
    val hasValue: Boolean
        get() = (value != null)

    /**
     * Состояние загрузки
     *
     * @param value - данные последнего статуса
     */
    data class Loading<T>(
        override val value: T? = null
    ) : LoadableData<T>

    /**
     * Состояние успешной загрузки данных [value]
     */
    data class Success<T>(
        override val value: T
    ) : LoadableData<T>

    /**
     * Состояние не успешной загрузки данных с ошибкой [error]
     *
     * @param value - данные последнего статуса
     */
    data class Error<T>(
        override val value: T? = null,
        val error: Exception
    ) : LoadableData<T>
}

/**
 * Испускает состояние загрузки с опциональными данными [data]
 */
fun <T> MutableStateFlow<LoadableData<T>>.emitLoading(data: T? = null) {
    value = LoadableData.Loading(data)
}

/**
 * Испускает состояние успешной загрузки с данными [data]
 */
fun <T> MutableStateFlow<LoadableData<T>>.emitSuccess(data: T) {
    value = LoadableData.Success(data)
}

/**
 * Испускает состояние не успешной загрузки с опциональными данными [data] и ошибкой [error]
 */
fun <T> MutableStateFlow<LoadableData<T>>.emitError(error: Exception, data: T? = null) {
    value = LoadableData.Error(data, error)
}