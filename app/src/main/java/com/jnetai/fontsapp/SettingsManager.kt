package com.jnetai.fontsapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SettingsManager {
    private const val PREFS_NAME = "fonts_app_prefs"
    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_ANIMATIONS = "animations"
    private const val KEY_ANIM_SPEED = "anim_speed"
    private const val KEY_ACCENT_COLOR = "accent_color"
    private const val KEY_FONT_SIZE = "font_size"
    private const val KEY_FAVOURITES = "favourites"
    private const val KEY_FONT_ORDER = "font_order"
    private const val KEY_SYMBOLS_VISIBLE = "symbols_visible"
    private const val KEY_EMOJIS_VISIBLE = "emojis_visible"

    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        applyTheme()
        DebugLogger.i("SettingsManager initialized")
    }

    fun applyTheme() {
        val darkMode = isDarkMode()
        AppCompatDelegate.setDefaultNightMode(
            if (darkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        DebugLogger.d("Theme applied: darkMode=$darkMode")
    }

    fun isDarkMode(): Boolean = prefs.getBoolean(KEY_DARK_MODE, true)

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
        applyTheme()
        DebugLogger.d("Dark mode set to: $enabled")
    }

    fun isAnimationsEnabled(): Boolean = prefs.getBoolean(KEY_ANIMATIONS, true)

    fun setAnimationsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_ANIMATIONS, enabled).apply()
        DebugLogger.d("Animations set to: $enabled")
    }

    fun getAnimSpeed(): Int = prefs.getInt(KEY_ANIM_SPEED, 5)

    fun setAnimSpeed(speed: Int) {
        prefs.edit().putInt(KEY_ANIM_SPEED, speed).apply()
        DebugLogger.d("Animation speed set to: $speed")
    }

    fun getAccentColor(): Int = prefs.getInt(KEY_ACCENT_COLOR, 0xFF6B9CE8.toInt())

    fun setAccentColor(color: Int) {
        prefs.edit().putInt(KEY_ACCENT_COLOR, color).apply()
        DebugLogger.d("Accent color set to: #${Integer.toHexString(color)}")
    }

    fun getFontSize(): Int = prefs.getInt(KEY_FONT_SIZE, 16)

    fun setFontSize(size: Int) {
        prefs.edit().putInt(KEY_FONT_SIZE, size).apply()
        DebugLogger.d("Font size set to: $size")
    }

    fun getFavourites(): MutableSet<String> {
        val json = prefs.getString(KEY_FAVOURITES, null) ?: return mutableSetOf()
        return try {
            val type = object : TypeToken<MutableSet<String>>() {}.type
            gson.fromJson(json, type) ?: mutableSetOf()
        } catch (e: Exception) {
            DebugLogger.e("Failed to load favourites", e)
            mutableSetOf()
        }
    }

    fun setFavourites(favourites: Set<String>) {
        val json = gson.toJson(favourites)
        prefs.edit().putString(KEY_FAVOURITES, json).apply()
        DebugLogger.d("Favourites saved: ${favourites.size} fonts")
    }

    fun addFavourite(fontName: String) {
        val favs = getFavourites()
        favs.add(fontName)
        setFavourites(favs)
    }

    fun removeFavourite(fontName: String) {
        val favs = getFavourites()
        favs.remove(fontName)
        setFavourites(favs)
    }

    fun isFavourite(fontName: String): Boolean = getFavourites().contains(fontName)

    fun getFontOrder(): List<String> {
        val json = prefs.getString(KEY_FONT_ORDER, null) ?: return emptyList()
        return try {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            DebugLogger.e("Failed to load font order", e)
            emptyList()
        }
    }

    fun setFontOrder(order: List<String>) {
        val json = gson.toJson(order)
        prefs.edit().putString(KEY_FONT_ORDER, json).apply()
        DebugLogger.d("Font order saved: ${order.size} fonts")
    }

    fun isSymbolsVisible(): Boolean = prefs.getBoolean(KEY_SYMBOLS_VISIBLE, false)

    fun setSymbolsVisible(visible: Boolean) {
        prefs.edit().putBoolean(KEY_SYMBOLS_VISIBLE, visible).apply()
    }

    fun isEmojisVisible(): Boolean = prefs.getBoolean(KEY_EMOJIS_VISIBLE, false)

    fun setEmojisVisible(visible: Boolean) {
        prefs.edit().putBoolean(KEY_EMOJIS_VISIBLE, visible).apply()
    }
}
