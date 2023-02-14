package com.dicoding.picodiploma.storyapp

data class StoriesResponse(
    val listStory: List<AllStory>,
    val error : Boolean,
    val message : String
)
data class AllStory(
    val id : String,
    val name : String,
    val description : String,
    val photoUrl : String,
    val createAt : String,
    val lat : Double,
    val lon : Double
)