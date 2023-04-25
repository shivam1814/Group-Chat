package com.shivam.groupchat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class PrivateRooms : Fragment(),PrivateAdapter.onClick {
    lateinit var adapter: PrivateAdapter
    lateinit var list: ArrayList<RoomModel>
    lateinit var recyclerView: RecyclerView
    lateinit var pref: pref

    lateinit var database: FirebaseDatabase
    lateinit var databseReference: DatabaseReference
    lateinit var databseReference1: DatabaseReference


    @SuppressLint("MissingInflatedId", "UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_private_rooms, container, false)

        database = FirebaseDatabase.getInstance()
        databseReference = database.getReference("GroupChat").child("private")
        databseReference1=database.getReference("GroupChat").child("private")
        list = ArrayList()
        pref = pref(context!!)
        recyclerView = view.findViewById(R.id.recyclerview_private)

        adapter = PrivateAdapter(context!!,list,pref.getData("id"),this)


        recyclerView.adapter = adapter
        getPrivateRooms()
        return view
    }

    private fun getPrivateRooms() {
        databseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for(postsnapshot in snapshot.children)
                {
                    var value=postsnapshot.getValue(RoomModel::class.java)
                    var isgroupjoined=false

                    for (snapshot in postsnapshot.child("members").children)
                    {
                        var member=snapshot.getValue(Usermodel::class.java)

                        if(pref.getData("id")==member!!.id)
                        {
                            isgroupjoined=true
                        }
                    }
                    if (isgroupjoined)
                    {
                        value!!.isgroupjoined=true
                    }


                    list.add(value!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showToast(context!!,error.message)
            }

        })
    }

    @SuppressLint("UseRequireInsteadOfGet", "SuspiciousIndentation")
    override fun onGroupJoined(roomModel: RoomModel) {
        var usermodel=Usermodel(pref.getData("id"),pref.getData("name"),pref.getData("email"),"")

       /* var key=databseReference.push().key
        databseReference.child(roomModel.group_id).child("members").child(key!!).setValue(usermodel).addOnCompleteListener {
            showToast(context!!,"Group joined successfully")*/

            //var key1:String?=databseReference1.key
            databseReference1.child(roomModel.group_id).child("requests").child(pref.getData("id"))
                .setValue(usermodel)


        }


    }

