package com.example.chatapplication

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_users.view.*

class UsersAdapter(
    val activity: Activity,
    val userList: List<UserModel>,
    val onClick: OnClickItem
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id = itemView.id
        var image = itemView.user_image
        var user_name = itemView.user_name
        var selected = itemView.imgSelect
        var imgOnline = itemView.imgOnline
        var userCard = itemView.userCard

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]
        //holder.id = currentItem.id!!.toInt()
        holder.user_name.text = currentItem.username
        holder.image.text = currentItem.username!![0].toString().toUpperCase()
        holder.imgOnline.setImageResource(
            if (currentItem.status)R.drawable.online_circle else R.drawable.circle_image
        )

        holder.userCard.setOnClickListener {
            Toast.makeText(activity, "adapter ${currentItem.id}", Toast.LENGTH_LONG).show()
            onClick.onClick(holder.adapterPosition)
        }

        holder.userCard.setOnLongClickListener {
            if (holder.selected.visibility == View.GONE)
                holder.selected.visibility = View.VISIBLE
            else
                holder.selected.visibility = View.GONE
            onClick.onLongClick(holder.adapterPosition)
            true
        }
    }

    interface OnClickItem {
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }
}