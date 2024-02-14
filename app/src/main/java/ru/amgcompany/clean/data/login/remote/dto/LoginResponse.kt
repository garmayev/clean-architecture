package ru.amgcompany.clean.data.login.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("id") val id : Int ?= null,
    @SerializedName("username") val username : String ?= null,
    @SerializedName("email") val email : String ?= null,
    @SerializedName("auth_key") val authKey : String ?= null
)