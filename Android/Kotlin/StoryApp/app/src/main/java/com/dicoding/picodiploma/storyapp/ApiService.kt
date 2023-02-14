package com.dicoding.picodiploma.storyapp

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService{
    @GET("stories")
    fun getALLStories(
        @Header("Authorization") authHeader: String
    ): Call<StoriesResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email")email:String,
        @Field("password")password:String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("name")name:String,
        @Field("email")email:String,
        @Field("password")password:String

    ): Call<RegisterResponse>
    @Multipart
    @POST("stories")
    fun ImageUpload(
        @Header("Authorization") header: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<UploadFileResponse>

}