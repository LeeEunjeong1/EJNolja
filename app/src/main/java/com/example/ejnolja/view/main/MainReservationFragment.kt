package com.example.ejnolja.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ejnolja.databinding.FragmentMainReservationBinding
import com.example.ejnolja.utils.UserPreferences
import com.example.ejnolja.view.login.LoginActivity

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
    }

    override fun onDestroy() {
        super.onDestroy()
        lBinding = null
    }

}