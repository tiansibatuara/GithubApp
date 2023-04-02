package com.example.githubapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter<T>(private val listUser: List<T>, private val context: Context,) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    companion object{
        private const val TAG = "UserAdapter"
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = listUser[position]
//        val (following, login, followers, photo, id) = listUser[position] as User

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
//            Log.d(TAG, "onBindViewHolder: ${user.login}")
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