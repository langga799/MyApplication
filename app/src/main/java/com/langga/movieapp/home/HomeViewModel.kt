package com.langga.movieapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.langga.movieapp.core.data.source.Resource
import com.langga.movieapp.core.domain.model.Movie
import com.langga.movieapp.core.domain.usecase.MovieUseCase

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getAllMovie(query: String): LiveData<Resource<List<Movie>>> {
        return movieUseCase.getAllMovie(query).asLiveData()
    }
}