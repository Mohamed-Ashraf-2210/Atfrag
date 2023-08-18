package com.example.moviesapp.remote

import com.example.moviesapp.model.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoints {
    @GET("movies")
    suspend fun getMovie() : Response<List<Movies>>

    @GET("movies/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") movieId : String ) : Response<Movies>
}