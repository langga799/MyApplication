package com.langga.movieapp.core.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.sqlite.db.SimpleSQLiteQuery
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.langga.movieapp.core.R

fun Context.loadImage(url: String, imageView: ImageView) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading_image)
            .error(R.drawable.ic_error_image))
        .into(imageView)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

object Sorting {
    const val NEWEST = "new"
    const val OLDEST = "old"
    const val RATING = "popular"

    fun sortListMovie(sort: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM movie_entity ")
        when (sort) {
            NEWEST -> simpleQuery.append("ORDER BY date DESC")
            OLDEST -> simpleQuery.append("ORDER BY date ASC")
            RATING -> simpleQuery.append("ORDER BY vote DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

}


