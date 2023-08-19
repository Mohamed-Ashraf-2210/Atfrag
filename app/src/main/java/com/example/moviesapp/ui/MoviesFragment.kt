package com.example.moviesapp.ui

import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.moviesapp.databinding.FragmentMoviesBinding
import com.example.moviesapp.remote.EndPoints
import com.example.moviesapp.remote.RetrofitBuilder
import com.example.moviesapp.R
import com.example.moviesapp.adapter.MovieAdapter
import com.example.moviesapp.model.Movies
import kotlinx.coroutines.launch
import java.io.IOException


class MoviesFragment : Fragment() {

    private  lateinit var binding : FragmentMoviesBinding
    private val args : MoviesFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater,container,false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        val categoryId = args.categoryId
        categoryId?.let {
            getMoviesByCategoryId(it)
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun getMoviesByCategoryId(categoryId: String) {
        try {

            lifecycleScope.launch{
                val api = RetrofitBuilder.retrofit.create(EndPoints::class.java)
                val response = api.getMoviesByCategoryId(categoryId)
                when(response.code()){
                    200 -> {
                        binding.progressBar.visibility= View.GONE
                        val listOfMovies = response.body()
                        listOfMovies?.let{
                            initMoviesAdapter(listOfMovies)
                        }
                    }
                }
            }
        }catch (e: IOException){
            Log.i("exception",e.message.toString())
        }

    }

    private fun initMoviesAdapter(listOfMovies: List<Movies>) {
        val moviesAdapter = MovieAdapter(listOfMovies,requireContext())
        binding.movie.adapter = moviesAdapter
    }


}