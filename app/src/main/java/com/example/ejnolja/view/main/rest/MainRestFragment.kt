package com.example.ejnolja.view.main.rest

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.ejnolja.databinding.FragmentMainRestBinding
import com.example.ejnolja.model.retrofit.MainRepository
import com.example.ejnolja.model.retrofit.RetrofitClient
import com.example.ejnolja.view.main.MainReservationFragment
import com.example.ejnolja.viewmodel.MainRestViewModel
import com.example.ejnolja.viewmodel.ViewModelFactory
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http2.Huffman.decode
import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.encode
import okio.Utf8
import java.lang.Byte.decode
import java.net.URLDecoder.decode
import java.net.URLEncoder
import java.util.*

class MainRestFragment : Fragment() {

    private var lBinding: FragmentMainRestBinding? = null
    private val binding get() = lBinding!!

    private lateinit var viewModel: MainRestViewModel

    private val retrofitClient = RetrofitClient
    val adapter = RestAdapter()

    companion object {
        fun newInstance() = MainReservationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        lBinding = FragmentMainRestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitClient)))[MainRestViewModel::class.java]

        binding.recyclerView.adapter = adapter

        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            Log.d("MainRestFragment",it.toString())
            adapter.setRestList(it.data)
        })
        viewModel.isError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
        })
        viewModel.getRestByRegion("서울","10","1")
    }

}