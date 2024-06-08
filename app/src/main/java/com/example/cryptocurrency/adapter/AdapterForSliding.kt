package com.example.cryptocurrency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.DataForSliding
import com.example.cryptocurrency.databinding.ItemForSlidingBinding

class AdapterForSliding(var list:ArrayList<DataForSliding>): RecyclerView.Adapter<AdapterForSliding.ViewHolder>() {
    class ViewHolder(var itemViewBinding:ItemForSlidingBinding): RecyclerView.ViewHolder(itemViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=ItemForSlidingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=list[position]
        Glide.with(holder.itemView.context).load(item.image).into(holder.itemViewBinding.image)

    }


}