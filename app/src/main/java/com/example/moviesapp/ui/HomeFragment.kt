package com.example.moviesapp.ui
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.MainActivity
import com.example.moviesapp.adapter.MovieAdapter
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.model.Movies
import com.example.moviesapp.remote.EndPoints
import com.example.moviesapp.remote.RetrofitBuilder
import kotlinx.coroutines.launch
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        initView()
        return binding.root
    }

    private fun initView() {

        (requireActivity() as MainActivity).toolbarTextView.text = "Home"
        binding.progressBar.visibility = View.VISIBLE
        getMovies()
    }

    private fun getMovies() {
        try {
            lifecycleScope.launch {
                val api = RetrofitBuilder.retrofit.create(EndPoints::class.java)
                val response = api.getMovie()
                when(response.code()){
                    200 -> {
                        val listOfMovies = response.body()
                        Log.i("movieTest",listOfMovies.toString())
                        initMovieAdapter(listOfMovies)
                    }
                }
            }

        }catch (e : IOException){
            Log.i("exception",e.message.toString())
        }
    }

    private fun initMovieAdapter(listOfMovies: List<Movies>?) {
        listOfMovies?.let {  // check There is data or not !!
            binding.progressBar.visibility = View.GONE
            val movieAdapter = MovieAdapter(it,requireContext())
            binding.movieRV.adapter = movieAdapter
        }

    }


}