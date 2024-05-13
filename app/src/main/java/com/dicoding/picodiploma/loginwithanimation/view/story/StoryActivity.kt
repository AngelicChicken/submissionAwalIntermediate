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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.MainViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.addstory.AddStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getAuthInstance(this)
    }

    private val storyViewModel by viewModels<StoryViewModel> {
        MainViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        viewModel.getSession().observe(this@StoryActivity) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this@StoryActivity, WelcomeActivity::class.java))
                finish()
            } else {
                setupAction()
            }
        }

        binding.FABAddStory.setOnClickListener{
            startActivity(Intent(this@StoryActivity, AddStoryActivity::class.java))
        }

        binding.FABLogout.setOnClickListener{
            viewModel.logout()
        }

        setupView()
        setupAction()
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

    private fun setupAction(){
        lifecycleScope.launch {
            storyViewModel.story().observe(this@StoryActivity) { story ->
                when (story) {
                    is ResultState.Error -> {
                        binding.progressIndicator.visibility = View.INVISIBLE
                        val error = story.error
                        Toast.makeText(this@StoryActivity, error, Toast.LENGTH_SHORT).show()
                    }

                    is ResultState.Loading -> {
                        binding.progressIndicator.visibility = View.VISIBLE
                    }

                    is ResultState.Success -> {
                        binding.progressIndicator.visibility = View.INVISIBLE
                        val adapter = ListAdapter()
                        adapter.submitList(story.data)
                        binding.recyclerView.adapter = adapter
                    }
                }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}