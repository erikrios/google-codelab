package com.erikriosetiawan.myinstrumenttest

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.myinstrumenttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = MainViewModel(CuboidModel())

        binding.btnSave.setOnClickListener(this)
        binding.btnCalculateSurfaceArea.setOnClickListener(this)
        binding.btnCalculateCircumference.setOnClickListener(this)
        binding.btnCalculateVolume.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val length = binding.etLength.text.toString().trim()
        val width = binding.etWidth.text.toString().trim()
        val height = binding.etHeight.text.toString().trim()

        when {
            TextUtils.isEmpty(length) -> {
                binding.etLength.error = "Field ini tidak boleh kosong"
            }
            TextUtils.isEmpty(width) -> {
                binding.etWidth.error = "Field ini tidak boleh kosong"
            }
            TextUtils.isEmpty(height) -> {
                binding.etHeight.error = "Field ini tidak boleh kosong"
            }
            else -> {
                val valueLength = length.toDouble()
                val valueWidth = width.toDouble()
                val valueHeight = height.toDouble()

                when (v?.id) {
                    binding.btnSave.id -> {
                        mainViewModel.save(valueLength, valueWidth, valueHeight)
                        visible()
                    }
                    binding.btnCalculateCircumference.id -> {
                        binding.tvResult.text = mainViewModel.circumference.toString()
                        gone()
                    }
                    binding.btnCalculateSurfaceArea.id -> {
                        binding.tvResult.text = mainViewModel.surfaceArea.toString()
                        gone()
                    }
                    binding.btnCalculateVolume.id -> {
                        binding.tvResult.text = mainViewModel.volume.toString()
                        gone()
                    }
                }
            }
        }
    }

    private fun visible() {
        binding.btnCalculateVolume.visibility = View.VISIBLE
        binding.btnCalculateCircumference.visibility = View.VISIBLE
        binding.btnCalculateSurfaceArea.visibility = View.VISIBLE
        binding.btnSave.visibility = View.GONE
    }

    private fun gone() {
        binding.btnCalculateVolume.visibility = View.GONE
        binding.btnCalculateCircumference.visibility = View.GONE
        binding.btnCalculateSurfaceArea.visibility = View.GONE
        binding.btnSave.visibility = View.VISIBLE
    }
}