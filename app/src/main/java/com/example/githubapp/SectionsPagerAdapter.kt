package com.example.githubapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment = DetailFragment()

        val bundle = Bundle()
        bundle.putString("login", login)
        bundle.putInt("Tabs", position)

        fragment.arguments = bundle
        return fragment
    }
}