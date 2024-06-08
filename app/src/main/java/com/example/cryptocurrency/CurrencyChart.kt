package com.example.cryptocurrency

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.cryptocurrency.databinding.ActivityCurrencyChartBinding

class CurrencyChart : AppCompatActivity() {
    lateinit var mBinding:ActivityCurrencyChartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_currency_chart)
        mBinding.back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val bundle = intent.extras
        if (bundle != null) {
            val name = bundle.getString("name")
            val symbol = bundle.getString("symbol")
            val id = bundle.getString("id")
            val price = bundle.getString("rate")
            val percent = bundle.getString("percent")
            val marketCap = bundle.getString("marketcap")
            val percentChange1h = bundle.getString("percentChange1h")
            val percentChange24h = bundle.getString("percentChange24h")
            val percentChange7d = bundle.getString("percentChange7d")
            val percentChange30d = bundle.getString("percentChange30d")
            val percentChange60d = bundle.getString("percentChange60d")
            val percentChange90d = bundle.getString("percentChange90d")
            val lastUpdated = bundle.getString("lastUpdated")
            val dominance = bundle.getString("dominance")
            mBinding.currencyName.text=name
            mBinding.textview6.text=percentChange1h
            mBinding.textview8.text=percentChange24h
            mBinding.textview10.text=percentChange7d
            mBinding.textview12.text=percentChange30d
            mBinding.textview14.text=percentChange60d
            mBinding.textview16.text=percentChange90d
            mBinding.textview18.text=dominance
            mBinding.textview20.text=lastUpdated
            mBinding.textview2.text=name
            mBinding.currencyRate.text=price
            mBinding.currencyPercent.text=percent
            mBinding.textview4.text=marketCap
            Glide.with(this).load("https://s2.coinmarketcap.com/static/img/coins/64x64/$id.png").into(mBinding.vurrencyImage)
        }}
    }