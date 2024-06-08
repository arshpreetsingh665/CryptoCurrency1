package com.example.cryptocurrency.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.CurrencyChart
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.ItemViewBinding
import com.example.cryptocurrency.model.CryptoCurrency

class Adapter(private var list: List<CryptoCurrency>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(var itemViewBinding: ItemViewBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.itemViewBinding.cardView1.setOnClickListener {
            val intent = Intent(holder.itemView.context, CurrencyChart::class.java)
            val bundle = Bundle()
            bundle.putString("name", data.name)
            bundle.putString("symbol", data.symbol)
            bundle.putString("id", data.id)
            bundle.putString("rate", String.format("$%.02f", data.quotes[0].price))
            bundle.putString("percent", data.quotes[0].percentChange24h.toString())
//            bundle.putString("newsContent", )
            bundle.putString("marketcap", data.quotes[0].marketCap.toString())
            bundle.putString("percentChange1h", data.quotes[0].percentChange1h.toString())
            bundle.putString("percentChange24h", data.quotes[0].percentChange24h.toString())
            bundle.putString("percentChange7d", data.quotes[0].percentChange7d.toString())
            bundle.putString("percentChange30d", data.quotes[0].percentChange30d.toString())
            bundle.putString("percentChange60d", data.quotes[0].percentChange60d.toString())
            bundle.putString("percentChange90d", data.quotes[0].percentChange90d.toString())
            bundle.putString("lastUpdated", data.quotes[0].lastUpdated.toString())
            bundle.putString("dominance", data.quotes[0].dominance.toString())
//            bundle.putString("newsPublishAt",)
//            bundle.putString("newsAuthor", )
            //  bundle.putString("newsContent", news.content)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)

        }
        holder.itemViewBinding.text2.text = data.name
        Glide.with(holder.itemView.context).load(
                "https://s2.coinmarketcap.com/static/img/coins/64x64/" + data.id + ".png"
            ).thumbnail(
                Glide.with(holder.itemView.context).load(R.drawable.spinner_refresh_svgrepo_com)
            ).into(holder.itemViewBinding.image1)
        if (data.quotes[0].percentChange24h > 0) {
            holder.itemViewBinding.text1.setTextColor(holder.itemView.context.resources.getColor(R.color.black))
            holder.itemViewBinding.text1.text =
                "+${String.format("%.02f", data.quotes[0].percentChange24h)}%"

        } else {
            holder.itemViewBinding.text1.setTextColor(holder.itemView.context.resources.getColor(R.color.white))
            holder.itemViewBinding.text1.text =
                "${String.format("%.02f", data.quotes[0].percentChange24h)}%"
        }
    }
}