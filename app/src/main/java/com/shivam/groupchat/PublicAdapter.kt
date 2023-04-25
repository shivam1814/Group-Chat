package com.shivam.groupchat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PublicAdapter(var context:Context, var list:ArrayList<RoomModel>,var user_id:String,var ongroupJoin:PublicAdapter.onClick):
    RecyclerView.Adapter<PublicAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var joinroom:Button=itemView.findViewById(R.id.joinroom)
        var txt_roomname:TextView=itemView.findViewById(R.id.txtroomname)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

         holder.txt_roomname.text=list[position].roomname


        if(list[position].isgroupjoined  || list[position].admin_id==user_id)
        {
            holder.joinroom.visibility=View.GONE
        }
        else
        {
            holder.joinroom.visibility=View.VISIBLE
        }

        holder.joinroom.setOnClickListener {
            ongroupJoin.onGroupJoined(list[position])
        }

        holder.itemView.setOnClickListener {
            if (holder.joinroom.visibility==View.GONE)
            {
                var intent=Intent(context,PublicGroupChatting::class.java)
                intent.putExtra("group_id",list[position].group_id)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    public interface onClick
    {
        fun onGroupJoined(roomModel: RoomModel)
    }
}

