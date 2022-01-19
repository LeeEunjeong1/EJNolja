package com.example.ejnolja.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ejnolja.model.models.Rest
import com.example.ejnolja.model.models.RestByRegionResponse
import com.example.ejnolja.model.retrofit.MainRepository
import com.example.ejnolja.model.retrofit.Responses
import com.example.ejnolja.model.retrofit.RetrofitManager
import com.example.ejnolja.utils.UserPreferences
import com.example.ejnolja.view.main.rest.MainRestFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.ByteString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRestViewModel(private val repository: MainRepository) :ViewModel() {

    private val _isSuccess: MutableLiveData<RestByRegionResponse> = MutableLiveData()
    val isSuccess: LiveData<RestByRegionResponse> get() = _isSuccess

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> get() = _isError


    fun getRestByRegion(areaName: String, numOfRows: Int, pageNo: Int){

        val response = repository.getRestByRegion(areaName,numOfRows,pageNo)
        response?.enqueue(object : Callback<RestByRegionResponse>{
            override fun onFailure(call: Call<RestByRegionResponse>, t: Throwable) {
                _isError.postValue("error")
            }

            override fun onResponse(call: Call<RestByRegionResponse>,response: Response<RestByRegionResponse>) {
               _isSuccess.postValue(response.body())
            }
        })
    }

    fun load(){
        getRestByRegion("서울",MainRestFragment.restPageSize,MainRestFragment.restPage)
    }

}