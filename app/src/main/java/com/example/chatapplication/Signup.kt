package com.example.chatapplication

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class Signup : AppCompatActivity() {

    companion object{
    lateinit var app: SocketCreate
    var mSocket: Socket? = null
    val idApp = UUID.randomUUID().toString()
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        app = application as SocketCreate
        mSocket = app.getSocket()

        mSocket!!.on(Socket.EVENT_CONNECT_ERROR) {
            runOnUiThread {
                Log.e("EVENT_CONNECT_ERROR", "EVENT_CONNECT_ERROR: ")
            }
        }
        mSocket!!.on(Socket.EVENT_CONNECT_TIMEOUT, Emitter.Listener {
            runOnUiThread {
                Log.e("EVENT_CONNECT_TIMEOUT", "EVENT_CONNECT_TIMEOUT: ")

            }
        })
        mSocket!!.on(
            Socket.EVENT_CONNECT
        ) {
            Log.e("onConnect", "Socket Connected!")
        }
        mSocket!!.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
            runOnUiThread {
                Log.e("onDisconnect", "Socket onDisconnect!")
            }
        })


        mSocket!!.connect()

        accountSignup.setOnClickListener {
            var i = Intent(this, SignIn::class.java)
            startActivity(i)
            finish()
        }

        mSocket!!.on("SignUp"){args ->
            runOnUiThread {
                if (args[0].toString() == idApp){
                    Log.e("signUp","true")
                }

            }
        }
        signup.setOnClickListener {
            if (signusername.text.isNotEmpty() && signpass.text.isNotEmpty()) {
                var username = signusername.text.toString()
                var password = signpass.text.toString()

                var jsonObject = JSONObject()
                jsonObject.put("id",UUID.randomUUID().toString())
                jsonObject.put("username",username)
                jsonObject.put("pass1",password)
                jsonObject.put("status",false)
                mSocket!!.emit("SignUp",idApp,jsonObject)
                var i = Intent(this, SignIn::class.java)
                startActivity(i)
                finish()
            }
    }
}
}