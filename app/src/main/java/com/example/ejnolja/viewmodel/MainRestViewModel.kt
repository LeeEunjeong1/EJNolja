package com.example.ejnolja.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ejnolja.model.retrofit.Responses
import com.example.ejnolja.model.retrofit.RetrofitManager
import com.example.ejnolja.utils.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainRestViewModel :ViewModel() {

    private val _isSuccess: MutableLiveData<Unit> = MutableLiveData()
    val isSuccess: LiveData<Unit> get() = _isSuccess

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> get() = _isError


    fun getRestByRegion(areaName: String, numOfRows: String, pageNo: String){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                RetrofitManager.instance.getRestByRegion(areaName = areaName, numOfRows = numOfRows, pageNo = pageNo,completion = {
                        RestByRegionResponse, _ ->
                    when(RestByRegionResponse){
                        Responses.FAIL -> {
                            _isError.postValue("데이터 불러오기 실패.")
                        }
                        Responses.OK -> {
                            Log.d("MainRestViewModel",RestByRegionResponse.toString())
                            _isSuccess.postValue(Unit)
                        }
                    }
                })

            }catch (e:Exception){
                _isError.postValue("로그인에 실패하였습니다.")
            }
        }

    }

}