package com.langga.movieapp.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.langga.movieapp.R
import com.langga.movieapp.core.ui.MovieAdapter
import com.langga.movieapp.core.utils.gone
import com.langga.movieapp.core.utils.toast
import com.langga.movieapp.core.utils.visible
import com.langga.movieapp.databinding.FragmentSearchBinding
import com.langga.movieapp.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchMovie.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery(newText.orEmpty())
                return true
            }

        })


        movieAdapter = MovieAdapter()
        binding.rvSearchMovie.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = movieAdapter
            setHasFixedSize(true)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val navigation =
                        activity?.findViewById<BottomNavigationView>(R.id.nav_view)!!
                    when {
                        dy > 0 && navigation.isShown -> {
                            navigation.gone()
                        }
                        dy < 0 -> {
                            navigation.visible()
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }

            })
        }

    }


    private fun searchQuery(text: String) {
        val inputText = "%$text%"
        viewModel.searchMovie(inputText).observe(viewLifecycleOwner) { resultSearching ->
            movieAdapter.setDataMovies(resultSearching)
            when {
                resultSearching.isNullOrEmpty() -> {
                    binding.apply {
                        rvSearchMovie.gone()
                        noResult.visible()
                        resources.getString(R.string.no_result_message).toast(requireActivity())
                    }
                }
                else -> {
                    binding.apply {
                        rvSearchMovie.visible()
                        noResult.gone()
                    }
                }
            }
        }

        movieAdapter.onItemClick = { clickItem ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, clickItem)
            startActivity(intent)
        }


    }


}

