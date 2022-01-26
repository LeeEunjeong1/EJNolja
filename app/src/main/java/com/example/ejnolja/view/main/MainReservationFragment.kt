package com.example.ejnolja.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.ejnolja.databinding.FragmentMainReservationBinding
import com.example.ejnolja.utils.UserPreferences
import com.example.ejnolja.view.login.LoginActivity
import java.io.DataInputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.net.Socket

class MainReservationFragment : Fragment() {

    companion object {
        fun newInstance() = MainReservationFragment()
    }
    private var lBinding: FragmentMainReservationBinding? = null
    private val binding get() = lBinding!!

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        lBinding = FragmentMainReservationBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_main_reservation, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener{
            Log.d("MainReservation",UserPreferences.id)
            UserPreferences.logout()
            Log.d("MainReservatin id",UserPreferences.id)
            startActivity(Intent(context,LoginActivity::class.java))
            (activity as AppCompatActivity).finishAffinity()
        }
        binding.button.setOnClickListener {
            var thread = NetworkThread()
            thread.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lBinding = null
    }

    //소켓통신 Thread
    inner class NetworkThread : Thread(){
        override fun run(){
            try{
                var socket = Socket("ip주소",55555)

                var input = socket.getInputStream()
                var dis = DataInputStream(input)

                var output = socket.getOutputStream()
                var dos = DataOutputStream(output)

                var data1 = dis.readInt()
                var data2 = dis.readDouble()
                var data3 = dis.readUTF()

                dos.writeInt(200)
                dos.writeDouble(22.22)
                dos.writeUTF("클라이언트가 보낸 문자열")

                socket.close()

                run{
                    binding.textView.text = "data1 : ${data1}\n"
                    binding.textView.append("data2 : ${data2}\n")
                    binding.textView.append("data2 : $data3")

                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

}