package com.jnetai.fontsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial

class HideFontAdapter(
    private val fonts: List<FontStyle>,
    private val onToggle: (FontStyle, Boolean) -> Unit
) : RecyclerView.Adapter<HideFontAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFontName: TextView = view.findViewById(R.id.tvFontName)
        val switchVisible: SwitchMaterial = view.findViewById(R.id.switchVisible)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hide_font, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val font = fonts[position]
        holder.tvFontName.text = font.displayName
        holder.switchVisible.isChecked = !SettingsManager.isFontHidden(font.name)
        holder.switchVisible.setOnCheckedChangeListener(null)
        holder.switchVisible.setOnCheckedChangeListener { _, isChecked ->
            onToggle(font, isChecked)
        }
    }

    override fun getItemCount(): Int = fonts.size
}
