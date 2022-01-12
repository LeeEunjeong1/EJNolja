package com.example.ejnolja.model.retrofit

import com.example.ejnolja.model.models.JoinResponse
import com.example.ejnolja.model.models.LoginResponse
import com.example.ejnolja.model.models.RestByRegionResponse
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    @FormUrlEncoded
    @POST("/auth/login")
    fun loginRequest(
        @Field("id") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("/auth/join")
    fun joinRequest(
        @Body params: HashMap<Any,Any>
    ): Call<JoinResponse>

    @GET("/rest/region")
    fun getRestByRegion(
        @Query("areaName") areaName:String,
        @Query("numOfRows") numOfRows:String,
        @Query("pageNo") pageNo:String
    ): Call<RestByRegionResponse>
}