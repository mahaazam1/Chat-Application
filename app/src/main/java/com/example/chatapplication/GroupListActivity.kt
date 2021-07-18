package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.Signup.Companion.mSocket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_group_list.*
import java.lang.reflect.Type

class GroupListActivity : AppCompatActivity(),GroupAdapter.OnClickItem {
    lateinit var groupAdapter: GroupAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_list)

        groupAdapter = GroupAdapter(this, arrayListOf(),this)
        recycler_view_group.adapter = groupAdapter
        recycler_view_group.layoutManager = LinearLayoutManager(this)
        recycler_view_group.setHasFixedSize(true)

        mSocket!!.emit("allGroup",true)
        mSocket!!.on("allGroup"){args ->
            runOnUiThread {
                val groupList: Type = object : TypeToken<List<GroupModel>>(){}.type
                val group = Gson().fromJson<List<GroupModel>>(args[0].toString(),groupList)
                groupAdapter.data.clear()
                group.forEach { group ->
                    group.usersId.map {id->
                        if (SignIn.userModel.id==id){
                            groupAdapter.apply {
                                data.add(group)
                                notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
            btn_useronline.setOnClickListener {
                val i =Intent(this,OnlineUser::class.java)
                startActivity(i)
            }
        }
    }

    override fun onClick(groupModel: GroupModel) {
        val i = Intent(this,MessageGroupActivity::class.java)
        i.putExtra("group",groupModel)
        startActivity(i)
    }

//    override fun onClick(position: Int) {
//        val i = Intent(this,MessageGroupActivity::class.java)
//        //i.putExtra("group",)
//        startActivity(i)
//    }
}