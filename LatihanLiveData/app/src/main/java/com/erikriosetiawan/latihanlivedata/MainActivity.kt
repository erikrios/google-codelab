package com.erikriosetiawan.latihanlivedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erikriosetiawan.latihanlivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        subscribe()
    }

    private fun subscribe() {
        val elapsedTimeObserver = Observer<Long?> { value ->
            val newText = this@MainActivity.resources.getString(R.string.seconds, value)
            binding.tvTimer.text = newText
        }
        viewModel.getElapsedTime().observe(this, elapsedTimeObserver)
    }
}