package com.example.chatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_message_group.*
import org.json.JSONObject

class MessageGroupActivity : AppCompatActivity() {
    private val messageAdapter = MessageAdapter(this,arrayListOf())
    private lateinit var groupModel: GroupModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_group)

        groupModel = intent.getParcelableExtra("group")!!
        var groupName = groupModel.name
        name_group.text = groupName
        img_group.text = groupName!![0].toString().toUpperCase()
        recyclerViewGroup.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        Signup.mSocket!!.on("messageGroup"){ args ->
            runOnUiThread {
                if (args[0].toString()==groupModel.id){
                    val messageModel = Gson().fromJson<MessageModel>(args[1].toString(),MessageModel::class.java)
                    messageAdapter.apply {
                        dataMessage.add(messageModel)
                        notifyDataSetChanged()
                    }
                }
            }
        }

        img_send_group.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val message = JSONObject()
        message.put("id", SignIn.userModel.id)
        message.put("username", SignIn.userModel.username)
        message.put("message",ed_messege_group.text.toString())
        Signup.mSocket!!.emit("messageGroup",groupModel.id, message)
        ed_messege_group.setText("")
    }
}