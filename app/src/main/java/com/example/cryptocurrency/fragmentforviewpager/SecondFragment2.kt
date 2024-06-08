package com.example.cryptocurrency.fragmentforviewpager

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.AdapterForTab
import com.example.cryptocurrency.apiutils.RetrofitImplementation
import com.example.cryptocurrency.databinding.FragmentSecondBinding
import com.example.cryptocurrency.model.CryptoCurrency
import com.example.cryptocurrency.model.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SecondFragment2 : Fragment() {
    lateinit var mBinding: FragmentSecondBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSecondBinding.inflate(inflater, container, false)
        if (isInternetAvailable()){
getTopCurrencyList()
        }
        return mBinding.root
    }
    fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = RetrofitImplementation.getInstance().getTopCurrencyList()
            val cryptoCurrencyList: List<CryptoCurrency>? = res.body()?.data?.cryptoCurrencyList
            if (cryptoCurrencyList != null) {
                val filteredList = cryptoCurrencyList.filter { cryptoCurrency ->
                    val quotes: List<Quote> = cryptoCurrency.quotes
                    quotes.isNotEmpty() && quotes[0].percentChange24h < 0
                }

                withContext(Dispatchers.Main) {
                    mBinding.secondViewpager.layoutManager = LinearLayoutManager(view?.context)
                    mBinding.secondViewpager.adapter = AdapterForTab(filteredList)
                }
            }
        }
    }
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}