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
import com.example.cryptocurrency.adapter.AdapterForTab
import com.example.cryptocurrency.apiutils.RetrofitImplementation
import com.example.cryptocurrency.databinding.FragmentFirstBinding
import com.example.cryptocurrency.model.CryptoCurrency
import com.example.cryptocurrency.model.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirstFragment : Fragment() {
    lateinit var mBinding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFirstBinding.inflate(inflater, container, false)
        if (isInternetAvailable()) {
            getTopCurrencyList()
        }
//        mBinding.firstViewpager.layoutManager =
//            LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)
//        mBinding.firstViewpager.adapter = AdapterForTab(list)
        return mBinding.root
    }

    fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = RetrofitImplementation.getInstance().getTopCurrencyList()
            val cryptoCurrencyList: List<CryptoCurrency>? = res.body()?.data?.cryptoCurrencyList
            if (cryptoCurrencyList != null) {
                val filteredList = cryptoCurrencyList.filter { cryptoCurrency ->
                    val quotes: List<Quote> = cryptoCurrency.quotes
                    quotes.isNotEmpty() && quotes[0].percentChange24h > 0
                }

                withContext(Dispatchers.Main) {
                    mBinding.firstViewpager.layoutManager = LinearLayoutManager(view?.context)
                    mBinding.firstViewpager.adapter = AdapterForTab(filteredList)
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

