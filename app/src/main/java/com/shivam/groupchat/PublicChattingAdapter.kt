package com.shivam.groupchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PublicChattingAdapter(list:ArrayList<MessageModel>,var user_id:String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list:ArrayList<MessageModel> = list


    init {
        this.list=list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view:View
        if (viewType==0)
        {
            return MessageViewModel(LayoutInflater.from(parent.context).inflate(R.layout.rv_message,parent,false))
        }
        return GroupJoinedModel(LayoutInflater.from(parent.context).inflate(R.layout.rv_other,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if (list[position].message_type=="0")
       {
           (holder as MessageViewModel).bindView(list[position],user_id)
       }
        else
       {
           (holder as GroupJoinedModel).bindView(list[position])

       }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].message_type.toInt()
    }

    class MessageViewModel(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        var send_message:CardView=itemView.findViewById(R.id.send_message)
        var recieve_message_layout:CardView=itemView.findViewById(R.id.recieve_message_layout)
        var recieve_message_name:TextView=itemView.findViewById(R.id.recieve_message_name)
        var recieve_message_time:TextView=itemView.findViewById(R.id.recieve_message_time)
        var send_message_name:TextView=itemView.findViewById(R.id.send_message_name)
        var send_message_time:TextView=itemView.findViewById(R.id.send_message_time)
        public fun bindView(model:MessageModel,user_id: String)
        {
            if (model.sender_id == user_id)
            {
                send_message.visibility=View.VISIBLE
                recieve_message_layout.visibility=View.GONE
                send_message_name.text=model.message
                send_message_time.text=model.date_time

            }
            else
            {
                send_message.visibility=View.GONE
                recieve_message_layout.visibility=View.VISIBLE
                recieve_message_name.text=model.message
                recieve_message_time.text=model.date_time
            }
        }
    }

    class GroupJoinedModel(itemView: View):RecyclerView.ViewHolder(itemView)
    {
          var text_title:TextView=itemView.findViewById(R.id.text_title)
        public fun bindView(model:MessageModel)
        {
            text_title.text=model.message
        }
    }
}