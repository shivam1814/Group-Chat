package com.shivam.groupchat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btn_logout:ImageView
    lateinit var pref: pref
    lateinit var btn_createroom:ImageView

    lateinit var viewpager:ViewPager2
    lateinit var tabLayout: TabLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_logout = findViewById(R.id.btn_logout)
        btn_logout.setOnClickListener(this)

        btn_createroom=findViewById(R.id.btn_createroom)
        btn_createroom.setOnClickListener(this)

        pref=pref(applicationContext)
        viewpager=findViewById(R.id.viewpager)
        tabLayout=findViewById(R.id.tablayout)

        var fragment= arrayListOf(
            PublicRooms(),
            PrivateRooms()
        )

        viewpager.adapter=ViewPagerAdapter(this,fragment)

        TabLayoutMediator(tabLayout,viewpager,object:TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                when(position)
                {
                    0->
                    {
                        tab.text="Public Rooms"
                    }
                    1->
                    {
                        tab.text="Private Rooms"
                    }
                }
            }

        }).attach()
    }

    override fun onClick(v: View?) {
       when(v)
       {
           btn_logout->
           {
               pref.clearData()
               finish()
           }
           btn_createroom->{
               startActivity(Intent(applicationContext,CreateRoom::class.java))

           }
       }
    }


}
