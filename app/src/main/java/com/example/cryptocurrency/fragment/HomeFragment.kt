package com.example.cryptocurrency.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptocurrency.DataForSliding
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.Adapter
import com.example.cryptocurrency.adapter.AdapterForSliding
import com.example.cryptocurrency.adapter.AdapterForViewPager
import com.example.cryptocurrency.apiutils.RetrofitImplementation

import com.example.cryptocurrency.databinding.FragmentHomeBinding
import com.example.cryptocurrency.fragmentforviewpager.FirstFragment
import com.example.cryptocurrency.fragmentforviewpager.SecondFragment2

import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.view.LayoutInflater as LayoutInflater1


class HomeFragment : Fragment() {
    lateinit var mBinding:FragmentHomeBinding
    val list= arrayListOf(
        DataForSliding(R.drawable.bitcoin_logo_wine),
        DataForSliding(R.drawable.ethereum_portrait_purple_dark_logo_wine),
        DataForSliding(R.drawable.vulkan__api__logo_wine),
    )
    private val autoScrollHandler = Handler()
    private lateinit var autoScrollRunnable: Runnable
    private val autoScrollDelay = 3000L
    private lateinit var viewPager: ViewPager2
  private lateinit var viewPagerAdapter: AdapterForViewPager
    override fun onCreateView(
     inflater: LayoutInflater1, container: ViewGroup?,
     savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding.viewpagerForSlider.adapter = AdapterForSliding(list)
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewpagerForSlider) { tab, position ->
            // Set tab text or custom view
//            tab.text = "$position"
        }.attach()
        autoScrollRunnable = object : Runnable {
            override fun run() {
                val currentItem = mBinding.viewpagerForSlider.currentItem
                val nextItem = (currentItem + 1) % list.size // Calculate the index of the next item
                mBinding.viewpagerForSlider.setCurrentItem(nextItem, true)
                autoScrollHandler.postDelayed(this, autoScrollDelay) // Schedule the next scroll
            }
        }

        startAutoScroll()
        if (isInternetAvailable()) {
            getTopCurrencyList()
        }
        viewPager = mBinding.viewPager
        viewPagerAdapter = AdapterForViewPager(childFragmentManager, lifecycle)

        viewPager.adapter = viewPagerAdapter
        val fragment1 = FirstFragment()
        val fragment2 = SecondFragment2()
        viewPagerAdapter.addFragment(fragment1, "Top ")
        viewPagerAdapter.addFragment(fragment2, "Down")
        TabLayoutMediator(mBinding.tab, mBinding.viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getFragmentTitle(position)
        }.attach()
        return mBinding.root
    }

    fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = RetrofitImplementation.getInstance().getTopCurrencyList()
            withContext(Dispatchers.Main) {
                mBinding.recyclerView.layoutManager =
                    LinearLayoutManager(view?.context, LinearLayoutManager.HORIZONTAL, false)
                mBinding.recyclerView.adapter = Adapter(res.body()!!.data.cryptoCurrencyList)
            }
            Log.d("Arsh", "getTopCurrencyList: ${res.body()!!.data.cryptoCurrencyList}")
        }
    }
    private fun startAutoScroll() {
        autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollDelay)
    }

    private fun stopAutoScroll() {
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }

    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        stopAutoScroll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll()
    }
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

}