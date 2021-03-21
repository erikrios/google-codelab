package com.erikriosetiawan.daggerhilttutorial.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.erikriosetiawan.daggerhilttutorial.data.model.User
import com.erikriosetiawan.daggerhilttutorial.databinding.ActivityMainBinding
import com.erikriosetiawan.daggerhilttutorial.ui.main.adapter.MainAdapter
import com.erikriosetiawan.daggerhilttutorial.ui.main.viewmodel.MainViewModel
import com.erikriosetiawan.daggerhilttutorial.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        adapter = MainAdapter(arrayListOf()) {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupObserver() {
        mainViewModel.users.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { users -> renderList(users) }
                    binding.apply {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }
                Status.LOADING -> {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}