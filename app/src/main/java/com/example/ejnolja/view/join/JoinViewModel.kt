package com.example.ejnolja.view.join

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

class JoinViewModel : ViewModel() {


    private val _isSuccess: MutableLiveData<Unit> = MutableLiveData()
    val isSuccess: LiveData<Unit> get() = _isSuccess

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> get() = _isError


    fun joinRequest(params:HashMap<Any,Any>){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                RetrofitManager.instance.join(params,completion = {
                        loginResponse, _ ->
                    when(loginResponse){
                        Responses.FAIL -> {
                            _isError.postValue("아이디 또는 비밀번호를 확인해주세요.")
                            Log.d("login Fail", Responses.FAIL.toString())
                        }
                        Responses.OK -> {
                            _isSuccess.postValue(Unit)
                            Log.d("login Success", UserPreferences.id)
                        }
                    }
                })

            }catch (e:Exception){
                _isError.postValue("로그인에 실패하였습니다.")
            }
        }

    }
}