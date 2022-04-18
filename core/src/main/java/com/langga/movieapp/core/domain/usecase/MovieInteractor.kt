package com.langga.movieapp.core.domain.usecase

import com.langga.movieapp.core.data.source.Resource
import com.langga.movieapp.core.domain.model.Movie
import com.langga.movieapp.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getAllMovie(query: String): Flow<Resource<List<Movie>>> =
        movieRepository.getAllMovie(query)

    override fun getFavoriteMovie(): Flow<List<Movie>> = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)

    override fun searchMovie(query: String): Flow<List<Movie>> {
        return movieRepository.searchMovie(query)
    }

}