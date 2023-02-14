package com.dicoding.picodiploma.storyapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryList(
    var name: String? = null,
    var photo: String? = null,
    var description: String? = null
) : Parcelable