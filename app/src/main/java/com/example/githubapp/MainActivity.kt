package com.example.githubapp

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        mainViewModel.listUser
            .observe(this) { users ->
            setUserData(users)
        }

        mainViewModel.isLoading
            .observe(this) { loading ->
            showLoading(loading)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.svSearch

        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )

        searchView.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            searchView.clearFocus()
            imm.hideSoftInputFromWindow(searchView.windowToken, 0)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    binding.tvSearchGuide.visibility = View.VISIBLE
                    binding.ivBackgroundLogo.visibility = View.VISIBLE
                    binding.rvUser.visibility = RecyclerView.GONE
                } else if (newText.length >= 3) {
                    binding.tvSearchGuide.visibility = View.GONE
                    binding.ivBackgroundLogo.visibility = View.GONE
                    binding.rvUser.visibility = RecyclerView.VISIBLE
                    mainViewModel.findUser(newText)
                } else {
                    binding.tvSearchGuide.visibility = View.GONE
                    binding.ivBackgroundLogo.visibility = View.GONE
                    binding.rvUser.visibility = RecyclerView.GONE
                }
                return false
            }
        })
    }

    private fun setUserData(user: List<User>) {
        val adapter = UserAdapter(user, this)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}