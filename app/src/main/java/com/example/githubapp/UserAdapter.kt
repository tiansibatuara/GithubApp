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

class UserAdapter(private val listReview: List<User>, private val context: Context,) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    companion object{
        private const val TAG = "UserAdapter"
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (following, login, followers, photo, id) = listReview[position]
        viewHolder.tvItem.text = login
        Glide.with(viewHolder.itemView.context)
            .load(photo)
            .centerCrop()
            .into(viewHolder.ivItem)

        viewHolder.itemView.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: ${login}")
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("LOGIN", login)
            context.startActivity(intent)
        }

    }
    override fun getItemCount() = listReview.size
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tv_username)
        val ivItem: ImageView = view.findViewById(R.id.civ_user)
    }
}