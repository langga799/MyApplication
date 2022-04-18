package com.langga.movieapp.core.data.source.remote.network

import com.langga.movieapp.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie")
    suspend fun getDataMovies(
        @Query("api_key") apiKey: String,
    ): ListMovieResponse
}