package com.erikriosetiawan.latihanviewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.erikriosetiawan.latihanviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        displayResult()

        binding.btnCalculate.setOnClickListener {
            val width = binding.etWidth.text.toString()
            val height = binding.etHeight.text.toString()
            val length = binding.etLength.text.toString()
            val errorMessage = "This field is empty"

            when {
                width.isEmpty() -> binding.etWidth.error = errorMessage
                height.isEmpty() -> binding.etHeight.error = errorMessage
                length.isEmpty() -> binding.etLength.error = errorMessage
                else -> {
                    viewModel.calculate(width, height, length)
                    displayResult()
                }
            }
        }
    }

    private fun displayResult() {
        binding.tvResult.text = viewModel.result.toString()
    }
}