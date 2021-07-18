package com.example.chatapplication

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.ChatActivity.Companion.user
import com.example.chatapplication.SignIn.Companion.userModel
import kotlinx.android.synthetic.main.msg_recver.view.*
import kotlinx.android.synthetic.main.msg_sender.view.*

class MessageAdapter(val activity: Activity, val dataMessage:ArrayList<MessageModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SenderViewHolder(item:View):RecyclerView.ViewHolder(item.rootView){
        fun bind(messageModel: MessageModel){
            itemView.rootView.msgSen.text = messageModel.message
        }
    }

    inner class ReceverViewHolder(item:View):RecyclerView.ViewHolder(item.rootView){
        fun bind(messageModel: MessageModel){
            itemView.rootView.msgRec.text = messageModel.message
            itemView.rootView.receiver_name.text = messageModel.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            0-> {
                return SenderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.msg_sender,parent,false))
            }
            else -> {
                return ReceverViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.msg_recver,parent,false))
            }
        }
    }

    override fun getItemCount(): Int {
        return dataMessage.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if (holder is SenderViewHolder){
           holder.bind(dataMessage[position])
       }else if (holder is ReceverViewHolder){
           holder.bind(dataMessage[position])
       }
    }

    override fun getItemViewType(position: Int): Int {
        val message = dataMessage[position]
        return when(message.id){
            SignIn.userModel.id -> {
                0
            }
            else ->{
                1
            }
        }
    }


}