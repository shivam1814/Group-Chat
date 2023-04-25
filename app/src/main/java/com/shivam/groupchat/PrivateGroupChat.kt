package com.shivam.groupchat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PrivateGroupChat : AppCompatActivity(),View.OnClickListener {
    lateinit var database: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    lateinit var back_btn: ImageView
    lateinit var btn_messagesend:ImageView
    lateinit var et_message: EditText
    lateinit var progressBar: ProgressBar

    lateinit var pref: pref
    lateinit var adapter: PrivateChatAdapter
    lateinit var list:ArrayList<MessageModel>
    lateinit var recyclerview: RecyclerView

    var group_id=""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_group_chat)


        et_message=findViewById(R.id.et_message)
        back_btn=findViewById(R.id.btn_back)
        back_btn.setOnClickListener(this)
        progressBar=findViewById(R.id.progressBar)
        btn_messagesend=findViewById(R.id.btn_messagesend)
        btn_messagesend.setOnClickListener(this)

        pref=pref(applicationContext)

       /* database=FirebaseDatabase.getInstance()
        databaseReference=database.getReference("GroupChat").child("chattings")

        group_id=intent.extras!!.getString("group_id","")*/


        list= ArrayList()

        var model1=MessageModel("Test","dfdf","dsfdsfsd","sdfsdfdsfsd","1","d")
        var model2=MessageModel("Test","dfdf","dsfdsfsd","sdfsdfdsfsd","2","d")
        var model3=MessageModel("Test","dfdf","dsfdsfsd","sdfsdfdsfsd","1","d")
        var model4=MessageModel("Test","dfdf","dsfdsfsd","sdfsdfdsfsd","0","d")
        list.add(model1)
        list.add(model2)
        list.add(model3)
        list.add(model4)

        recyclerview=findViewById(R.id.chatRecyclerView)

        var manager= LinearLayoutManager(applicationContext)
        manager.stackFromEnd=true
        recyclerview.layoutManager=manager

        adapter= PrivateChatAdapter(list,"1")

        recyclerview.adapter=adapter
    }

    override fun onClick(v: View?) {
        when(v)
        {
            back_btn ->
            {
                onBackPressed()
            }

        }
    }
}