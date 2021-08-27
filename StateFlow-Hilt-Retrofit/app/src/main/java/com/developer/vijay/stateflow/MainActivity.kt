package com.developer.vijay.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.vijay.stateflow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "mydata"
    }

    private lateinit var mBinding: ActivityMainBinding
    private val viewModel by viewModels<UserViewModel>()
    private val postAdapter by lazy {
        PostAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.title = "Post List"

        mBinding.rvPosts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
        }

        viewModel.getPosts()

        lifecycleScope.launchWhenStarted {
            viewModel.apiResponse.collect {
                when (it) {
                    is Resource.Error -> {
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.rvPosts.visibility = View.GONE
                        mBinding.title = it.message
                        Log.d(TAG, "onCreate: Error")
                    }
                    is Resource.Loading -> {
                        mBinding.progressBar.visibility = View.VISIBLE
                        mBinding.rvPosts.visibility = View.GONE
                        Log.d(TAG, "onCreate: Loading")
                    }
                    is Resource.Success -> {
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.rvPosts.visibility = View.VISIBLE
                        postAdapter.setData(it.data as PostResponse)
                        Log.d(TAG, "onCreate: Success")
                    }
                }
            }
        }

    }


}