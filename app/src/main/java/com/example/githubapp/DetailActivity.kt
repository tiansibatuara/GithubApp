package com.example.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ActivityDetailBinding
import kotlin.math.log

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object{
        private const val TAG = "DetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        detailViewModel.selectedUser.observe(this) { user ->
            setUserDetail(user)
        }

        detailViewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }

        val login = intent.getStringExtra("LOGIN")
        detailViewModel.getDetailUser(login!!)
    }

    private fun setUserDetail(user: UserDetailResponse) {
        binding.tvName.text = user.name
        binding.tvUsername.text = user.login
        binding.tvFollowers.text = user.followers.toString()
        binding.tvFollowing.text = user.following.toString()
        Glide.with(this)
            .load(user.avatarUrl)
            .centerCrop()
            .into(binding.civUser)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}