package com.example.chatapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapplication.Signup.Companion.idApp
import com.example.chatapplication.Signup.Companion.mSocket
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.json.JSONObject

class SignIn : AppCompatActivity() {
    companion object{
        lateinit var userModel: UserModel
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        accountSignin.setOnClickListener {
            var i = Intent(this, Signup::class.java)
            startActivity(i)
            finish()
        }

        mSocket!!.on("SignIn") { args ->
            runOnUiThread {
                if (args[0].toString() == idApp)
                    if (args[1].toString().toBoolean()) {
                        Toast.makeText(this, "true", Toast.LENGTH_LONG).show()
                       userModel = Gson().fromJson(args[2].toString(),UserModel::class.java)
                        var i = Intent(this, OnlineUser::class.java)
                        startActivity(i)
                        finish()

                    } else {
                        Toast.makeText(this, "false", Toast.LENGTH_LONG).show()
                    }
            }
        }
        login.setOnClickListener {
            var user = username.text.toString()
            var password = pass.text.toString()
            var jsonObject = JSONObject()
            jsonObject.put("user", user)
            jsonObject.put("pass", password)
            mSocket!!.emit("SignIn", idApp, true, jsonObject)

        }
    }
}
