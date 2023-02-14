package com.dicoding.picodiploma.storyapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val token: String,
    val isLogin: Boolean
) : Parcelable