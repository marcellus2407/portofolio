package com.dicoding.picodiploma.storyapp


data class LoginResponse(
    val loginResult: LoginResult,
    val error: Boolean,
    val message: String
)
data class LoginResult(
    val userId : String,
    val name : String,
    val token : String
)