package com.shivam.groupchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class SplashScreen : AppCompatActivity() {

    lateinit var preferance:pref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)

        preferance=pref(applicationContext)

        Handler().postDelayed(
            {
                if (preferance.getData("name")==""){
                    var intent= Intent(applicationContext,Login::class.java)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    var intent = Intent(applicationContext, Login::class.java)
                    startActivity(intent)
                    finish()
                }
        },2000);
    }
}