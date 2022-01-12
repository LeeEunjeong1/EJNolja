package com.example.ejnolja.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ejnolja.databinding.ActivitySplashBinding
import com.example.ejnolja.utils.UserPreferences
import com.example.ejnolja.view.login.LoginActivity
import com.example.ejnolja.view.main.MainActivity
import com.example.ejnolja.viewmodel.SplashViewModel


class SplashActivity:AppCompatActivity() {
    private var lBinding: ActivitySplashBinding? = null
    private val binding get() = lBinding!!
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        val handler = Handler()
        handler.postDelayed(Runnable {
            doSplash()
            observeSplash()
        }, 1000)


    }

    private fun doSplash(){
        UserPreferences.init(this)
        viewModel.doSplash()


    }
    private fun observeSplash(){
        with(viewModel){
            goLogin.observe(this@SplashActivity){
                Log.d("splash","goLogin")
                Log.d("splash",UserPreferences.id)
                startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                finish()
            }
            goMain.observe(this@SplashActivity){
                Log.d("splash","goMain")
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                finish()
            }
        }
    }
}