package com.example.ejnolja.model.retrofit

class MainRepository constructor(private val retrofitClient: RetrofitClient) {

    companion object{
        val instance = RetrofitManager()
    }

    private val iRetrofit: RetrofitInterface? = retrofitClient.getClient()?.create(RetrofitInterface::class.java)

    fun getRestByRegion(areaName:String,numOfRows:Int,pageNo:Int) = iRetrofit?.getRestByRegion(areaName,numOfRows,pageNo)

}