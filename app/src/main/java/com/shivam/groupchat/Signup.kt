package com.shivam.groupchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
//import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity(),View.OnClickListener {

    lateinit var database : FirebaseDatabase
    lateinit var databseReference : DatabaseReference

    lateinit var back_btn:ImageView
    lateinit var btn_gotologin:TextView
    lateinit var btn_signup:Button

    lateinit var et_name_signup:EditText
    lateinit var et_email_signup:EditText
    lateinit var et_pass_signup:EditText
    lateinit var et_confirmpass_signup:EditText

    lateinit var preferance:pref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        back_btn=findViewById(R.id.back_button)
        btn_gotologin=findViewById(R.id.btn_gotologin)
        btn_signup=findViewById(R.id.signup_btn)

        et_name_signup=findViewById(R.id.et_name)
        et_email_signup=findViewById(R.id.et_email)
        et_pass_signup=findViewById(R.id.et_pass)
        et_confirmpass_signup=findViewById(R.id.et_confirmpass)

        back_btn.setOnClickListener(this)
        btn_gotologin.setOnClickListener(this)
        btn_signup.setOnClickListener(this)

        database=FirebaseDatabase.getInstance()
        databseReference=database.getReference("GroupChat").child("Users")

        preferance=pref(applicationContext)
    }

    override fun onClick(v: View?) {
        when(v)
        {
            back_btn -> {
                onBackPressed()
            }
            btn_gotologin->
            {
                onBackPressed()
            }
            btn_signup->
            {
                getData()
            }

        }
    }

 private fun getData(){
     var name=et_name_signup.text.toString().trim()
     var email=et_email_signup.text.toString().trim()
     var pass=et_pass_signup.text.toString().trim()
     var con_pass=et_confirmpass_signup.text.toString().trim()

     if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || con_pass.isEmpty())
     {
         showToast(applicationContext,"All Fields are required")
     }
     else{

         if(pass.equals(con_pass)){
             var id=databseReference.push().key
             var model=Usermodel(id!!,name,email,pass)
             databseReference.child(id!!).setValue(model).addOnCompleteListener {
                 preferance.savedata("name",name)
                 preferance.savedata("id",id)
                 preferance.savedata("email",email)
                 showToast(applicationContext,"successfully sign-up")
                 startActivity(Intent(applicationContext,MainActivity::class.java))
                 finish()
             }
         } else{
             showToast(applicationContext,"please check your password")
         }

     }
 }
}