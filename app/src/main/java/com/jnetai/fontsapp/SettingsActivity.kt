package com.jnetai.fontsapp

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    private lateinit var switchDarkMode: SwitchMaterial
    private lateinit var switchAnimations: SwitchMaterial
    private lateinit var sbAnimSpeed: SeekBar
    private lateinit var rvReorderFonts: RecyclerView
    private var reorderAdapter: ReorderFontAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_settings)

            val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener { finish() }

            switchDarkMode = findViewById(R.id.switchDarkMode)
            switchAnimations = findViewById(R.id.switchAnimations)
            sbAnimSpeed = findViewById(R.id.sbAnimSpeed)
            rvReorderFonts = findViewById(R.id.rvReorderFonts)

            switchDarkMode.isChecked = SettingsManager.isDarkMode()
            switchAnimations.isChecked = SettingsManager.isAnimationsEnabled()
            sbAnimSpeed.progress = SettingsManager.getAnimSpeed()

            switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
                SettingsManager.setDarkMode(isChecked)
                DebugLogger.d("Dark mode toggled: $isChecked")
            }

            switchAnimations.setOnCheckedChangeListener { _, isChecked ->
                SettingsManager.setAnimationsEnabled(isChecked)
                DebugLogger.d("Animations toggled: $isChecked")
            }

            sbAnimSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    SettingsManager.setAnimSpeed(progress)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            setupColorPickers()
            setupReorderFonts()

            DebugLogger.i("SettingsActivity created")
        } catch (e: Exception) {
            DebugLogger.e("SettingsActivity onCreate failed", e)
            finish()
        }
    }

    private fun setupColorPickers() {
        val colors = mapOf(
            R.id.colorBlue to 0xFF6B9CE8.toInt(),
            R.id.colorOrange to 0xFFE87A5B.toInt(),
            R.id.colorGreen to 0xFF4CAF50.toInt(),
            R.id.colorPurple to 0xFF9C27B0.toInt(),
            R.id.colorRed to 0xFFF44336.toInt()
        )

        for ((viewId, color) in colors) {
            findViewById<View>(viewId).setOnClickListener {
                SettingsManager.setAccentColor(color)
                DebugLogger.d("Accent color changed to: #${Integer.toHexString(color)}")
            }
        }
    }

    private fun setupReorderFonts() {
        val fonts = FontManager.allFonts.toMutableList()
        rvReorderFonts.layoutManager = LinearLayoutManager(this)

        reorderAdapter = ReorderFontAdapter(
            fonts,
            onMoveUp = { position ->
                if (position > 0) {
                    reorderAdapter?.moveItem(position, position - 1)
                    saveFontOrder()
                }
            },
            onMoveDown = { position ->
                if (position < fonts.size - 1) {
                    reorderAdapter?.moveItem(position, position + 1)
                    saveFontOrder()
                }
            }
        )
        rvReorderFonts.adapter = reorderAdapter
    }

    private fun saveFontOrder() {
        val fonts = FontManager.allFonts
        val order = fonts.map { it.name }
        SettingsManager.setFontOrder(order)
        DebugLogger.d("Font order saved")
    }
}
