package com.example.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        detailViewModel.selectedUser
            .observe(this) { user ->
            setUserDetail(user)

            TabLayoutMediator(
                binding.tabs,
                binding.viewPager) { tab, position ->
                val currTab = resources.getString(TAB_TITLES[position])
                val count = if (currTab == resources.getString(R.string.tab_text_1)) {
                    detailViewModel.selectedUser.value?.followers
                } else {
                    detailViewModel.selectedUser.value?.following
                }

                tab.text = "$count $currTab"
            }.attach()
            supportActionBar?.elevation = 0f
        }

        detailViewModel.isLoading
            .observe(this) { loading ->
            showLoading(loading)
        }

        val login = intent.getStringExtra("LOGIN")
        detailViewModel.getDetailUser(login!!)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, login)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val backButton = findViewById<ImageButton>(R.id.ib_back)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setUserDetail(user: UserDetailResponse) {
        binding.tvName.text = user.name
        binding.tvUsername.text = user.login
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