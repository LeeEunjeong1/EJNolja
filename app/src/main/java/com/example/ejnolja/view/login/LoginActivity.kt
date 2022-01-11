package com.example.ejnolja.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ejnolja.databinding.ActivityLoginBinding
import com.example.ejnolja.model.retrofit.Responses
import com.example.ejnolja.model.retrofit.RetrofitManager
import com.example.ejnolja.view.join.JoinActivity
import com.example.ejnolja.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private var lBinding: ActivityLoginBinding? = null
    private val binding get() = lBinding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        initListener()
        isLogin()

    }


    override fun onDestroy() {
        lBinding = null
        super.onDestroy()
    }

    private fun initListener(){
        with(binding){
            btnLogin.setOnClickListener{
                Log.d("login","Login-onCreate")
                val id : String = binding.userId.text.toString()
                val password: String = binding.userPwd.text.toString()
                viewModel.loginRequest(id,password)
            }
            goSignup.setOnClickListener{
                startActivity(Intent(this@LoginActivity,JoinActivity::class.java))
                finish()
            }
        }

    }
    private fun isLogin() {
        with(viewModel){
            isSuccess.observe(this@LoginActivity){
                val intentMain = Intent(this@LoginActivity, MainActivity::class.java)
                intentMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intentMain)
                finish()
            }
            isError.observe(this@LoginActivity){
                Toast.makeText(this@LoginActivity,it,Toast.LENGTH_SHORT).show()
            }
        }
    }






}