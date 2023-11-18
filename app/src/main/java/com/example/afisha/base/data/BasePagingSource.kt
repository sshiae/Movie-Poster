package com.example.afisha.base.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.afisha.common.DomainMapper
import com.example.afisha.data.remote.entity.response.ApiDocsResponse

/**
 * Базовый PagingSource класс для выполнения сетевых запросов посредством Retorift
 */
abstract class BasePagingSource<ValueDto : DomainMapper<Value>, Value : Any>(
    private val request: suspend (position: Int) -> ApiDocsResponse<ValueDto>,
) : PagingSource<Int, Value>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        val position = params.key ?: BASE_STARTING_PAGE_INDEX

        return try {
            val response = request(position)
            LoadResult.Page(
                data = response.docs.map { it.mapToDomain() },
                prevKey = null,
                nextKey = if (response.page < response.pages) response.page + 1 else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val BASE_STARTING_PAGE_INDEX = 1
    }
}