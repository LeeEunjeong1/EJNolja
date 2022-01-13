package com.example.ejnolja.view.main.rest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejnolja.databinding.AdapterRestBinding
import com.example.ejnolja.model.models.Rest
import com.example.ejnolja.model.models.RestByRegionResponse

class RestAdapter: RecyclerView.Adapter<RestViewHolder>() {

    var rest = mutableListOf<Rest>()

    fun setRestList(rest: List<Rest>) {
        this.rest = rest.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterRestBinding.inflate(inflater, parent, false)
        return RestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestViewHolder, position: Int) {
        val rest = rest[position]
        holder.binding.name.text = rest.title
      //  Glide.with(holder.itemView.context).load(rest.imageUrl).into(holder.binding.imageview)

    }

    override fun getItemCount(): Int {
        return rest.size
    }
}

class RestViewHolder(val binding: AdapterRestBinding) : RecyclerView.ViewHolder(binding.root) {

}