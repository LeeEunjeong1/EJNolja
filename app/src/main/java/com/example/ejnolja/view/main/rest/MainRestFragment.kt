package com.example.ejnolja.view.main.rest

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejnolja.R
import com.example.ejnolja.databinding.FragmentMainRestBinding
import com.example.ejnolja.model.retrofit.MainRepository
import com.example.ejnolja.model.retrofit.RetrofitClient
import com.example.ejnolja.utils.LoadingDialog
import com.example.ejnolja.view.main.MainReservationFragment
import com.example.ejnolja.viewmodel.MainRestViewModel
import com.example.ejnolja.viewmodel.ViewModelFactory

class MainRestFragment : Fragment() {

    private var lBinding: FragmentMainRestBinding? = null
    private val binding get() = lBinding!!

    private lateinit var viewModel: MainRestViewModel

    private val retrofitClient = RetrofitClient
    val adapter = RestAdapter()

    private val loadingDialog by lazy { LoadingDialog(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        lBinding = FragmentMainRestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitClient)))[MainRestViewModel::class.java]
        loadingDialog.show()

        initLayout()
        initListener()
        observeRecyclerView()

    }

    private fun initLayout() {
        with(binding){
            toolbar.toolbarTitle.text = "지역별 관광지"
            recyclerView.adapter = adapter
            restPage =1
            viewModel.load()
                // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource( requireContext(),R.array.region, android.R.layout.simple_spinner_item).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
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
                        viewModel.load()
                        loadingDialog.show()

                    }
                }
            })
            spinner.setSelection(0)
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    //  (parent?.getChildAt(0) as TextView).setTextColor(Color.BLACK)
                    region = parent?.selectedItem.toString()
                    restPage = 1
                    restPageSize = 10
                    viewModel.load()
                    loadingDialog.show()

                }
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        }
    }
    private fun observeRecyclerView(){
        with(viewModel){
            isSuccess.observe(viewLifecycleOwner, Observer {
                loadingDialog.dismiss()
                Log.d("MainRestFragment",it.toString())
                adapter.setRestList(it.data)
                if(restPage > 0 && it.data.isEmpty()){
                    Toast.makeText(context,"더 이상 없습니다.",Toast.LENGTH_SHORT).show()
                }
            })
           isError.observe(viewLifecycleOwner, Observer {
               loadingDialog.dismiss()
                Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
            })

        }
    }

    companion object{
        var restPage = 1
        var restPageSize = 10
        var region = ""
    }

}

