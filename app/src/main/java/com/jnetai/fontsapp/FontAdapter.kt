package com.jnetai.fontsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FontAdapter(
    private val fonts: List<FontStyle>,
    private val onFontClick: (FontStyle) -> Unit,
    private val onFavouriteClick: (FontStyle) -> Unit
) : RecyclerView.Adapter<FontAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFontName: TextView = view.findViewById(R.id.tvFontName)
        val btnFavourite: ImageButton = view.findViewById(R.id.btnFavourite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_font, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val font = fonts[position]
        holder.tvFontName.text = font.displayName
        holder.tvFontName.typeface = android.graphics.Typeface.DEFAULT

        val isFav = SettingsManager.isFavourite(font.name)
        holder.btnFavourite.setImageResource(
            if (isFav) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )

        holder.itemView.setOnClickListener {
            animateClick(it) {
                onFontClick(font)
            }
        }

        holder.btnFavourite.setOnClickListener {
            animateClick(it) {
                onFavouriteClick(font)
            }
        }
    }

    override fun getItemCount(): Int = fonts.size

    private fun animateClick(view: View, action: () -> Unit) {
        if (SettingsManager.isAnimationsEnabled()) {
            view.animate()
                .scaleX(0.92f)
                .scaleY(0.92f)
                .setDuration((50 * SettingsManager.getAnimSpeed() / 5).toLong())
                .withEndAction {
                    view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration((50 * SettingsManager.getAnimSpeed() / 5).toLong())
                        .start()
                }
                .start()
        }
        action()
    }
}
