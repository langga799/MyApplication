package com.langga.movieapp.core.data.source.local

import com.langga.movieapp.core.data.source.local.entity.MovieEntity
import com.langga.movieapp.core.data.source.local.room.DaoMovie
import com.langga.movieapp.core.utils.Sorting
import kotlinx.coroutines.flow.Flow


class LocalDataSource constructor(private val daoMovie: DaoMovie) {

    fun getAllMovie(query: String): Flow<List<MovieEntity>> {
        val queryText = Sorting.sortListMovie(query)
        return daoMovie.getAllMovie(queryText)
    }

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = daoMovie.getFavoriteMovie()

    suspend fun insertMovie(listMovie: List<MovieEntity>) = daoMovie.insertMovie(listMovie)

    fun setFavoriteMovie(movieEntity: MovieEntity, state: Boolean) {
        movieEntity.isFavorite = state
        daoMovie.updateFavoriteMovie(movieEntity)
    }

    fun searchMovie(query: String): Flow<List<MovieEntity>> {
        return daoMovie.searchMovie(query)
    }

}