package com.example.ejnolja.view.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ejnolja.R
import com.example.ejnolja.viewmodel.JoinViewModel
import com.example.ejnolja.viewmodel.MainRestViewModel

class MainRestFragment : Fragment() {

    private lateinit var viewModel: MainRestViewModel

    companion object {
        fun newInstance() = MainReservationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_rest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainRestViewModel::class.java]
        viewModel.getRestByRegion("서울", "10", "1")
    }



}