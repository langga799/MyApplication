package com.langga.movieapp.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.langga.movieapp.R
import com.langga.movieapp.core.data.source.Resource
import com.langga.movieapp.core.ui.MovieAdapter
import com.langga.movieapp.core.utils.Sorting
import com.langga.movieapp.core.utils.gone
import com.langga.movieapp.core.utils.toast
import com.langga.movieapp.core.utils.visible
import com.langga.movieapp.databinding.FragmentHomeBinding
import com.langga.movieapp.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val adapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadingHome.gone()
        binding.apply {
            rvMovie.layoutManager = GridLayoutManager(requireActivity(), 2)
            rvMovie.setHasFixedSize(true)
            rvMovie.adapter = adapter

            rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val navigation = activity?.findViewById<BottomNavigationView>(R.id.nav_view)!!
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


        val spinner = binding.spinnerFilterMovie
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.filter_item,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long,
            ) {
                if (view != null) {
                    when (position) {
                        0 -> {
                            getMovies(Sorting.NEWEST)
                        }
                        1 -> {
                            getMovies(Sorting.OLDEST)
                        }
                        2 -> {
                            getMovies(Sorting.RATING)
                        }
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        onItemCLickMovie()
    }


    private fun getMovies(query: String) {
        viewModel.getAllMovie(query).observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> binding.loadingHome.visible()
                    is Resource.Success -> {
                        adapter.setDataMovies(movie.data)
                        binding.loadingHome.gone()
                    }
                    is Resource.Error -> {
                        binding.loadingHome.gone()
                        resources.getString(R.string.error_message).toast(requireActivity())
                    }
                }
            }
        }
    }


    private fun onItemCLickMovie() {
        adapter.onItemClick = { clickItem ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, clickItem)
            startActivity(intent)
        }
    }


}