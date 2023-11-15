package com.example.afisha.data.remote.paging

import com.example.afisha.base.data.BasePagingSource
import com.example.afisha.data.remote.api.AfishaApi
import com.example.afisha.data.remote.entity.RemoteMovie
import com.example.afisha.domain.model.Movie

class MovieTopPagingSource(
    private val afishaApi: AfishaApi,
    private val country: String
) : BasePagingSource<RemoteMovie, Movie>(
    { afishaApi.getMoviesSortedByRating(it, country) }
)