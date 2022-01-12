package com.example.ejnolja.view.join

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ejnolja.databinding.ActivitySignupBinding
import com.example.ejnolja.view.login.LoginActivity
import com.example.ejnolja.viewmodel.JoinViewModel

class JoinActivity:AppCompatActivity(){

    private var lBinding: ActivitySignupBinding? = null
    private val binding get() = lBinding!!
    private lateinit var viewModel: JoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[JoinViewModel::class.java]

        initListener()
        isJoin()

    }


    override fun onDestroy() {
        lBinding = null
        super.onDestroy()
    }

    private fun initListener(){
        with(binding){
            btnSignup.setOnClickListener{
                Log.d("login","Login-onCreate")
                val id : String = binding.userId.text.toString()
                val password: String = binding.userPwd.text.toString()
                val name: String = binding.userName.text.toString()

                val params = HashMap<Any,Any>()
                params["id"] = id
                params["password"] = password
                params["nickName"] = name

                viewModel.joinRequest(params)
            }
        }

    }
    private fun isJoin() {
        with(viewModel){
            isSuccess.observe(this@JoinActivity){
                val intentMain = Intent(this@JoinActivity, LoginActivity::class.java)
                intentMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intentMain)
                finish()
            }
            isError.observe(this@JoinActivity){
                Toast.makeText(this@JoinActivity,it, Toast.LENGTH_SHORT).show()
            }
        }
    }





}