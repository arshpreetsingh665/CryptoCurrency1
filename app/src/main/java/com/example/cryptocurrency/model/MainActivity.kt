package com.example.cryptocurrency.model

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //https://youtu.be/IJmsBCcpii4
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navControl = navHost!!.findNavController()
        val popUpMenu = PopupMenu(this, null)
        popUpMenu.inflate(R.menu.menu)
        mBinding.bottomBar.setupWithNavController(popUpMenu.menu, navControl)
    }
}