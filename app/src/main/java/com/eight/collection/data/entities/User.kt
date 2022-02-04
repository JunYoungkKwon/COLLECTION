package com.eight.collection.data.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("ID") val id: String,
    @SerializedName("password") val password: String
    //@SerializedName("name") val name: String
)