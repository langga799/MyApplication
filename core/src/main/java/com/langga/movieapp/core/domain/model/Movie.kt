package com.langga.movieapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val idMovie: Int,
    val overview: String,
    val originalTitle: String,
    val popularity: Double,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val isFavorite: Boolean,
) : Parcelable