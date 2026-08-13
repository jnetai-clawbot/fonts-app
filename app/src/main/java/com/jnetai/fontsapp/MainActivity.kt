package com.jnetai.fontsapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var etInput: EditText
    private lateinit var etOutput: EditText
    private lateinit var rvFonts: RecyclerView
    private lateinit var btnToggleFavs: MaterialButton
    private lateinit var sbFontSize: SeekBar
    private lateinit var tvFontSize: TextView
    private lateinit var cardSymbols: View
    private lateinit var cardEmojis: View
    private lateinit var flexSymbols: FlexboxLayout
    private lateinit var flexEmojis: FlexboxLayout
    private lateinit var btnSymbols: MaterialButton
    private lateinit var btnEmojis: MaterialButton
    private lateinit var tvStatus: TextView

    private var fontAdapter: FontAdapter? = null
    private var currentFontName: String? = null
    private var allFonts: List<FontStyle> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            SettingsManager.init(this)
            setContentView(R.layout.activity_main)
            initViews()
            setupFontList()
            setupSymbols()
            setupEmojis()
            setupListeners()
            DebugLogger.i("MainActivity created successfully")
        } catch (e: Exception) {
            DebugLogger.e("MainActivity onCreate failed", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initViews() {
        etInput = findViewById(R.id.etInput)
        etOutput = findViewById(R.id.etOutput)
        rvFonts = findViewById(R.id.rvFonts)
        btnToggleFavs = findViewById(R.id.btnToggleFavs)
        sbFontSize = findViewById(R.id.sbFontSize)
        tvFontSize = findViewById(R.id.tvFontSize)
        cardSymbols = findViewById(R.id.cardSymbols)
        cardEmojis = findViewById(R.id.cardEmojis)
        flexSymbols = findViewById(R.id.flexSymbols)
        flexEmojis = findViewById(R.id.flexEmojis)
        btnSymbols = findViewById(R.id.btnSymbols)
        btnEmojis = findViewById(R.id.btnEmojis)
        tvStatus = findViewById(R.id.tvStatus)

        sbFontSize.progress = SettingsManager.getFontSize()
        tvFontSize.text = SettingsManager.getFontSize().toString()
        etOutput.textSize = SettingsManager.getFontSize().toFloat()

        cardSymbols.visibility = if (SettingsManager.isSymbolsVisible()) View.VISIBLE else View.GONE
        cardEmojis.visibility = if (SettingsManager.isEmojisVisible()) View.VISIBLE else View.GONE
    }

    private var showingFavourites = false

    private fun setupFontList() {
        allFonts = FontManager.allFonts
        rvFonts.layoutManager = LinearLayoutManager(this)
        rvFonts.isNestedScrollingEnabled = true

        fontAdapter = FontAdapter(
            allFonts,
            onFontClick = { font ->
                currentFontName = font.name
                applyFont()
            },
            onFavouriteClick = { font ->
                if (SettingsManager.isFavourite(font.name)) {
                    SettingsManager.removeFavourite(font.name)
                } else {
                    SettingsManager.addFavourite(font.name)
                }
                fontAdapter?.notifyDataSetChanged()
            }
        )
        rvFonts.adapter = fontAdapter

        btnToggleFavs.setOnClickListener {
            animateClick(it) {
                showingFavourites = !showingFavourites
                if (showingFavourites) {
                    btnToggleFavs.text = "All Fonts"
                    val favs = FontManager.favouriteFonts
                    fontAdapter = FontAdapter(
                        favs,
                        onFontClick = { font -> currentFontName = font.name; applyFont() },
                        onFavouriteClick = { font ->
                            if (SettingsManager.isFavourite(font.name)) SettingsManager.removeFavourite(font.name)
                            else SettingsManager.addFavourite(font.name)
                            fontAdapter?.notifyDataSetChanged()
                        }
                    )
                } else {
                    btnToggleFavs.text = "Favourites"
                    allFonts = FontManager.allFonts
                    fontAdapter = FontAdapter(
                        allFonts,
                        onFontClick = { font -> currentFontName = font.name; applyFont() },
                        onFavouriteClick = { font ->
                            if (SettingsManager.isFavourite(font.name)) SettingsManager.removeFavourite(font.name)
                            else SettingsManager.addFavourite(font.name)
                            fontAdapter?.notifyDataSetChanged()
                        }
                    )
                }
                rvFonts.adapter = fontAdapter
            }
        }
    }

    private fun setupSymbols() {
        for (symbol in SymbolManager.symbols) {
            val btn = MaterialButton(this).apply {
                text = symbol
                textSize = 18f
                setPadding(8, 4, 8, 4)
                layoutParams = FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(4, 4, 4, 4)
                }
                setOnClickListener {
                    animateClick(this) {
                        val cursorPos = etInput.selectionStart
                        val text = etInput.text
                        text.insert(cursorPos, symbol)
                    }
                }
            }
            flexSymbols.addView(btn)
        }
    }

    private fun setupEmojis() {
        for (emoji in EmojiManager.emojis) {
            val btn = MaterialButton(this).apply {
                text = emoji
                textSize = 20f
                setPadding(8, 4, 8, 4)
                layoutParams = FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(4, 4, 4, 4)
                }
                setOnClickListener {
                    animateClick(this) {
                        val cursorPos = etInput.selectionStart
                        val text = etInput.text
                        text.insert(cursorPos, emoji)
                    }
                }
            }
            flexEmojis.addView(btn)
        }
    }

    private fun setupListeners() {
        findViewById<ImageButton>(R.id.btnPaste).setOnClickListener {
            animateClick(it) { pasteFromClipboard() }
        }

        findViewById<ImageButton>(R.id.btnClearInput).setOnClickListener {
            animateClick(it) { etInput.text.clear() }
        }

        findViewById<ImageButton>(R.id.btnCopy).setOnClickListener {
            animateClick(it) { copyToClipboard() }
        }

        findViewById<ImageButton>(R.id.btnClearOutput).setOnClickListener {
            animateClick(it) { etOutput.text.clear() }
        }

        findViewById<MaterialButton>(R.id.btnFlipUp).setOnClickListener {
            animateClick(it) { flipTextUp() }
        }

        findViewById<MaterialButton>(R.id.btnFlipDown).setOnClickListener {
            animateClick(it) { flipTextDown() }
        }

        findViewById<MaterialButton>(R.id.btnFlipLeft).setOnClickListener {
            animateClick(it) { flipTextLeft() }
        }

        findViewById<MaterialButton>(R.id.btnFlipRight).setOnClickListener {
            animateClick(it) { flipTextRight() }
        }

        findViewById<MaterialButton>(R.id.btnReverse).setOnClickListener {
            animateClick(it) { reverseText() }
        }

        btnSymbols.setOnClickListener {
            animateClick(it) { toggleSymbols() }
        }

        btnEmojis.setOnClickListener {
            animateClick(it) { toggleEmojis() }
        }

        findViewById<TextView>(R.id.tvSymbolsHeader).setOnClickListener {
            toggleSymbols()
        }

        findViewById<TextView>(R.id.tvEmojisHeader).setOnClickListener {
            toggleEmojis()
        }

        findViewById<MaterialButton>(R.id.btnHideSymbols).setOnClickListener {
            toggleSymbols()
        }

        findViewById<MaterialButton>(R.id.btnHideEmojis).setOnClickListener {
            toggleEmojis()
        }

        findViewById<MaterialButton>(R.id.btnSave).setOnClickListener {
            animateClick(it) { saveToFile() }
        }

        findViewById<MaterialButton>(R.id.btnLoad).setOnClickListener {
            animateClick(it) { loadFromFile() }
        }

        findViewById<MaterialButton>(R.id.btnUploadFont).setOnClickListener {
            animateClick(it) { uploadFont() }
        }

        findViewById<MaterialButton>(R.id.btnCopyOutput).setOnClickListener {
            animateClick(it) { copyToClipboard() }
        }

        findViewById<MaterialButton>(R.id.btnSettings).setOnClickListener {
            animateClick(it) {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }

        findViewById<MaterialButton>(R.id.btnAbout).setOnClickListener {
            animateClick(it) {
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }

        sbFontSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvFontSize.text = progress.toString()
                etOutput.textSize = progress.toFloat()
                SettingsManager.setFontSize(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        etOutput.setOnClickListener {
            copyToClipboard()
        }

        etOutput.setOnLongClickListener {
            copyToClipboard()
            true
        }

        etInput.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                applyFont()
            }
        })
    }

    private fun toggleSymbols() {
        val visible = cardSymbols.visibility != View.VISIBLE
        cardSymbols.visibility = if (visible) View.VISIBLE else View.GONE
        SettingsManager.setSymbolsVisible(visible)
    }

    private fun toggleEmojis() {
        val visible = cardEmojis.visibility != View.VISIBLE
        cardEmojis.visibility = if (visible) View.VISIBLE else View.GONE
        SettingsManager.setEmojisVisible(visible)
    }

    private fun applyFont() {
        try {
            val input = etInput.text?.toString() ?: ""
            if (input.isEmpty()) {
                etOutput.text.clear()
                return
            }
            val fontName = currentFontName
            if (fontName != null) {
                val font = FontManager.getFontByName(fontName)
                if (font != null && font.typefaceRes != 0) {
                    val typeface = resources.getFont(font.typefaceRes)
                    etOutput.typeface = typeface
                    etOutput.setText(input)
                } else {
                    etOutput.typeface = Typeface.DEFAULT
                    val converted = FontManager.convertText(input, fontName)
                    etOutput.setText(converted)
                }
            }
        } catch (e: Exception) {
            DebugLogger.e("applyFont failed", e)
        }
    }

    private fun pasteFromClipboard() {
        try {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = clipboard.primaryClip
            if (clip != null && clip.itemCount > 0) {
                val text = clip.getItemAt(0).text ?: ""
                etInput.text.insert(etInput.selectionStart, text)
            }
        } catch (e: Exception) {
            DebugLogger.e("pasteFromClipboard failed", e)
            showStatus("Failed to paste")
        }
    }

    private fun copyToClipboard() {
        try {
            val text = etOutput.text?.toString() ?: ""
            if (text.isEmpty()) {
                showStatus(getString(R.string.no_text))
                return
            }
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("styled_text", text)
            clipboard.setPrimaryClip(clip)
            showStatus(getString(R.string.copied))
        } catch (e: Exception) {
            DebugLogger.e("copyToClipboard failed", e)
            showStatus("Failed to copy")
        }
    }

    private fun flipTextUp() {
        try {
            val text = etOutput.text?.toString() ?: return
            val flipped = text.map { c ->
                val map = mapOf(
                    'a' to '\u0250', 'b' to 'q', 'c' to '\u0254', 'd' to 'p', 'e' to '\u01DD',
                    'f' to '\u025F', 'g' to '\u0183', 'h' to '\u0265', 'i' to '\u0131',
                    'j' to '\u027E', 'k' to '\u029E', 'l' to 'l', 'm' to '\u026F',
                    'n' to 'u', 'r' to '\u0279', 't' to '\u0287', 'v' to '\u028C',
                    'w' to '\u028D', 'y' to '\u028E',
                    'A' to '\u2200', 'B' to 'B', 'C' to '\u0186', 'E' to '\u018E',
                    'F' to '\u2132', 'G' to '\u05E4', 'H' to 'H', 'I' to 'I',
                    'J' to '\u017F', 'L' to '\u2142', 'M' to 'W', 'N' to 'N',
                    'P' to '\u0500', 'R' to '\u1D1A', 'T' to '\u22A5', 'U' to '\u2229',
                    'V' to '\u039B', 'W' to 'M', 'Y' to '\u2144',
                    '.' to '\u02D9', ',' to '\u2018', '?' to '\u00BF', '!' to '\u00A1',
                    '&' to '\u214B', '\"' to '\u201E', '\'' to ','
                )
                map[c] ?: c
            }.joinToString("")
            etOutput.setText(flipped)
        } catch (e: Exception) {
            DebugLogger.e("flipTextUp failed", e)
        }
    }

    private fun flipTextDown() {
        try {
            val text = etOutput.text?.toString() ?: return
            etOutput.setText(text.reversed())
        } catch (e: Exception) {
            DebugLogger.e("flipTextDown failed", e)
        }
    }

    private fun flipTextLeft() {
        try {
            val text = etOutput.text?.toString() ?: return
            val lines = text.split("\n")
            val flipped = lines.joinToString("\n") { it.reversed() }
            etOutput.setText(flipped)
        } catch (e: Exception) {
            DebugLogger.e("flipTextLeft failed", e)
        }
    }

    private fun flipTextRight() {
        flipTextLeft()
    }

    private fun reverseText() {
        try {
            val text = etOutput.text?.toString() ?: return
            etOutput.setText(text.reversed())
        } catch (e: Exception) {
            DebugLogger.e("reverseText failed", e)
        }
    }

    private fun saveToFile() {
        try {
            val input = etInput.text?.toString() ?: ""
            val output = etOutput.text?.toString() ?: ""
            val data = "$input|||$output"
            val file = File(filesDir, "saved_text.txt")
            file.writeText(data)
            showStatus(getString(R.string.saved))
            DebugLogger.i("Text saved to file")
        } catch (e: Exception) {
            DebugLogger.e("saveToFile failed", e)
            showStatus("Failed to save")
        }
    }

    private fun loadFromFile() {
        try {
            val file = File(filesDir, "saved_text.txt")
            if (!file.exists()) {
                showStatus("No saved data found")
                return
            }
            val data = file.readText()
            val parts = data.split("|||", limit = 2)
            if (parts.size == 2) {
                etInput.setText(parts[0])
                etOutput.setText(parts[1])
                showStatus(getString(R.string.loaded))
                DebugLogger.i("Text loaded from file")
            }
        } catch (e: Exception) {
            DebugLogger.e("loadFromFile failed", e)
            showStatus("Failed to load")
        }
    }

    private fun uploadFont() {
        try {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("font/ttf", "font/otf", "application/x-font-ttf", "application/x-font-opentype"))
            }
            startActivityForResult(intent, REQUEST_UPLOAD_FONT)
        } catch (e: Exception) {
            DebugLogger.e("uploadFont failed", e)
            showStatus("Failed to open file picker")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == REQUEST_UPLOAD_FONT && resultCode == RESULT_OK) {
                val uri = data?.data ?: return
                val fontName = uri.lastPathSegment ?: "Custom Font"
                FontManager.addCustomFont(FontStyle(
                    name = "custom_${System.currentTimeMillis()}",
                    displayName = fontName,
                    converter = { it },
                    isUnicode = false
                ))
                refreshFontList()
                showStatus(getString(R.string.font_uploaded))
                DebugLogger.i("Custom font uploaded: $fontName")
            }
        } catch (e: Exception) {
            DebugLogger.e("onActivityResult failed", e)
        }
    }

    private fun refreshFontList() {
        allFonts = FontManager.allFonts
        fontAdapter = FontAdapter(
            allFonts,
            onFontClick = { font -> currentFontName = font.name; applyFont() },
            onFavouriteClick = { font ->
                if (SettingsManager.isFavourite(font.name)) SettingsManager.removeFavourite(font.name)
                else SettingsManager.addFavourite(font.name)
                fontAdapter?.notifyDataSetChanged()
            }
        )
        rvFonts.adapter = fontAdapter
    }

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

    private fun showStatus(message: String) {
        tvStatus.text = message
        tvStatus.alpha = 1f
        tvStatus.animate()
            .alpha(0f)
            .setStartDelay(3000)
            .setDuration(2000)
            .start()
    }

    companion object {
        private const val REQUEST_UPLOAD_FONT = 1001
    }
}
