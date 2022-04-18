package com.langga.movieapp.core.domain.repository

import com.langga.movieapp.core.data.source.Resource
import com.langga.movieapp.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getAllMovie(query: String): Flow<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flow<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)

    fun searchMovie(query: String): Flow<List<Movie>>

}