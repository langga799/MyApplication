package com.langga.movieapp.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.langga.movieapp.R
import com.langga.movieapp.core.domain.model.Movie
import com.langga.movieapp.core.utils.loadImage
import com.langga.movieapp.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailMovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(FLAG_TRANSLUCENT_NAVIGATION, FLAG_TRANSLUCENT_NAVIGATION)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val getDetailMovieFromItemCLick = intent?.getParcelableExtra<Movie>(EXTRA_DATA)
        showDetailMovie(getDetailMovieFromItemCLick)
    }


    private fun showDetailMovie(movie: Movie?) {
        movie?.apply {
            supportActionBar?.title = movie.originalTitle
            binding.apply {
                loadImage(POSTER_PATH + movie.posterPath, ivPosterDetail)
                loadImage(BACKGROUND_PATH + movie.backdropPath, ivBackgroundDetail)
                tvTitleDetail.text = movie.originalTitle
                tvDateDetail.text = movie.releaseDate
                tvPopularityDetail.text = movie.popularity.toString()
                tvRatingDetail.text = movie.voteAverage.toString()
                descriptionDetail.text = movie.overview
            }

            var statusFavorite = movie.isFavorite
            setIconFavorite(statusFavorite)
            binding.fabFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                viewModel.setFavoriteMovie(movie, statusFavorite)
                setIconFavorite(statusFavorite)
            }
        }
    }


    private fun setIconFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this,
                R.drawable.ic_favorite_fill))
        } else {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this,
                R.drawable.ic_favorite_border))
        }
    }


    companion object {
        const val EXTRA_DATA = "extra_data"
        const val POSTER_PATH = "https://image.tmdb.org/t/p/w500"
        const val BACKGROUND_PATH = "https://image.tmdb.org/t/p/w500"
    }
}