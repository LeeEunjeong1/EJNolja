package com.example.ejnolja.model.retrofit
import android.util.Log
import com.example.ejnolja.model.models.JoinResponse
import com.example.ejnolja.model.models.LoginResponse
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitManager {
    private val BASE_URL = "http://3.144.155.186:3001"
    private val TAG: String = "RetrofitManager"

    companion object{
        val instance = RetrofitManager()
    }

    private val iRetrofit: RetrofitInterface? = RetrofitClient.getClient(BASE_URL)?.create(RetrofitInterface::class.java)

    fun login(id: String, password: String, completion: (Responses, String) -> Unit){
        val call = iRetrofit?.loginRequest(id, password) ?: return

        call.enqueue(object: Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG, "- onFailure: ");
                completion(Responses.FAIL, t.toString())
            }

            override fun onResponse(call: Call<LoginResponse>, responses: Response<LoginResponse>) {
                Log.d(TAG, "- onResponse: ${responses.body()} ")
                Log.d(TAG, "- onResponse: status code is ${responses.code()}")
                if(responses.body()?.state == false){
                    completion(Responses.FAIL, responses.body().toString())
                }else{
                    completion(Responses.OK, responses.body().toString())
                }
            }
        })
    }

    fun join(params: HashMap<Any,Any>,completion: (Responses, String) -> Unit){
        val call = iRetrofit?.joinRequest(params) ?: return

        call.enqueue(object: Callback<JoinResponse>{
            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                Log.d(TAG, "- onFailure: ");
                completion(Responses.FAIL, t.toString())
            }

            override fun onResponse(call: Call<JoinResponse>, responses: Response<JoinResponse>) {
                Log.d(TAG, "- onResponse: ${responses.body()} ")
                Log.d(TAG, "- onResponse: status code is ${responses.code()}")
                if(responses.body()?.state == false){
                    completion(Responses.FAIL, responses.body().toString())
                }else{
                    completion(Responses.OK, responses.body().toString())
                }
            }
        })
    }
}