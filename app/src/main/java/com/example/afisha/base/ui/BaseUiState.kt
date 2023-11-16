package com.example.afisha.base.ui

/**
 * Базовый UI состояние с оригинальной сущность [original]
 */
abstract class BaseUiState<T> {

    /**
     * оригинальная сущность, по которой строится [BaseUiState]
     */
    abstract val original: T
}