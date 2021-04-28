package com.erikriosetiawan.mymodularizationapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.mymodularizationapp.databinding.ActivityMainBinding
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var splitInstallManager: SplitInstallManager
    lateinit var request: SplitInstallRequest
    val DYNAMIC_FEATURE = "news_feature"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDynamicModules()
    }

    private fun initDynamicModules() {
        splitInstallManager = SplitInstallManagerFactory.create(this)
        request = SplitInstallRequest
            .newBuilder()
            .addModule(DYNAMIC_FEATURE)
            .build()
    }

    private fun setClickListener() {
        binding.buttonClick.setOnClickListener {
            if (!isDynamicFeatureDownloaded(DYNAMIC_FEATURE)) {
                downloadFeature()
            } else {
                binding.buttonDeleteNewsModule.visibility = View.VISIBLE
                binding.buttonOpenNewsModule.visibility = View.VISIBLE
            }
        }
    }

    private fun isDynamicFeatureDownloaded(feature: String): Boolean =
        splitInstallManager.installedModules.contains(feature)

    private fun downloadFeature() {
        splitInstallManager.startInstall(request)
            .addOnFailureListener { }
            .addOnSuccessListener {
                binding.apply {
                    buttonOpenNewsModule.visibility = View.VISIBLE
                    buttonDeleteNewsModule.visibility = View.VISIBLE
                }
            }
            .addOnCompleteListener { }
    }
}