package com.langga.movieapp.detail

import androidx.lifecycle.ViewModel
import com.langga.movieapp.core.domain.model.Movie
import com.langga.movieapp.core.domain.usecase.MovieUseCase

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setFavoriteMovie(movie: Movie, newState: Boolean) {
        movieUseCase.setFavoriteMovie(movie, newState)
    }
}