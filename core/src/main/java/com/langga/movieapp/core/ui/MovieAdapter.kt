package com.langga.movieapp.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.langga.movieapp.core.databinding.ItemListMovieBinding
import com.langga.movieapp.core.domain.model.Movie
import com.langga.movieapp.core.utils.loadImage

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val listMovie = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setDataMovies(newListMovie: List<Movie>?) {
        listMovie.apply {
            clear()
            addAll(newListMovie.orEmpty())
        }
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: ItemListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                tvTitle.text = movie.originalTitle
                tvReleaseDate.text = movie.releaseDate
                tvRating.text = movie.voteAverage.toString()
                itemView.context.loadImage(POSTER_PATH + movie.posterPath, ivPoster)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listMovie[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding =
            ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int = listMovie.size

    companion object {
        const val POSTER_PATH = "https://image.tmdb.org/t/p/w500"
    }


}