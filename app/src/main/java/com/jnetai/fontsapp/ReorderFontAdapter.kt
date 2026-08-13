package com.jnetai.fontsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReorderFontAdapter(
    private val fonts: MutableList<FontStyle>,
    private val onMoveUp: (Int) -> Unit,
    private val onMoveDown: (Int) -> Unit
) : RecyclerView.Adapter<ReorderFontAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFontName: TextView = view.findViewById(R.id.tvFontName)
        val btnUp: View = view.findViewById(R.id.btnUp)
        val btnDown: View = view.findViewById(R.id.btnDown)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reorder_font, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val font = fonts[position]
        holder.tvFontName.text = font.displayName

        holder.btnUp.setOnClickListener { onMoveUp(position) }
        holder.btnDown.setOnClickListener { onMoveDown(position) }

        holder.btnUp.alpha = if (position == 0) 0.3f else 1f
        holder.btnDown.alpha = if (position == fonts.size - 1) 0.3f else 1f
    }

    override fun getItemCount(): Int = fonts.size

    fun moveItem(from: Int, to: Int) {
        if (from < to) {
            for (i in from until to) {
                fonts[i] = fonts[i + 1].also { fonts[i + 1] = fonts[i] }
            }
        } else {
            for (i in from downTo to + 1) {
                fonts[i] = fonts[i - 1].also { fonts[i - 1] = fonts[i] }
            }
        }
        notifyItemMoved(from, to)
    }
}
