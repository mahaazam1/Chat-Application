package com.example.chatapplication

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.SignIn.Companion.userModel
import com.example.chatapplication.Signup.Companion.mSocket
import kotlinx.android.synthetic.main.activity_alert_dialog_n_g.*
import kotlinx.android.synthetic.main.activity_alert_dialog_n_g.view.*
//import com.example.chatapplication.Signup.Companion.users

import kotlinx.android.synthetic.main.activity_online_user.*
import kotlinx.android.synthetic.main.item_users.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class OnlineUser : AppCompatActivity(),UsersAdapter.OnClickItem {
    lateinit var usersAdapter: UsersAdapter
    private lateinit var userList: ArrayList<UserModel>
    val usersId = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_user)
        userList = ArrayList()
        usersId.add(userModel.id!!)
        Log.e("tag","$userList")
        usersAdapter= UsersAdapter(this,userList,this)
        recycler_view.adapter = usersAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)


        mSocket!!.emit("allUser",true)

        mSocket!!.on("allUser"){args ->
            runOnUiThread {
                try {
                    val users = args[0] as JSONArray
                    userList.clear()
                for (i in 0 until users.length()) {
                    if(userModel.id==users.getJSONObject(i).getString("id")){
                        var name =users.getJSONObject(i).getString("username")
                        profilechat.text = name[0].toString().toUpperCase()
                        continue
                    }else{
                        userList.add(
                            UserModel(
                                users.getJSONObject(i).getString("id"),
                                users.getJSONObject(i).getString("username"),
                                users.getJSONObject(i).getBoolean("status")
                            )
                        )
                    }
                }
                    Log.e("tag","$userList")
                    usersAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    Log.e("TAG", e.toString())
                }
            }
        }
        btn_group.setOnClickListener {
            val i = Intent(this,GroupListActivity::class.java)
            startActivity(i)
        }

        createGroup.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.activity_alert_dialog_n_g, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Enter Name Group:")
                .setPositiveButton( "OK") { dialog, which ->
                    val dataGroup = JSONObject()
                    var nameGroup=mDialogView.nameGroup.text.toString()
                    dataGroup.put("name",nameGroup)
                    dataGroup.put("id",UUID.randomUUID().toString())
                    val userIdJSONArray = JSONArray()
                    for (id in usersId){
                        userIdJSONArray.put(id)
                    }
                    dataGroup.put("usersId",userIdJSONArray)
                    mSocket!!.emit("addGroup",dataGroup)
                    recycler_view.adapter = UsersAdapter(this,userList,this)
                    val i = Intent(this,GroupListActivity::class.java)
                    startActivity(i)

                }
                .setNegativeButton( "Cancel"){dialog,which ->
                    Log.e( "maha",  "Cancel")

                }
            mBuilder.show()

        }

        mSocket!!.emit("updateOnline",JSONObject().apply {
            put("id",userModel.id)
            put("status",true)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket!!.emit("updateOnline",JSONObject().apply {
            put("id",userModel.id)
            put("status",false)
        })
    }

    override fun onClick(position: Int) {
        var intent = Intent(this,ChatActivity::class.java)
        intent.putExtra("user",userList[position])
        startActivity(intent)
    }

    override fun onLongClick(position: Int) {
        Toast.makeText(this,"Item Selected",Toast.LENGTH_LONG).show()
        if(!usersId.contains(userList[position].id)){
            usersId.add(userList[position].id!!)
        }
    }
}