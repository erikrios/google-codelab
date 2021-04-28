package com.erikriosetiawan.mymodularizationapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.mymodularizationapp.databinding.ActivityMainBinding
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus.*

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
        setClickListener()
    }

    private fun initDynamicModules() {
        splitInstallManager = SplitInstallManagerFactory.create(this)
        request = SplitInstallRequest
            .newBuilder()
            .addModule(DYNAMIC_FEATURE)
            .build()
    }

    private fun setClickListener() {
        binding.apply {
            buttonClick.setOnClickListener {
                if (!isDynamicFeatureDownloaded(DYNAMIC_FEATURE)) {
                    downloadFeature()
                } else {
                    binding.buttonDeleteNewsModule.visibility = View.VISIBLE
                    binding.buttonOpenNewsModule.visibility = View.VISIBLE
                }
            }
            buttonOpenNewsModule.setOnClickListener {
                val intent =
                    Intent().setClassName(
                        this@MainActivity,
                        "com.eririosetiawan.newsfeature.NewsLoaderActivity"
                    )
                startActivity(intent)
            }
            buttonDeleteNewsModule.setOnClickListener {
                val list = mutableListOf<String>()
                list.add(DYNAMIC_FEATURE)
                uninstallDynamicFeature(list)
            }
        }
    }

    private fun isDynamicFeatureDownloaded(feature: String): Boolean =
        splitInstallManager.installedModules.contains(feature)

    private fun downloadFeature() {
        splitInstallManager.startInstall(request)
            .addOnFailureListener {
                Log.d(MainActivity::class.java.simpleName, it.localizedMessage.toString())
            }
            .addOnSuccessListener {
                binding.apply {
                    Log.d(MainActivity::class.java.simpleName, it.toString())
                    buttonOpenNewsModule.visibility = View.VISIBLE
                    buttonDeleteNewsModule.visibility = View.VISIBLE
                }
            }
            .addOnCompleteListener {
                Log.d(MainActivity::class.java.simpleName, it.result.toString())
            }
        monitorStateRequest()
    }

    private fun uninstallDynamicFeature(list: List<String>) {
        splitInstallManager.deferredUninstall(list)
            .addOnSuccessListener {
                binding.apply {
                    buttonOpenNewsModule.visibility = View.GONE
                    buttonOpenNewsModule.visibility = View.GONE
                }
            }
    }

    @SuppressLint("SwitchIntDef")
    private fun monitorStateRequest() {
        var mySessionId = 0
        val listener = SplitInstallStateUpdatedListener {
            mySessionId = it.sessionId()
            when (it.status()) {
                DOWNLOADING -> {
                    val totalBytes = it.totalBytesToDownload()
                    val progress = it.bytesDownloaded()
                }
                INSTALLING -> Log.d("Status", "INSTALLING")
                INSTALLED -> Log.d("Status", "INSTALLED")
                FAILED -> Log.d("Status", "FAILED")
                REQUIRES_USER_CONFIRMATION -> {
                    Log.d("Status", "REQUIRES_USER_CONFIRMATION")
                    startIntentSender(it.resolutionIntent()?.intentSender, null, 0, 0, 0)
                }
            }
        }
    }
}