package com.dicoding.picodiploma.loginwithanimation.view.story

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.StoryViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.addstory.AddStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.maps.MapsActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getAuthInstance(this)
    }

    private val storyViewModel by viewModels<StoryViewModel> {
        StoryViewModelFactory.getInstance(this)
    }

    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()

        setupRecyclerView()
        observeUserSession()
        setupFabActions()
        setupView()
        observeStoryData()

    }

    private fun setupRecyclerView(){
        adapter = ListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.addLoadStateListener { loadState ->
            binding.progressIndicator.isVisible = loadState.source.refresh is LoadState.Loading
            if (loadState.source.refresh is LoadState.Error) {
                val error = (loadState.source.refresh as LoadState.Error).error
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeUserSession(){
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun setupFabActions() {
        binding.FABAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }

        binding.FABLogout.setOnClickListener {
            mainViewModel.logout()
        }

        binding.FABMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun observeStoryData() {
        lifecycleScope.launch {
            storyViewModel.story.observe(this@StoryActivity) { pagingData ->
                adapter.submitData(lifecycle, pagingData)
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}