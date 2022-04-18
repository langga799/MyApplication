package com.langga.movieapp.core.utils

import com.langga.movieapp.core.data.source.local.entity.MovieEntity
import com.langga.movieapp.core.data.source.remote.response.ResultMovie
import com.langga.movieapp.core.domain.model.Movie

object DataMapper {

    fun mapResponseToEntities(input: List<ResultMovie>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                idMovie = it.id,
                overview = it.overview,
                originalTitle = it.title,
                popularity = it.popularity,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }


    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                idMovie = it.idMovie,
                overview = it.overview,
                originalTitle = it.originalTitle,
                popularity = it.popularity,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                isFavorite = it.isFavorite
            )
        }


    fun mapDomainToEntity(input: Movie) = MovieEntity(
        idMovie = input.idMovie,
        overview = input.overview,
        originalTitle = input.originalTitle,
        popularity = input.popularity,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        isFavorite = input.isFavorite
    )
}