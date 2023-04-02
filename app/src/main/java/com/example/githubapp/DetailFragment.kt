package com.example.githubapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var tabs: Int = 0
    private var login: String? = ""
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        private const val TAG = "DetailFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabs = it.getInt("Tabs")
            login = it.getString("login")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.rvUser.layoutManager = layoutManager

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        if (tabs == 0) {
            detailViewModel.getFollowers(login!!)
            detailViewModel.listFollowers.observe(viewLifecycleOwner) { followers ->
                Log.d(TAG, "onCreateView: ${followers}")
                setFollowData(followers)
            }

        } else if (tabs == 1) {
            detailViewModel.getFollowing(login!!)
            detailViewModel.listFollowing.observe(viewLifecycleOwner) { following ->
                Log.d(TAG, "onCreateView: ${following}")
                setFollowData(following)
            }
        }

        return binding.root
    }

    private fun <T> setFollowData(user: List<T>) {
        val adapter = UserAdapter(user, requireContext())
        binding.rvUser.adapter = adapter
    }
}