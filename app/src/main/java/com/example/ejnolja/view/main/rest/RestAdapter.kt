package com.example.ejnolja.view.main.rest

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejnolja.databinding.AdapterRestBinding
import com.example.ejnolja.model.models.Rest

class RestAdapter: RecyclerView.Adapter<RestViewHolder>() {

    var rest = mutableListOf<Rest>()

    fun setRestList(rest: List<Rest>) {
        if(MainRestFragment.restPage==1) {
            this.rest = rest.toMutableList()
            notifyDataSetChanged()
        }else{
            this.rest.addAll((MainRestFragment.restPage -1)* MainRestFragment.restPageSize,rest.toMutableList())
            notifyItemRangeInserted((MainRestFragment.restPage -1)* MainRestFragment.restPageSize,MainRestFragment.restPageSize)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterRestBinding.inflate(inflater, parent, false)
        return RestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestViewHolder, position: Int) {
        val rest = rest[position]
        holder.binding.name.text = rest.title
        Glide.with(holder.itemView.context).load(rest.firstimage).into(holder.binding.imageview)

    }

    override fun getItemCount(): Int {
        return rest.size
    }
}

class RestViewHolder(val binding: AdapterRestBinding) : RecyclerView.ViewHolder(binding.root) {

}