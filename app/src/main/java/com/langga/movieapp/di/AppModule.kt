package com.langga.movieapp.di

import com.langga.movieapp.core.domain.usecase.MovieInteractor
import com.langga.movieapp.core.domain.usecase.MovieUseCase
import com.langga.movieapp.detail.DetailMovieViewModel
import com.langga.movieapp.home.HomeViewModel
import com.langga.movieapp.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}


val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
}