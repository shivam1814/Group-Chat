package com.shivam.groupchat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class PublicRooms : Fragment(),PublicAdapter.onClick {
    lateinit var adapter: PublicAdapter
    lateinit var list:ArrayList<RoomModel>
    lateinit var recyclerView: RecyclerView
    lateinit var pref: pref

    lateinit var database: FirebaseDatabase
    lateinit var databseReference: DatabaseReference
    lateinit var databaseReference1: DatabaseReference

    @SuppressLint("SuspiciousIndentation", "UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     var  view= inflater.inflate(R.layout.fragment_public_rooms, container, false)
        recyclerView = view.findViewById(R.id.recyclerview)
        database = FirebaseDatabase.getInstance()
        databseReference = database.getReference("GroupChat").child("public")
        databaseReference1=database.getReference("GroupChat").child("chattings")

        list= ArrayList()
        pref=pref(context!!)
        adapter= PublicAdapter(context!!,list,pref.getData("id"),this)

        recyclerView.adapter = adapter

        getPublicRooms()

        return view
    }

     private fun getPublicRooms()
     {
         databseReference.addValueEventListener(object:ValueEventListener
         {
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

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onGroupJoined(roomModel: RoomModel) {
        var usermodel=Usermodel(pref.getData("id"),pref.getData("name"),pref.getData("email"),"")

        var model = MessageModel(
            pref.getData("name")+"Joined the group",
            getDateTime(),
            pref.getData("id"),
            pref.getData("name"),
            "1",
            "1"
        )

        var key=databseReference.push().key
        databseReference.child(roomModel.group_id).child("members").child(key!!).setValue(usermodel).addOnCompleteListener {


            var key1 = databaseReference1.push().key
            databaseReference1.child(key!!).child(key1!!).setValue(model)

            showToast(context!!,"Group joined successfully")
        }


    }

}




