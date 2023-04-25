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
import com.google.firebase.database.*

class PublicGroupChatting : AppCompatActivity(), View.OnClickListener {

    lateinit var database: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    lateinit var back_btn: ImageView
    lateinit var btn_messagesend:ImageView
    lateinit var et_message:EditText
    lateinit var progressBar:ProgressBar
    lateinit var pref: pref
    lateinit var adapter: PublicChattingAdapter
    lateinit var list:ArrayList<MessageModel>
    lateinit var recyclerview:RecyclerView

    var group_id=""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_public_group_chatting)

        et_message=findViewById(R.id.et_message)
        back_btn=findViewById(R.id.btn_back)
        back_btn.setOnClickListener(this)
        progressBar=findViewById(R.id.progressBar)
        btn_messagesend=findViewById(R.id.btn_messagesend)
        btn_messagesend.setOnClickListener(this)
        pref=pref(applicationContext)

        database=FirebaseDatabase.getInstance()
        databaseReference=database.getReference("GroupChat").child("chattings")

        group_id=intent.extras!!.getString("group_id","")

        list= ArrayList()

        recyclerview=findViewById(R.id.chatRecyclerView)

        var manager=LinearLayoutManager(applicationContext)
        manager.stackFromEnd=true
        recyclerview.layoutManager=manager

        adapter= PublicChattingAdapter(list,pref.getData("id"))

        recyclerview.adapter=adapter

        getChatMessage()

    }

    private fun getChatMessage()
    {
        var reference=database.getReference("GroupChat").child("chattings").child(group_id)
        reference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (postsnapshot in snapshot.children)
                {
                    var message=postsnapshot.getValue(MessageModel::class.java)
                    list.add(message!!)
                }
                if (list.size>0)
                {
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onClick(v: View?) {
        when(v) {
            back_btn -> {
                onBackPressed()
            }
            btn_messagesend -> {
                var message = et_message.text.toString().trim()
                if (message.isEmpty()) {
                    showToast(applicationContext,"please enter message")
                } else {
                    btn_messagesend.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE

                    var model = MessageModel(
                        message,
                        getDateTime(),
                        pref.getData("id"),
                        pref.getData("name"),
                        "0",
                        "0"
                    )
                    var key = databaseReference.push().key
                    databaseReference.child(group_id).child(key!!).setValue(model)
                        .addOnCompleteListener {
                            et_message.setText("")
                            btn_messagesend.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                }
            }
        }
    }
}