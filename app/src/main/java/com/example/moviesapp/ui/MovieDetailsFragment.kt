package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.FragmentMovieDetailsBinding
import com.example.moviesapp.model.Movies
import com.example.moviesapp.remote.EndPoints
import com.example.moviesapp.remote.RetrofitBuilder
import kotlinx.coroutines.launch
import java.io.IOException


class MovieDetailsFragment : Fragment() {
    private lateinit var  binding : FragmentMovieDetailsBinding
    //to get movie by id { safe args }
    private val args : MovieDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater,container,false)
        intiView()
        return binding.root
    }

    private fun intiView() {
        args.movieDetails?.let {
            getMovieById(it)
        }

    }

    private fun getMovieById(id: String) {
        try {
            lifecycleScope.launch {
                val api = RetrofitBuilder.retrofit.create(EndPoints::class.java)
                val response = api.getMovieById(id)
                when(response.code()){
                    200 -> {
                        val movieDetail = response.body()
                        setViewByMovieDeatails(movieDetail)
                    }
                    404 -> {
                        Toast.makeText(requireContext(),response.errorBody()?.string(),Toast.LENGTH_SHORT).show()
                   }

                }
            }
        }catch (e : IOException){
            Log.i("exception",e.message.toString())
        }


    }

    private fun setViewByMovieDeatails(movieDetail: Movies?) {
        Glide.with(requireContext()).load(movieDetail?.image).into(binding.movieImgDetails)
        binding.movieTitleDetails.text = movieDetail?.name
        binding.movieDataDetails.text = movieDetail?.dateOfProduction
        binding.durationMovie.text = movieDetail?.duration
        binding.numberOfWatch.text = movieDetail?.numberOfWatch.toString()
        binding.movieDes.text = movieDetail?.description
        binding.movieAuthor.text = movieDetail?.author
    }


//    private fun intiView() {
//        val movieId = args.movieDetails
//        movieId?.let {
//            getMovieById(it)
//        }
//
//    }
//
//    private fun getMovieById(movieId: String) {
//        try {
//            lifecycleScope.launch {
//                val api = RetrofitBuilder.retrofit.create(EndPoints::class.java)
//                val respons = api.getMovieById(movieId)
//                when(respons.code()){
//                    200 -> {
//                        val listOfMovies = respons.body()
//                        listOfMovies?.let {
//                            intiMovieAdapter(listOfMovies)
//                        }
//                    }
//                }
//            }
//        }catch (e :IOException){
//            Log.i("exception",e.message.toString())
//        }
//    }
//
//    private fun intiMovieAdapter(listOfMovies: List<Movies>) {
//        val movieAdapter = MovieAdapter(listOfMovies,requireContext())
//    }
//

}