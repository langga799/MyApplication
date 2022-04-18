package com.langga.movieapp.favorite.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.langga.movieapp.core.data.source.Resource
import com.langga.movieapp.core.ui.MovieAdapter
import com.langga.movieapp.detail.DetailActivity
import com.langga.movieapp.favorite.R
import com.langga.movieapp.favorite.databinding.FragmentFavoriteBinding
import com.langga.movieapp.favorite.di.favoriteModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        val movieAdapter = MovieAdapter()
        binding.rvMovieFavorite.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            setHasFixedSize(true)
            adapter = movieAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val navigation = activity?.findViewById<BottomNavigationView>(com.langga.movieapp.R.id.nav_view)!!
                    when {
                        dy > 0 && navigation.isShown -> {
                            navigation.visibility = View.GONE
                        }
                        dy < 0 -> {
                            navigation.visibility = View.VISIBLE
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }

            })
        }

        viewModel.movie.observe(viewLifecycleOwner){ favoriteMovie ->
            if (favoriteMovie != null){
                movieAdapter.setDataMovies(favoriteMovie)
            }
        }

        movieAdapter.onItemClick = { clickItem ->
            activity?.startActivity(Intent(activity, DetailActivity::class.java)
                .putExtra(DetailActivity.EXTRA_DATA, clickItem)
            )
        }

    }


}