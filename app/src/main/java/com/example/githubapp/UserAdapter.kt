package com.example.githubapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter<T>(private val listUser: List<T>, private val context: Context,) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_user,
                viewGroup,
                false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = listUser[position]

        when (user) {
            is User -> viewHolder.tvItem.text = user.login
            is FollowersResponseItem -> viewHolder.tvItem.text = user.login
            is FollowingResponseItem -> viewHolder.tvItem.text = user.login
        }

        Glide.with(viewHolder.itemView.context)
            .load(when(user) {
                is User -> user.avatarUrl
                is FollowingResponseItem -> user.avatarUrl
                is FollowersResponseItem -> user.avatarUrl
                else -> ""
            })
            .centerCrop()
            .into(viewHolder.ivItem)

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            val login = when (user) {
                is User -> user.login
                is FollowersResponseItem -> user.login
                is FollowingResponseItem -> user.login
                else -> ""
            }
            intent.putExtra("LOGIN", login)
            context.startActivity(intent)
        }

    }
    override fun getItemCount() = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tv_username)
        val ivItem: ImageView = view.findViewById(R.id.civ_user)
    }
}