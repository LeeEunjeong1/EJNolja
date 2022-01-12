package com.example.ejnolja.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ejnolja.utils.UserPreferences

class SplashViewModel : ViewModel() {

    private val _goLogin = MutableLiveData<Unit>()
    val goLogin: LiveData<Unit> get() = _goLogin

    private val _goMain = MutableLiveData<Unit>()
    val goMain: LiveData<Unit> get() = _goMain

    fun doSplash(){

        if(UserPreferences.id.isNotEmpty()){
            _goMain.value = Unit
        }else{
            _goLogin.value= Unit
        }
    }
}