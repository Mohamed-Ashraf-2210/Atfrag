package com.example.moviesapp.model

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("_id"             ) var Id              : String?           = null,
    @SerializedName("name"            ) var name            : String?           = null,
    @SerializedName("email"           ) var email           : String?           = null,
    @SerializedName("password"        ) var password        : String?           = null,
    @SerializedName("age"             ) var age             : Int?              = null,
    @SerializedName("__v"             ) var _v              : Int?              = null,
    @SerializedName("favouriteMovies" ) var favouriteMovies : ArrayList<String> = arrayListOf()
)