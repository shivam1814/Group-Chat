package com.shivam.groupchat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
//import kotlinx.android.synthetic.main.activity_create_room.*

class CreateRoom : AppCompatActivity(), View.OnClickListener{
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference1: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var pref: pref
    lateinit var usermodel: Usermodel
    
    lateinit var btn_createroom:Button
    lateinit var btn_back:ImageView
    lateinit var et_roomname:EditText
    lateinit var checkbox:CheckBox


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)

        btn_createroom=findViewById(R.id.btn_createroom)
        btn_back=findViewById(R.id.btn_back)
        et_roomname=findViewById(R.id.et_roomname)
        checkbox=findViewById(R.id.checkbox)

        btn_createroom.setOnClickListener(this)
        btn_back.setOnClickListener(this)

        pref= pref(applicationContext)
        database=FirebaseDatabase.getInstance()

        databaseReference1=database.getReference("GroupChat").child("chattings")
        usermodel=Usermodel(pref.getData("id"),pref.getData("name"),pref.getData("email"),"")
    }

    override fun onClick(v: View?) {
        when(v)
        {
            btn_createroom->{
                var roomname=et_roomname.text.toString().trim()
                var isprivate=checkbox.isChecked

                if (roomname.isEmpty())
                {
                    showToast(applicationContext,"Please enter room name")
                    return
                }
                createRoom(roomname,isprivate)
            }
            btn_back->{
                onBackPressed()
            }
        }
    }

    private fun createRoom(roomname:String,isprivate:Boolean)
    {
            if(isprivate)
            {
                databaseReference=database.getReference("GroupChat").child("private")
                var key=databaseReference.push().key

                var roomModel=RoomModel(key!!,roomname,1,usermodel.name,usermodel.id)
                databaseReference.child(key).setValue(roomModel).addOnCompleteListener {
                    showToast(applicationContext,"Room Create Successfully")
                    finish()
                }
            }
            else
            {

                var model = MessageModel(
                    pref.getData("name")+"create the group",
                    getDateTime(),
                    pref.getData("id"),
                    pref.getData("name"),
                    "1",
                    "0"
                )

                databaseReference=database.getReference("GroupChat").child("public")
                var key=databaseReference.push().key

                var key1 = databaseReference1.push().key
                databaseReference1.child(key!!).child(key1!!).setValue(model)


                var roomModel=RoomModel(key!!,roomname,0,usermodel.name,usermodel.id)
                databaseReference.child(key).setValue(roomModel).addOnCompleteListener {
                    showToast(applicationContext,"Room Create Successfully")
                    finish()
                }
            }

    }
}