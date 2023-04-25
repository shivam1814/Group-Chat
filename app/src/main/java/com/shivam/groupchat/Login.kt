package com.shivam.groupchat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.*

class Login : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var databseReference: DatabaseReference
    lateinit var preferance:pref

    //var btn_createnew:TextView=findViewById(R.id.btn_createnew)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var et_login_email: EditText = findViewById(R.id.login_et_email)
        var et_login_pass: EditText = findViewById(R.id.login_et_pass)

        preferance= pref(applicationContext)
        database = FirebaseDatabase.getInstance()
        databseReference = database.getReference("GroupChat").child("Users")

        var btn_createnew: TextView = findViewById(R.id.btn_createnew)
        var btn_login: Button = findViewById(R.id.login_btn)

        btn_createnew.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)

            var email = et_login_email.text.toString().trim()
            var pass = et_login_pass.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                showToast(applicationContext, "all fields reuires.")
            } else {
                isEmailExist(email, pass)
            }
        }
    }

    private fun isEmailExist(email: String, pass: String) {
        databseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<Usermodel>()
                var isemailexist = false

                for (postsnapshot in snapshot.children) {
                    val value = postsnapshot.getValue(Usermodel::class.java)
                    if (value!!.email == email && value!!.pass == pass) {
                        isemailexist = true

                        preferance.savedata("name",value.name)
                        preferance.savedata("id",value.id)
                        preferance.savedata("email",value.email)
                    }
                    list.add(value)
                }
                if (isemailexist) {

                    showToast(applicationContext,"Login successfully ")
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                    finish()
                } else {
                    showToast(applicationContext, "Login failed check your email and password")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

//    override fun onClick(v: View?) {
//
//        var btn_createnew:TextView=findViewById(R.id.btn_createnew)
//        when(v)
//        {
//            btn_createnew->{
//                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


}