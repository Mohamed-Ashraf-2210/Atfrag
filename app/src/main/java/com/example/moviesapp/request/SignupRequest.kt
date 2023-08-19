package com.example.moviesapp.request

data class SignupRequest(
    val name:String,
    val age :Int,
    val email:String,
    val password :String
)