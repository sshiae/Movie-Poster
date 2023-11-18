package com.example.afisha.base.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.example.afisha.common.DomainMapper
import com.example.afisha.data.remote.api.AfishaApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Абстрактный класс для реализации репозиториев
 */
abstract class BaseRepository {

    /**
     * Базовая конфигурация для PagingLibrary
     */
    private val config = PagingConfig(
        PAGE_SIZE,
        PAGE_SIZE,
        ENABLE_PLACEHOLDERS,
        PAGE_SIZE * 3,
        Int.MAX_VALUE,
        Int.MIN_VALUE
    )

    /**
     * Выполнение сетевых запросов с параметрами по умолчанию для [BasePagingSource]
     */
    protected fun <ValueDto : DomainMapper<Value>, Value : Any> doPagingNetworkRequest(
        pagingSource: BasePagingSource<ValueDto, Value>
    ): Flow<PagingData<Value>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                pagingSource
            }
        )
            .flow
    }

    /**
     * Выполнение сетевых запросов с параметрами по умолчанию для [PagingSource]
     */
    protected fun <ValueDto : DomainMapper<Value>, Value : Any> doPagingRequest(
        pagingSource: PagingSource<Int, ValueDto>
    ): Flow<PagingData<Value>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                pagingSource
            }
        )
            .flow
            .map { items -> items.map { it.mapToDomain() } }
    }

    companion object {
        private const val PAGE_SIZE = AfishaApi.DEFAULT_PAGE_SIZE
        private const val ENABLE_PLACEHOLDERS = true
    }
}