package com.example.chatapplication

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_users.view.*

class GroupAdapter(val activity: Activity,
                   val data: ArrayList<GroupModel>,
                   val onClick: OnClickItem
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var id = itemView.id
        var image = itemView.user_image
        var group_name = itemView.user_name
        var groupCard = itemView.userCard
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group =data[position]
        holder.group_name.text = group.name
        holder.image.text = group.name!![0].toString().toUpperCase()
        holder.groupCard.setOnClickListener {
            Toast.makeText(activity, "adapter ${group.id}", Toast.LENGTH_LONG).show()
            onClick.onClick(group)
        }
    }


    interface OnClickItem {
        fun onClick(groupModel: GroupModel)
    }
}
