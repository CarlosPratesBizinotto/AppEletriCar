package com.example.appeletricar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.appeletricar.R
import com.example.appeletricar.data.CarFactory
import com.example.appeletricar.ui.adapter.CarAdapter
import com.example.appeletricar.ui.adapter.TabsAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    //lateinit var radioGroup: RadioGroup
    lateinit var tabLayout : TabLayout
    lateinit var viewPager : ViewPager2


   //Ciclo da Activity -> OnCreate Inicia A tela
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       Log.d("Lifecycle", "OnCreate")
        setContentView(R.layout.activity_main)
        setupView()
        setupTabs()
    }

    //Apenas exemplos sobre ciclo de vida de uma activity
    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "Resume")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "Start")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "Pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "Destroy")
    }


    //Recuperar os campos da ActivityMain
    fun setupView() {
        //radioGroup = findViewById(R.id.rg_group_colors)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.vp_view_pager)

    }


    fun setupTabs(){
        val tabsAdapter = TabsAdapter(this)
        viewPager.adapter = tabsAdapter

        //Configurados as abas
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })

    }

}