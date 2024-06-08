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
import com.example.cryptocurrency.databinding.ItemViewForViewpagerBinding
import com.example.cryptocurrency.model.CryptoCurrency

class AdapterForTab(var list: List<CryptoCurrency>) :
    RecyclerView.Adapter<AdapterForTab.ViewHolder>() {
    var filteredList: List<CryptoCurrency> = list

    class ViewHolder(var itemViewForViewpagerBinding: ItemViewForViewpagerBinding) :
        RecyclerView.ViewHolder(itemViewForViewpagerBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemViewForViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = filteredList[position]
        holder.itemViewForViewpagerBinding.cardview2.setOnClickListener {
            val intent = Intent(holder.itemView.context, CurrencyChart::class.java)
            val bundle = Bundle()
            bundle.putString("name", data.name)
            bundle.putString("symbol", data.symbol)
            bundle.putString("id", data.id)
            bundle.putString("rate", String.format("$%.02f", data.quotes[0].price))
            bundle.putString("percent", data.quotes[0].percentChange24h.toString())
            bundle.putString("marketcap", data.quotes[0].marketCap.toString())
            bundle.putString("percentChange1h", data.quotes[0].percentChange1h.toString())
            bundle.putString("percentChange24h", data.quotes[0].percentChange24h.toString())
            bundle.putString("percentChange7d", data.quotes[0].percentChange7d.toString())
            bundle.putString("percentChange30d", data.quotes[0].percentChange30d.toString())
            bundle.putString("percentChange60d", data.quotes[0].percentChange60d.toString())
            bundle.putString("percentChange90d", data.quotes[0].percentChange90d.toString())
            bundle.putString("lastUpdated", data.quotes[0].lastUpdated.toString())
            bundle.putString("dominance", data.quotes[0].dominance.toString())
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)

        }
        holder.itemViewForViewpagerBinding.text2.text = data.name
        holder.itemViewForViewpagerBinding.text3.text = data.symbol
        holder.itemViewForViewpagerBinding.text4.text =
            String.format("$%.02f", data.quotes[0].price)
        if (data.quotes[0].percentChange24h > 0) {
            holder.itemViewForViewpagerBinding.text5.setTextColor(
                holder.itemView.context.resources.getColor(
                    R.color.black
                )
            )
            holder.itemViewForViewpagerBinding.text5.text =
                "+${String.format("%.02f", data.quotes[0].percentChange24h)}%"

        } else {
            holder.itemViewForViewpagerBinding.text5.setTextColor(
                holder.itemView.context.resources.getColor(
                    R.color.colorPrimary
                )
            )
            holder.itemViewForViewpagerBinding.text5.text =
                "${String.format("%.02f", data.quotes[0].percentChange24h)}%"
        }
        Glide.with(holder.itemView.context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + data.id + ".png")
            .thumbnail(
                Glide.with(holder.itemView.context).load(R.drawable.spinner_refresh_svgrepo_com)
            ).into(holder.itemViewForViewpagerBinding.imagef)
        Glide.with(holder.itemView.context)
            .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + data.id + ".png")
            .thumbnail(
                Glide.with(holder.itemView.context).load(R.drawable.spinner_refresh_svgrepo_com)
            ).into(holder.itemViewForViewpagerBinding.iamgeGraph)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: List<CryptoCurrency>) {
        this.filteredList = filteredList
        notifyDataSetChanged()
    }
}