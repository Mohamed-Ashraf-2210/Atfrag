package com.example.moviesapp.model

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("_id"              ) var Id               : String?             = null,
    @SerializedName("name"             ) var name             : String?             = null,
    @SerializedName("description"      ) var description      : String?             = null,
    @SerializedName("rating"           ) var rating           : Int?                = null,
//    @SerializedName("category"         ) var category         : ArrayList<Category> = arrayListOf(),
    @SerializedName("duration"         ) var duration         : String?             = null,
    @SerializedName("author"           ) var author           : String?             = null,
    @SerializedName("dateOfProduction" ) var dateOfProduction : String?             = null,
    @SerializedName("numberOfWatch"    ) var numberOfWatch    : Int?                = null,
    @SerializedName("image"            ) var image            : String?             = null,
    @SerializedName("video"            ) var video            : String?             = null,
    @SerializedName("__v"              ) var _v               : Int?                = null
)
