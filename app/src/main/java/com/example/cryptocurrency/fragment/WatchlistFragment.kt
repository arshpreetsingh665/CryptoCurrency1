package com.example.cryptocurrency.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.cryptocurrency.databinding.FragmentWatchlistBinding

class WatchlistFragment : Fragment() {
lateinit var mBinding:FragmentWatchlistBinding
//
//
//    private lateinit var adapter: AdapterForTab

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentWatchlistBinding.inflate(inflater, container, false)
      //  setupRecyclerView()

        return mBinding.root
    }
//    private fun setupRecyclerView() {
////        adapter = AdapterForTab(list)
////        mBinding.recyclerViewForWatchlist.layoutManager = LinearLayoutManager(requireContext())
////        mBinding.recyclerViewForWatchlist.adapter = adapter
//    }
}