package ru.amgcompany.clean.domain.login.entity

data class LoginEntity (
    var id : Int,
    var username: String,
    var email: String,
    var authKey: String
)