package com.langga.movieapp.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.langga.movieapp.core.domain.model.Movie
import com.langga.movieapp.core.domain.usecase.MovieUseCase

class SearchViewModel(private val useCase: MovieUseCase) : ViewModel() {
    fun searchMovie(query: String): LiveData<List<Movie>> {
        return useCase.searchMovie(query).asLiveData()
    }
}