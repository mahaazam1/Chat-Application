package com.example.chatapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.SignIn.Companion.userModel
import com.example.chatapplication.Signup.Companion.mSocket
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.chat_username
import kotlinx.android.synthetic.main.activity_chat.profilechat
import kotlinx.android.synthetic.main.activity_online_user.*
import org.json.JSONObject
import java.lang.Exception

class ChatActivity : AppCompatActivity() {

    companion object{
        lateinit var user: UserModel
    }

    lateinit var messageAdapter: MessageAdapter
    var messageId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        user = intent.getParcelableExtra("user")!!
        messageAdapter = MessageAdapter(this, arrayListOf())

        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        var desName = user.username
        chat_username.text = desName
        profilechat.text = desName!![0].toString().toUpperCase()

        messageId = userModel.id!! + user.id

        mSocket!!.on("message") { args ->
            runOnUiThread {
                if (args[0].toString() == messageId || (user.id + userModel.id!!) == args[0].toString()) {
                    val messageModel =
                        Gson().fromJson<MessageModel>(args[1].toString(), MessageModel::class.java)
                    messageAdapter.apply {
                        dataMessage.add(messageModel)
                        notifyDataSetChanged()
                    }
                }
            }
        }
        img_send.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val message = JSONObject()
        message.put("id", userModel.id)
        message.put("username", userModel.username)
        message.put("message", ed_messege.text.toString())
        mSocket!!.emit("message", messageId, message)
        ed_messege.setText("")
    }


}