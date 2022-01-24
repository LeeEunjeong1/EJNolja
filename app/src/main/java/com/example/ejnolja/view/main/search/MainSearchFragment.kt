package com.example.ejnolja.view.main.search

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejnolja.R
import com.example.ejnolja.databinding.FragmentMainRestBinding
import com.example.ejnolja.databinding.FragmentMainSearchBinding
import com.example.ejnolja.model.retrofit.MainRepository
import com.example.ejnolja.model.retrofit.RetrofitClient
import com.example.ejnolja.utils.KeyboardManager
import com.example.ejnolja.utils.LoadingDialog
import com.example.ejnolja.view.main.MainReservationFragment
import com.example.ejnolja.view.main.rest.RestAdapter
import com.example.ejnolja.viewmodel.MainRestViewModel
import com.example.ejnolja.viewmodel.ViewModelFactory

class MainSearchFragment : Fragment() {


    private var lBinding: FragmentMainSearchBinding? = null
    private val binding get() = lBinding!!

    private lateinit var viewModel: MainRestViewModel

    private val retrofitClient = RetrofitClient
    val adapter = RestAdapter()

    private val loadingDialog by lazy { LoadingDialog(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        lBinding = FragmentMainSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitClient)))[MainRestViewModel::class.java]

        initLayout()
        initListener()
        observeRecyclerView()

    }

    private fun initLayout() {
        with(binding){
            toolbar.toolbarTitle.text = "관광지 검색"
            recyclerView.adapter = adapter
            restPage =1
        }
    }
    private fun initListener(){
        with(binding){
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

                    // 스크롤이 끝에 도달했는지 확인
                    if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                        restPage++
                        viewModel.search()
                        loadingDialog.show()

                    }
                }
            })
            textEdt.setOnKeyListener { v, keyCode, event ->
                when
                {
                    ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) ->
                    {
                        searchBtn.performClick()

                        return@setOnKeyListener true
                    }
                    else -> false
                }
            }
            searchBtn.setOnClickListener{
                if(textEdt.text.isEmpty()){
                    Toast.makeText(requireContext(),"지역을 입력해주세요",Toast.LENGTH_SHORT).show()
                }else{
                    KeyboardManager.run { hideKeyboard() }
                    loadingDialog.show()
                    region = textEdt.text.toString()
                    Log.d("MainSearchFragment_region",region)
                    restPage = 1
                    restPageSize = 10
                    viewModel.search()
                    loadingDialog.show()

                }
            }

        }
    }
    private fun observeRecyclerView(){
        with(viewModel){
            isSuccess.observe(viewLifecycleOwner, Observer {
                loadingDialog.dismiss()
                Log.d("MainSearchFragment_it",it.toString())
                adapter.setRestList(it.data)
                if(restPage > 0 && it.data.isEmpty()){
                    Toast.makeText(context,"더 이상 없습니다.", Toast.LENGTH_SHORT).show()
                }
            })
            isError.observe(viewLifecycleOwner, Observer {
                loadingDialog.dismiss()
                Toast.makeText(context,it, Toast.LENGTH_SHORT).show()
            })

        }
    }

    companion object{
        var restPage = 1
        var restPageSize = 10
        var region = ""
    }
}