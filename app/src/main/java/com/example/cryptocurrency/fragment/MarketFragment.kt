package com.example.cryptocurrency.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.adapter.AdapterForTab
import com.example.cryptocurrency.apiutils.RetrofitImplementation

import com.example.cryptocurrency.databinding.FragmentMarketBinding
import com.example.cryptocurrency.model.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MarketFragment : Fragment() {
    lateinit var mBinding: FragmentMarketBinding
    private lateinit var searchView: SearchView
    private lateinit var adapter: AdapterForTab
    private var list: List<CryptoCurrency> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMarketBinding.inflate(inflater, container, false)
        if (isInternetAvailable()) {
            setupRecyclerView()
        }
        setupSearchView()
        return mBinding.root
    }

    private fun setupRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = RetrofitImplementation.getInstance().getTopCurrencyList()
            withContext(Dispatchers.Main){
                mBinding.recyclerViewForMarket.layoutManager =
                    LinearLayoutManager(view?.context)

                // Assign the adapter to the 'adapter' property
                adapter = AdapterForTab(res.body()!!.data.cryptoCurrencyList)
                mBinding.recyclerViewForMarket.adapter = adapter
                list = res.body()?.data?.cryptoCurrencyList ?: emptyList()
            }
            Log.d("Arsh","getTopCurrencyList: ${res.body()!!.data.cryptoCurrencyList}")
        }
    }

    private fun setupSearchView() {
        searchView = mBinding.searchView
        searchView.queryHint="Search"
        searchView.setOnClickListener {
            searchView.isIconified = false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    private fun filterList(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            list // No query provided, return the original list
        } else {
            val lowerCaseQuery = query.toLowerCase()
            list.filter { data ->
                data.name.toLowerCase().contains(lowerCaseQuery)
            }
        }
        if (::adapter.isInitialized) {
            adapter.filterList(filteredList)
        }
    }
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}
