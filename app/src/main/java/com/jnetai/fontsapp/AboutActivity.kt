package com.jnetai.fontsapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class AboutActivity : AppCompatActivity() {
    private lateinit var tvVersion: TextView
    private lateinit var btnCheckUpdates: MaterialButton
    private lateinit var btnShare: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_about)

            val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener { finish() }

            tvVersion = findViewById(R.id.tvVersion)
            btnCheckUpdates = findViewById(R.id.btnCheckUpdates)
            btnShare = findViewById(R.id.btnShare)

            try {
                val pkgInfo = packageManager.getPackageInfo(packageName, 0)
                val versionName = pkgInfo.versionName ?: "1.0.0"
                tvVersion.text = "Version $versionName"
            } catch (e: PackageManager.NameNotFoundException) {
                tvVersion.text = "Version 1.0.0"
                DebugLogger.e("Failed to get version info", e)
            }

            btnCheckUpdates.setOnClickListener {
                checkForUpdates()
            }

            btnShare.setOnClickListener {
                shareApp()
            }

            DebugLogger.i("AboutActivity created")
        } catch (e: Exception) {
            DebugLogger.e("AboutActivity onCreate failed", e)
            finish()
        }
    }

    private fun checkForUpdates() {
        btnCheckUpdates.isEnabled = false
        btnCheckUpdates.text = "Checking..."

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://api.github.com/repos/jnetai/fonts-app/releases/latest")
                val connection = url.openConnection() as HttpURLConnection
                connection.setRequestProperty("Accept", "application/vnd.github.v3+json")
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val response = connection.inputStream.bufferedReader().readText()
                val json = JSONObject(response)
                val latestVersion = json.optString("tag_name", "v1.0.0").removePrefix("v")
                val htmlUrl = json.optString("html_url", "")

                withContext(Dispatchers.Main) {
                    btnCheckUpdates.isEnabled = true
                    btnCheckUpdates.text = getString(R.string.check_updates)

                    try {
                        val pkgInfo = packageManager.getPackageInfo(packageName, 0)
                        val currentVersion = pkgInfo.versionName ?: "1.0.0"

                        if (latestVersion != currentVersion) {
                            Toast.makeText(
                                this@AboutActivity,
                                "New version available: $latestVersion\nCurrent: $currentVersion",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@AboutActivity,
                                "You are up to date! ($currentVersion)",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        DebugLogger.e("Version comparison failed", e)
                        Toast.makeText(this@AboutActivity, "Latest: $latestVersion", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                DebugLogger.e("Check for updates failed", e)
                withContext(Dispatchers.Main) {
                    btnCheckUpdates.isEnabled = true
                    btnCheckUpdates.text = getString(R.string.check_updates)
                    Toast.makeText(
                        this@AboutActivity,
                        "Failed to check updates: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out ${getString(R.string.app_name)} - a font styling app for Android!\n\n" +
                    "https://github.com/jnetai/fonts-app/releases"
                )
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        } catch (e: Exception) {
            DebugLogger.e("Share app failed", e)
            Toast.makeText(this, "Failed to share", Toast.LENGTH_SHORT).show()
        }
    }
}
