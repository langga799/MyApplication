package com.langga.movieapp.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entity")
data class MovieEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var idMovie: Int,

    @ColumnInfo(name = "title")
    var originalTitle: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "popularity")
    var popularity: Double,

    @ColumnInfo(name = "poster")
    var posterPath: String,

    @ColumnInfo(name = "backdrop")
    var backdropPath: String,

    @ColumnInfo(name = "date")
    var releaseDate: String,

    @ColumnInfo(name = "vote")
    var voteAverage: Double,

    @ColumnInfo
    var isFavorite: Boolean = false,
)