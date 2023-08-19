package com.example.moviesapp.remote

import android.graphics.Movie
import android.icu.text.IDNA
import com.example.moviesapp.model.Category
import com.example.moviesapp.model.Movies
import com.example.moviesapp.model.User
import com.example.moviesapp.request.SigninRequest
import com.example.moviesapp.request.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EndPoints {
    @POST("signup")
    suspend fun signUp(@Body signUpRequest: SignupRequest):Response<User>

    @POST("signin")
    suspend fun signIn(@Body signInRequest: SigninRequest):Response<IDNA.Info>

    @GET("movies")
    suspend fun getMovie() : Response<List<Movies>>

    @GET("movies/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") movieId : String ) : Response<Movies>

    @GET("categories")
    suspend fun getCategories() : Response<List<Category>>

    @GET("categories/{category_id}/movies")
    suspend fun getMoviesByCategoryId(@Path("category_id") categoryId :String) : Response<List<Movies>>

}