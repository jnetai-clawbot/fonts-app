package com.jnetai.fontsapp

data class FontStyle(
    val name: String,
    val displayName: String,
    val converter: (String) -> String,
    val isUnicode: Boolean = true,
    val typefaceRes: Int = 0
)

object FontManager {
    private val fonts = mutableListOf<FontStyle>()
    private val customFonts = mutableListOf<FontStyle>()

    val allFonts: List<FontStyle>
        get() {
            val hidden = SettingsManager.getHiddenFonts()
            val visible = (fonts + customFonts).filter { !hidden.contains(it.name) }
            val ordered = SettingsManager.getFontOrder()
            if (ordered.isEmpty()) return visible
            val orderedList = mutableListOf<FontStyle>()
            val remaining = visible.toMutableList()
            for (name in ordered) {
                val found = remaining.find { it.name == name }
                if (found != null) {
                    orderedList.add(found)
                    remaining.remove(found)
                }
            }
            orderedList.addAll(remaining)
            return orderedList
        }

    val allFontsIncludingHidden: List<FontStyle>
        get() = fonts + customFonts

    val favouriteFonts: List<FontStyle>
        get() {
            val favs = SettingsManager.getFavourites()
            return allFonts.filter { favs.contains(it.name) }
        }

    fun getFontByName(name: String): FontStyle? = allFonts.find { it.name == name }

    fun addCustomFont(font: FontStyle) {
        customFonts.add(font)
        DebugLogger.i("Custom font added: ${font.name}")
    }

    fun removeCustomFont(name: String) {
        customFonts.removeAll { it.name == name }
        DebugLogger.i("Custom font removed: $name")
    }

    fun convertText(text: String, fontName: String): String {
        val font = getFontByName(fontName) ?: return text
        return try {
            font.converter(text)
        } catch (e: Exception) {
            DebugLogger.e("Font conversion failed: $fontName", e)
            text
        }
    }

    init {
        initFonts()
    }

    private fun initFonts() {
        fonts.clear()

        // TTF Fonts (loaded from res/font/)
        fonts.add(FontStyle("material_symbols_outlined", "Material Symbols Outlined", converter = { it }, isUnicode = false, typefaceRes = R.font.material_symbols_outlined))
        fonts.add(FontStyle("uncial_antiqua", "Uncial Antiqua", converter = { it }, isUnicode = false, typefaceRes = R.font.uncial_antiqua))
        fonts.add(FontStyle("pirata_one", "Pirata One", converter = { it }, isUnicode = false, typefaceRes = R.font.pirata_one))
        fonts.add(FontStyle("redacted", "Redacted", converter = { it }, isUnicode = false, typefaceRes = R.font.redacted))
        fonts.add(FontStyle("monoton", "Monoton", converter = { it }, isUnicode = false, typefaceRes = R.font.monoton))
        fonts.add(FontStyle("lobster", "Lobster", converter = { it }, isUnicode = false, typefaceRes = R.font.lobster))
        fonts.add(FontStyle("pacifico", "Pacifico", converter = { it }, isUnicode = false, typefaceRes = R.font.pacifico))
        fonts.add(FontStyle("sacramento", "Sacramento", converter = { it }, isUnicode = false, typefaceRes = R.font.sacramento))
        fonts.add(FontStyle("fredericka_the_great", "Fredericka the Great", converter = { it }, isUnicode = false, typefaceRes = R.font.fredericka_the_great))
        fonts.add(FontStyle("bungee_shade", "Bungee Shade", converter = { it }, isUnicode = false, typefaceRes = R.font.bungee_shade))
        fonts.add(FontStyle("ewert", "Ewert", converter = { it }, isUnicode = false, typefaceRes = R.font.ewert))
        fonts.add(FontStyle("special_elite", "Special Elite", converter = { it }, isUnicode = false, typefaceRes = R.font.special_elite))
        fonts.add(FontStyle("nosifer", "Nosifer", converter = { it }, isUnicode = false, typefaceRes = R.font.nosifer))
        fonts.add(FontStyle("knewave", "Knewave", converter = { it }, isUnicode = false, typefaceRes = R.font.knewave))
        fonts.add(FontStyle("zeyada", "Zeyada", converter = { it }, isUnicode = false, typefaceRes = R.font.zeyada))
        fonts.add(FontStyle("rye", "Rye", converter = { it }, isUnicode = false, typefaceRes = R.font.rye))
        fonts.add(FontStyle("noto_sans_math", "Noto Sans Math", converter = { it }, isUnicode = false, typefaceRes = R.font.noto_sans_math))
        fonts.add(FontStyle("marcellus_sc", "Marcellus SC", converter = { it }, isUnicode = false, typefaceRes = R.font.marcellus_sc))
        fonts.add(FontStyle("metamorphous", "Metamorphous", converter = { it }, isUnicode = false, typefaceRes = R.font.metamorphous))
        fonts.add(FontStyle("creepster", "Creepster", converter = { it }, isUnicode = false, typefaceRes = R.font.creepster))
        fonts.add(FontStyle("unifrakturmaguntia", "UnifrakturMaguntia", converter = { it }, isUnicode = false, typefaceRes = R.font.unifrakturmaguntia))
        fonts.add(FontStyle("vast_shadow", "Vast Shadow", converter = { it }, isUnicode = false, typefaceRes = R.font.vast_shadow))
        fonts.add(FontStyle("germania_one", "Germania One", converter = { it }, isUnicode = false, typefaceRes = R.font.germania_one))
        fonts.add(FontStyle("geostar", "Geostar", converter = { it }, isUnicode = false, typefaceRes = R.font.geostar))
        fonts.add(FontStyle("codystar", "Codystar", converter = { it }, isUnicode = false, typefaceRes = R.font.codystar))
        fonts.add(FontStyle("grenze_gotisch", "Grenze Gotisch", converter = { it }, isUnicode = false, typefaceRes = R.font.grenze_gotisch))
        fonts.add(FontStyle("metal_mania", "Metal Mania", converter = { it }, isUnicode = false, typefaceRes = R.font.metal_mania))
        fonts.add(FontStyle("slackey", "Slackey", converter = { it }, isUnicode = false, typefaceRes = R.font.slackey))
        fonts.add(FontStyle("griffy", "Griffy", converter = { it }, isUnicode = false, typefaceRes = R.font.griffy))
        fonts.add(FontStyle("holtwood_one_sc", "Holtwood One SC", converter = { it }, isUnicode = false, typefaceRes = R.font.holtwood_one_sc))
        fonts.add(FontStyle("sancreek", "Sancreek", converter = { it }, isUnicode = false, typefaceRes = R.font.sancreek))
        fonts.add(FontStyle("faster_one", "Faster One", converter = { it }, isUnicode = false, typefaceRes = R.font.faster_one))
        fonts.add(FontStyle("butcherman", "Butcherman", converter = { it }, isUnicode = false, typefaceRes = R.font.butcherman))
        fonts.add(FontStyle("fuggles", "Fuggles", converter = { it }, isUnicode = false, typefaceRes = R.font.fuggles))
        fonts.add(FontStyle("caesar_dressing", "Caesar Dressing", converter = { it }, isUnicode = false, typefaceRes = R.font.caesar_dressing))
        fonts.add(FontStyle("orbitron", "Orbitron", converter = { it }, isUnicode = false, typefaceRes = R.font.orbitron))
        fonts.add(FontStyle("audiowide", "Audiowide", converter = { it }, isUnicode = false, typefaceRes = R.font.audiowide))
        fonts.add(FontStyle("black_ops_one", "Black Ops One", converter = { it }, isUnicode = false, typefaceRes = R.font.black_ops_one))
        fonts.add(FontStyle("rakkas", "Rakkas", converter = { it }, isUnicode = false, typefaceRes = R.font.rakkas))
        fonts.add(FontStyle("londrina_outline", "Londrina Outline", converter = { it }, isUnicode = false, typefaceRes = R.font.londrina_outline))

        // Unicode decorative fonts
        fonts.add(FontStyle("box_black", "Box Black", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> "\uD83C\uDD70" + (c - 'A').toChar()
                    c.isLowerCase() -> "\uD83C\uDD70" + (c - 'a').toChar()
                    c.isDigit() -> (0x1F7E0 + (c - '0')).toChar().toString()
                    c == ' ' -> "  "
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("box_white", "Box White", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> "\uD83C\uDD70" + (c - 'A').toChar()
                    c.isLowerCase() -> "\uD83C\uDD70" + (c - 'a').toChar()
                    c.isDigit() -> (0x1F7E0 + (c - '0')).toChar().toString()
                    c == ' ' -> "  "
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("gothic", "𝕲𝖔𝖙𝖍𝖎𝖈", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝕬"
                    'B' -> "𝕭"
                    'C' -> "𝕮"
                    'D' -> "𝕯"
                    'E' -> "𝕰"
                    'F' -> "𝕱"
                    'G' -> "𝕲"
                    'H' -> "𝕳"
                    'I' -> "𝕴"
                    'J' -> "𝕵"
                    'K' -> "𝕶"
                    'L' -> "𝕷"
                    'M' -> "𝕸"
                    'N' -> "𝕹"
                    'O' -> "𝕺"
                    'P' -> "𝕻"
                    'Q' -> "𝕼"
                    'R' -> "𝕽"
                    'S' -> "𝕾"
                    'T' -> "𝕿"
                    'U' -> "𝖀"
                    'V' -> "𝖁"
                    'W' -> "𝖂"
                    'X' -> "𝖃"
                    'Y' -> "𝖄"
                    'Z' -> "𝖅"
                    'a' -> "𝖆"
                    'b' -> "𝖇"
                    'c' -> "𝖈"
                    'd' -> "𝖉"
                    'e' -> "𝖊"
                    'f' -> "𝖋"
                    'g' -> "𝖌"
                    'h' -> "𝖍"
                    'i' -> "𝖎"
                    'j' -> "𝖏"
                    'k' -> "𝖐"
                    'l' -> "𝖑"
                    'm' -> "𝖒"
                    'n' -> "𝖓"
                    'o' -> "𝖔"
                    'p' -> "𝖕"
                    'q' -> "𝖖"
                    'r' -> "𝖗"
                    's' -> "𝖘"
                    't' -> "𝖙"
                    'u' -> "𝖚"
                    'v' -> "𝖛"
                    'w' -> "𝖜"
                    'x' -> "𝖝"
                    'y' -> "𝖞"
                    'z' -> "𝖟"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("typewriter", "𝚃𝚢𝚙𝚎𝚠𝚛𝚒𝚝𝚎𝚛", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝚨"
                    'B' -> "𝚩"
                    'C' -> "𝚪"
                    'D' -> "𝚫"
                    'E' -> "𝚬"
                    'F' -> "𝚭"
                    'G' -> "𝚮"
                    'H' -> "𝚯"
                    'I' -> "𝚰"
                    'J' -> "𝚱"
                    'K' -> "𝚲"
                    'L' -> "𝚳"
                    'M' -> "𝚴"
                    'N' -> "𝚵"
                    'O' -> "𝚶"
                    'P' -> "𝚷"
                    'Q' -> "𝚸"
                    'R' -> "𝚹"
                    'S' -> "𝚺"
                    'T' -> "𝚻"
                    'U' -> "𝚼"
                    'V' -> "𝚽"
                    'W' -> "𝚾"
                    'X' -> "𝚿"
                    'Y' -> "𝛀"
                    'Z' -> "𝛁"
                    'a' -> "𝛂"
                    'b' -> "𝛃"
                    'c' -> "𝛄"
                    'd' -> "𝛅"
                    'e' -> "𝛆"
                    'f' -> "𝛇"
                    'g' -> "𝛈"
                    'h' -> "𝛉"
                    'i' -> "𝛊"
                    'j' -> "𝛋"
                    'k' -> "𝛌"
                    'l' -> "𝛍"
                    'm' -> "𝛎"
                    'n' -> "𝛏"
                    'o' -> "𝛐"
                    'p' -> "𝛑"
                    'q' -> "𝛒"
                    'r' -> "𝛓"
                    's' -> "𝛔"
                    't' -> "𝛕"
                    'u' -> "𝛖"
                    'v' -> "𝛗"
                    'w' -> "𝛘"
                    'x' -> "𝛙"
                    'y' -> "𝛚"
                    'z' -> "𝛛"
                    '0' -> "𝟬"
                    '1' -> "𝟭"
                    '2' -> "𝟮"
                    '3' -> "𝟯"
                    '4' -> "𝟰"
                    '5' -> "𝟱"
                    '6' -> "𝟲"
                    '7' -> "𝟳"
                    '8' -> "𝟴"
                    '9' -> "𝟵"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("comic", "𝓒𝓸𝓶𝓲𝓬", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝓰"
                    'B' -> "𝓱"
                    'C' -> "𝓲"
                    'D' -> "𝓳"
                    'E' -> "𝓴"
                    'F' -> "𝓵"
                    'G' -> "𝓶"
                    'H' -> "𝓷"
                    'I' -> "𝓸"
                    'J' -> "𝓹"
                    'K' -> "𝓺"
                    'L' -> "𝓻"
                    'M' -> "𝓼"
                    'N' -> "𝓽"
                    'O' -> "𝓾"
                    'P' -> "𝓿"
                    'Q' -> "𝔀"
                    'R' -> "𝔁"
                    'S' -> "𝔂"
                    'T' -> "𝔃"
                    'U' -> "𝔄"
                    'V' -> "𝔅"
                    'W' -> "𝔆"
                    'X' -> "𝔇"
                    'Y' -> "𝔈"
                    'Z' -> "𝔉"
                    'a' -> "𝔊"
                    'b' -> "𝔋"
                    'c' -> "𝔌"
                    'd' -> "𝔍"
                    'e' -> "𝔎"
                    'f' -> "𝔏"
                    'g' -> "𝔐"
                    'h' -> "𝔑"
                    'i' -> "𝔒"
                    'j' -> "𝔓"
                    'k' -> "𝔔"
                    'l' -> "𝔕"
                    'm' -> "𝔖"
                    'n' -> "𝔗"
                    'o' -> "𝔘"
                    'p' -> "𝔙"
                    'q' -> "𝔚"
                    'r' -> "𝔛"
                    's' -> "𝔜"
                    't' -> "𝔝"
                    'u' -> "𝔞"
                    'v' -> "𝔟"
                    'w' -> "𝔠"
                    'x' -> "𝔡"
                    'y' -> "𝔢"
                    'z' -> "𝔣"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("script", "𝒮𝒸𝓇𝒾𝓅𝓉", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝒜"
                    'B' -> "𝒵"
                    'C' -> "𝒞"
                    'D' -> "𝒟"
                    'E' -> "𝒷"
                    'F' -> "𝒸"
                    'G' -> "𝒢"
                    'H' -> "𝒹"
                    'I' -> "𝒤"
                    'J' -> "𝒥"
                    'K' -> "𝒦"
                    'L' -> "𝒺"
                    'M' -> "𝒻"
                    'N' -> "𝒩"
                    'O' -> "𝒪"
                    'P' -> "𝒫"
                    'Q' -> "𝒬"
                    'R' -> "𝒭"
                    'S' -> "𝒮"
                    'T' -> "𝒯"
                    'U' -> "𝒰"
                    'V' -> "𝒱"
                    'W' -> "𝒲"
                    'X' -> "𝒳"
                    'Y' -> "𝒴"
                    'Z' -> "𝒵"
                    'a' -> "𝒶"
                    'b' -> "𝒷"
                    'c' -> "𝒸"
                    'd' -> "𝒹"
                    'e' -> "𝓰"
                    'f' -> "𝒻"
                    'g' -> "𝓲"
                    'h' -> "𝒽"
                    'i' -> "𝒾"
                    'j' -> "𝒿"
                    'k' -> "𝓀"
                    'l' -> "𝓁"
                    'm' -> "𝓂"
                    'n' -> "𝓃"
                    'o' -> "𝓄"
                    'p' -> "𝓅"
                    'q' -> "𝓆"
                    'r' -> "𝓇"
                    's' -> "𝓈"
                    't' -> "𝓉"
                    'u' -> "𝓊"
                    'v' -> "𝓋"
                    'w' -> "𝓌"
                    'x' -> "𝓍"
                    'y' -> "𝓎"
                    'z' -> "𝓏"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("serif_bold", "𝐒𝐞𝐫𝐢𝐟 𝐁𝐨𝐥𝐝", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝐀"
                    'B' -> "𝐁"
                    'C' -> "𝐂"
                    'D' -> "𝐃"
                    'E' -> "𝐄"
                    'F' -> "𝐅"
                    'G' -> "𝐆"
                    'H' -> "𝐇"
                    'I' -> "𝐈"
                    'J' -> "𝐉"
                    'K' -> "𝐊"
                    'L' -> "𝐋"
                    'M' -> "𝐌"
                    'N' -> "𝐍"
                    'O' -> "𝐎"
                    'P' -> "𝐏"
                    'Q' -> "𝐐"
                    'R' -> "𝐑"
                    'S' -> "𝐒"
                    'T' -> "𝐓"
                    'U' -> "𝐔"
                    'V' -> "𝐕"
                    'W' -> "𝐖"
                    'X' -> "𝐗"
                    'Y' -> "𝐘"
                    'Z' -> "𝐙"
                    'a' -> "𝐚"
                    'b' -> "𝐛"
                    'c' -> "𝐜"
                    'd' -> "𝐝"
                    'e' -> "𝐞"
                    'f' -> "𝐟"
                    'g' -> "𝐠"
                    'h' -> "𝐡"
                    'i' -> "𝐢"
                    'j' -> "𝐣"
                    'k' -> "𝐤"
                    'l' -> "𝐥"
                    'm' -> "𝐦"
                    'n' -> "𝐧"
                    'o' -> "𝐨"
                    'p' -> "𝐩"
                    'q' -> "𝐪"
                    'r' -> "𝐫"
                    's' -> "𝐬"
                    't' -> "𝐭"
                    'u' -> "𝐮"
                    'v' -> "𝐯"
                    'w' -> "𝐰"
                    'x' -> "𝐱"
                    'y' -> "𝐲"
                    'z' -> "𝐳"
                    '0' -> "𝟎"
                    '1' -> "𝟏"
                    '2' -> "𝟐"
                    '3' -> "𝟑"
                    '4' -> "𝟒"
                    '5' -> "𝟓"
                    '6' -> "𝟔"
                    '7' -> "𝟕"
                    '8' -> "𝟖"
                    '9' -> "𝟗"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("sans_bold", "𝗦𝗮𝗻𝘀 𝗕𝗼𝗹𝗱", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝖴"
                    'B' -> "𝖵"
                    'C' -> "𝖶"
                    'D' -> "𝖷"
                    'E' -> "𝖸"
                    'F' -> "𝖹"
                    'G' -> "𝖺"
                    'H' -> "𝖻"
                    'I' -> "𝖼"
                    'J' -> "𝖽"
                    'K' -> "𝖾"
                    'L' -> "𝖿"
                    'M' -> "𝗀"
                    'N' -> "𝗁"
                    'O' -> "𝗂"
                    'P' -> "𝗃"
                    'Q' -> "𝗄"
                    'R' -> "𝗅"
                    'S' -> "𝗆"
                    'T' -> "𝗇"
                    'U' -> "𝗈"
                    'V' -> "𝗉"
                    'W' -> "𝗊"
                    'X' -> "𝗋"
                    'Y' -> "𝗌"
                    'Z' -> "𝗍"
                    'a' -> "𝗎"
                    'b' -> "𝗏"
                    'c' -> "𝗐"
                    'd' -> "𝗑"
                    'e' -> "𝗒"
                    'f' -> "𝗓"
                    'g' -> "𝗔"
                    'h' -> "𝗕"
                    'i' -> "𝗖"
                    'j' -> "𝗗"
                    'k' -> "𝗘"
                    'l' -> "𝗙"
                    'm' -> "𝗚"
                    'n' -> "𝗛"
                    'o' -> "𝗜"
                    'p' -> "𝗝"
                    'q' -> "𝗞"
                    'r' -> "𝗟"
                    's' -> "𝗠"
                    't' -> "𝗡"
                    'u' -> "𝗢"
                    'v' -> "𝗣"
                    'w' -> "𝗤"
                    'x' -> "𝗥"
                    'y' -> "𝗦"
                    'z' -> "𝗧"
                    '0' -> "𝟶"
                    '1' -> "𝟷"
                    '2' -> "𝟸"
                    '3' -> "𝟹"
                    '4' -> "𝟺"
                    '5' -> "𝟻"
                    '6' -> "𝟼"
                    '7' -> "𝟽"
                    '8' -> "𝟾"
                    '9' -> "𝟿"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("double_struck", "𝔻𝕠𝕦𝕓𝕝𝕖", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝔸"
                    'B' -> "𝔹"
                    'C' -> "ℂ"
                    'D' -> "𝔻"
                    'E' -> "𝔼"
                    'F' -> "𝔽"
                    'G' -> "𝔾"
                    'H' -> "ℍ"
                    'I' -> "𝕀"
                    'J' -> "𝕁"
                    'K' -> "𝕂"
                    'L' -> "𝕃"
                    'M' -> "𝕄"
                    'N' -> "ℕ"
                    'O' -> "𝕆"
                    'P' -> "ℙ"
                    'Q' -> "ℚ"
                    'R' -> "ℝ"
                    'S' -> "𝕊"
                    'T' -> "𝕋"
                    'U' -> "𝕌"
                    'V' -> "𝕍"
                    'W' -> "𝕎"
                    'X' -> "𝕏"
                    'Y' -> "𝕐"
                    'Z' -> "ℤ"
                    'a' -> "𝕒"
                    'b' -> "𝕓"
                    'c' -> "𝕔"
                    'd' -> "𝕕"
                    'e' -> "𝕖"
                    'f' -> "𝕗"
                    'g' -> "𝕘"
                    'h' -> "𝕙"
                    'i' -> "𝕚"
                    'j' -> "𝕛"
                    'k' -> "𝕜"
                    'l' -> "𝕝"
                    'm' -> "𝕞"
                    'n' -> "𝕟"
                    'o' -> "𝕠"
                    'p' -> "𝕡"
                    'q' -> "𝕢"
                    'r' -> "𝕣"
                    's' -> "𝕤"
                    't' -> "𝕥"
                    'u' -> "𝕦"
                    'v' -> "𝕧"
                    'w' -> "𝕨"
                    'x' -> "𝕩"
                    'y' -> "𝕪"
                    'z' -> "𝕫"
                    '0' -> "𝟘"
                    '1' -> "𝟙"
                    '2' -> "𝟚"
                    '3' -> "𝟛"
                    '4' -> "𝟜"
                    '5' -> "𝟝"
                    '6' -> "𝟞"
                    '7' -> "𝟟"
                    '8' -> "𝟠"
                    '9' -> "𝟡"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("italic_bold", "𝑰𝒕𝒂𝒍𝒊𝒄", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝑨"
                    'B' -> "𝑩"
                    'C' -> "𝑪"
                    'D' -> "𝑫"
                    'E' -> "𝑬"
                    'F' -> "𝑭"
                    'G' -> "𝑮"
                    'H' -> "𝑯"
                    'I' -> "𝑰"
                    'J' -> "𝑱"
                    'K' -> "𝑲"
                    'L' -> "𝑳"
                    'M' -> "𝑴"
                    'N' -> "𝑵"
                    'O' -> "𝑶"
                    'P' -> "𝑷"
                    'Q' -> "𝑸"
                    'R' -> "𝑹"
                    'S' -> "𝑺"
                    'T' -> "𝑻"
                    'U' -> "𝑼"
                    'V' -> "𝑽"
                    'W' -> "𝑾"
                    'X' -> "𝑿"
                    'Y' -> "𝒀"
                    'Z' -> "𝒁"
                    'a' -> "𝒂"
                    'b' -> "𝒃"
                    'c' -> "𝒄"
                    'd' -> "𝒅"
                    'e' -> "𝒆"
                    'f' -> "𝒇"
                    'g' -> "𝒈"
                    'h' -> "𝒉"
                    'i' -> "𝒊"
                    'j' -> "𝒋"
                    'k' -> "𝒌"
                    'l' -> "𝒍"
                    'm' -> "𝒎"
                    'n' -> "𝒏"
                    'o' -> "𝒐"
                    'p' -> "𝒑"
                    'q' -> "𝒒"
                    'r' -> "𝒓"
                    's' -> "𝒔"
                    't' -> "𝒕"
                    'u' -> "𝒖"
                    'v' -> "𝒗"
                    'w' -> "𝒘"
                    'x' -> "𝒙"
                    'y' -> "𝒚"
                    'z' -> "𝒛"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("italic_sans", "𝘪𝘵𝘢𝘭𝘪𝘤 𝘴𝘢𝘯𝘴", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝘈"
                    'B' -> "𝘉"
                    'C' -> "𝘊"
                    'D' -> "𝘋"
                    'E' -> "𝘌"
                    'F' -> "𝘍"
                    'G' -> "𝘎"
                    'H' -> "𝘏"
                    'I' -> "𝘐"
                    'J' -> "𝘑"
                    'K' -> "𝘒"
                    'L' -> "𝘓"
                    'M' -> "𝘔"
                    'N' -> "𝘕"
                    'O' -> "𝘖"
                    'P' -> "𝘗"
                    'Q' -> "𝘘"
                    'R' -> "𝘙"
                    'S' -> "𝘚"
                    'T' -> "𝘛"
                    'U' -> "𝘜"
                    'V' -> "𝘝"
                    'W' -> "𝘞"
                    'X' -> "𝘟"
                    'Y' -> "𝘠"
                    'Z' -> "𝘡"
                    'a' -> "𝘢"
                    'b' -> "𝘣"
                    'c' -> "𝘤"
                    'd' -> "𝘥"
                    'e' -> "𝘦"
                    'f' -> "𝘧"
                    'g' -> "𝘨"
                    'h' -> "𝘩"
                    'i' -> "𝘪"
                    'j' -> "𝘫"
                    'k' -> "𝘬"
                    'l' -> "𝘭"
                    'm' -> "𝘮"
                    'n' -> "𝘯"
                    'o' -> "𝘰"
                    'p' -> "𝘱"
                    'q' -> "𝘲"
                    'r' -> "𝘳"
                    's' -> "𝘴"
                    't' -> "𝘵"
                    'u' -> "𝘶"
                    'v' -> "𝘷"
                    'w' -> "𝘸"
                    'x' -> "𝘹"
                    'y' -> "𝘺"
                    'z' -> "𝘻"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("bold_fraktur", "𝕱𝖗𝖆𝖐𝖙𝖚𝖗", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝕬"
                    'B' -> "𝕭"
                    'C' -> "𝕮"
                    'D' -> "𝕯"
                    'E' -> "𝕰"
                    'F' -> "𝕱"
                    'G' -> "𝕲"
                    'H' -> "𝕳"
                    'I' -> "𝕴"
                    'J' -> "𝕵"
                    'K' -> "𝕶"
                    'L' -> "𝕷"
                    'M' -> "𝕸"
                    'N' -> "𝕹"
                    'O' -> "𝕺"
                    'P' -> "𝕻"
                    'Q' -> "𝕼"
                    'R' -> "𝕽"
                    'S' -> "𝕾"
                    'T' -> "𝕿"
                    'U' -> "𝖀"
                    'V' -> "𝖁"
                    'W' -> "𝖂"
                    'X' -> "𝖃"
                    'Y' -> "𝖄"
                    'Z' -> "𝖅"
                    'a' -> "𝖆"
                    'b' -> "𝖇"
                    'c' -> "𝖈"
                    'd' -> "𝖉"
                    'e' -> "𝖊"
                    'f' -> "𝖋"
                    'g' -> "𝖌"
                    'h' -> "𝖍"
                    'i' -> "𝖎"
                    'j' -> "𝖏"
                    'k' -> "𝖐"
                    'l' -> "𝖑"
                    'm' -> "𝖒"
                    'n' -> "𝖓"
                    'o' -> "𝖔"
                    'p' -> "𝖕"
                    'q' -> "𝖖"
                    'r' -> "𝖗"
                    's' -> "𝖘"
                    't' -> "𝖙"
                    'u' -> "𝖚"
                    'v' -> "𝖛"
                    'w' -> "𝖜"
                    'x' -> "𝖝"
                    'y' -> "𝖞"
                    'z' -> "𝖟"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("monospace_unicode", "𝚖𝚘𝚗𝚘𝚜𝚙𝚊𝚌𝚎", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝚨"
                    'B' -> "𝚩"
                    'C' -> "𝚪"
                    'D' -> "𝚫"
                    'E' -> "𝚬"
                    'F' -> "𝚭"
                    'G' -> "𝚮"
                    'H' -> "𝚯"
                    'I' -> "𝚰"
                    'J' -> "𝚱"
                    'K' -> "𝚲"
                    'L' -> "𝚳"
                    'M' -> "𝚴"
                    'N' -> "𝚵"
                    'O' -> "𝚶"
                    'P' -> "𝚷"
                    'Q' -> "𝚸"
                    'R' -> "𝚹"
                    'S' -> "𝚺"
                    'T' -> "𝚻"
                    'U' -> "𝚼"
                    'V' -> "𝚽"
                    'W' -> "𝚾"
                    'X' -> "𝚿"
                    'Y' -> "𝛀"
                    'Z' -> "𝛁"
                    'a' -> "𝛂"
                    'b' -> "𝛃"
                    'c' -> "𝛄"
                    'd' -> "𝛅"
                    'e' -> "𝛆"
                    'f' -> "𝛇"
                    'g' -> "𝛈"
                    'h' -> "𝛉"
                    'i' -> "𝛊"
                    'j' -> "𝛋"
                    'k' -> "𝛌"
                    'l' -> "𝛍"
                    'm' -> "𝛎"
                    'n' -> "𝛏"
                    'o' -> "𝛐"
                    'p' -> "𝛑"
                    'q' -> "𝛒"
                    'r' -> "𝛓"
                    's' -> "𝛔"
                    't' -> "𝛕"
                    'u' -> "𝛖"
                    'v' -> "𝛗"
                    'w' -> "𝛘"
                    'x' -> "𝛙"
                    'y' -> "𝛚"
                    'z' -> "𝛛"
                    '0' -> "𝟬"
                    '1' -> "𝟭"
                    '2' -> "𝟮"
                    '3' -> "𝟯"
                    '4' -> "𝟰"
                    '5' -> "𝟱"
                    '6' -> "𝟲"
                    '7' -> "𝟳"
                    '8' -> "𝟴"
                    '9' -> "𝟵"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("circles_black", "⬤ Black", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> String(Character.toChars(0x1F150 + (c - 'A')))
                    c.isLowerCase() -> String(Character.toChars(0x1F150 + (c - 'a')))
                    c.isDigit() -> when (c) {
                        '0' -> "\u24FF"; '1' -> "\u278A"; '2' -> "\u278B"
                        '3' -> "\u278C"; '4' -> "\u278D"; '5' -> "\u278E"
                        '6' -> "\u278F"; '7' -> "\u2790"; '8' -> "\u2791"
                        '9' -> "\u2792"
                        else -> c.toString()
                    }
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("circles_white", "○ White", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> String(Character.toChars(0x1F130 + (c - 'A')))
                    c.isLowerCase() -> String(Character.toChars(0x1F130 + (c - 'a')))
                    c.isDigit() -> when (c) {
                        '0' -> "\u24EA"; '1' -> "\u2460"; '2' -> "\u2461"
                        '3' -> "\u2462"; '4' -> "\u2463"; '5' -> "\u2464"
                        '6' -> "\u2465"; '7' -> "\u2466"; '8' -> "\u2467"
                        '9' -> "\u2468"
                        else -> c.toString()
                    }
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("squares_black", "■ Black", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> String(Character.toChars(0x1F170 + (c - 'A')))
                    c.isLowerCase() -> String(Character.toChars(0x1F170 + (c - 'a')))
                    c.isDigit() -> when (c) {
                        '0' -> "\u24FF"; '1' -> "\u278A"; '2' -> "\u278B"
                        '3' -> "\u278C"; '4' -> "\u278D"; '5' -> "\u278E"
                        '6' -> "\u278F"; '7' -> "\u2790"; '8' -> "\u2791"
                        '9' -> "\u2792"
                        else -> c.toString()
                    }
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("squares_white", "□ White", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> String(Character.toChars(0x1F140 + (c - 'A')))
                    c.isLowerCase() -> String(Character.toChars(0x1F140 + (c - 'a')))
                    c.isDigit() -> when (c) {
                        '0' -> "\u24EA"; '1' -> "\u2460"; '2' -> "\u2461"
                        '3' -> "\u2462"; '4' -> "\u2463"; '5' -> "\u2464"
                        '6' -> "\u2465"; '7' -> "\u2466"; '8' -> "\u2467"
                        '9' -> "\u2468"
                        else -> c.toString()
                    }
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("reverse", "ɹǝʌǝɹsǝ", converter = { text ->
            val map = mapOf(
                'a' to '\u0250', 'b' to 'q', 'c' to '\u0254', 'd' to 'p', 'e' to '\u01DD',
                'f' to '\u025F', 'g' to '\u0183', 'h' to '\u0265', 'i' to '\u0131',
                'j' to '\u027E', 'k' to '\u029E', 'l' to 'l', 'm' to '\u026F',
                'n' to 'u', 'o' to 'o', 'p' to 'd', 'q' to 'b', 'r' to '\u0279',
                's' to 's', 't' to '\u0287', 'u' to 'n', 'v' to '\u028C',
                'w' to '\u028D', 'x' to 'x', 'y' to '\u028E', 'z' to 'z',
                'A' to '\u2200', 'B' to 'B', 'C' to '\u0186', 'D' to 'D',
                'E' to '\u018E', 'F' to '\u2132', 'G' to '\u05E4', 'H' to 'H',
                'I' to 'I', 'J' to '\u017F', 'K' to 'K', 'L' to '\u2142',
                'M' to 'W', 'N' to 'N', 'O' to 'O', 'P' to '\u0500',
                'Q' to '\u038C', 'R' to '\u1D1A', 'S' to 'S', 'T' to '\u22A5',
                'U' to '\u2229', 'V' to '\u039B', 'W' to 'M', 'X' to 'X',
                'Y' to '\u2144', 'Z' to 'Z',
                '0' to '0', '1' to '\u21C2', '2' to '\u218A', '3' to '\u218B',
                '4' to '\u218C', '5' to '\u218D', '6' to '9', '7' to '\u218E',
                '8' to '8', '9' to '6',
                '.' to '\u02D9', ',' to '\u2018', '?' to '\u00BF', '!' to '\u00A1',
                '"' to '\u201E', '\'' to ',', '&' to '\u214B'
            )
            text.reversed().map { map[it] ?: it }.joinToString("")
        }))

        fonts.add(FontStyle("small_caps", "Sᴍᴀʟʟ Cᴀᴘs", converter = { text ->
            val map = mapOf(
                'a' to '\u1D00', 'b' to '\u0299', 'c' to '\u1D04', 'd' to '\u1D05',
                'e' to '\u1D07', 'f' to '\uA730', 'g' to '\u0262', 'h' to '\u029C',
                'i' to '\u026A', 'j' to '\u1D0A', 'k' to '\u1D0B', 'l' to '\u029F',
                'm' to '\u1D0D', 'n' to '\u0274', 'o' to '\u1D0F', 'p' to '\u1D18',
                'q' to 'q', 'r' to '\u0280', 's' to '\uA731', 't' to '\u1D1B',
                'u' to '\u1D1C', 'v' to '\u1D20', 'w' to '\u1D21', 'x' to 'x',
                'y' to '\u028F', 'z' to '\u1D22'
            )
            text.map { map[it] ?: it }.joinToString("")
        }))

        fonts.add(FontStyle("bubble", "ⓑⓤⓑⓑⓛⓔ", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> String(Character.toChars(0x24B6 + (c - 'A')))
                    c.isLowerCase() -> String(Character.toChars(0x24D0 + (c - 'a')))
                    c.isDigit() -> when (c) {
                        '0' -> "\u24EA"; '1' -> "\u2460"; '2' -> "\u2461"
                        '3' -> "\u2462"; '4' -> "\u2463"; '5' -> "\u2464"
                        '6' -> "\u2465"; '7' -> "\u2466"; '8' -> "\u2467"
                        '9' -> "\u2468"
                        else -> c.toString()
                    }
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("parenthesized", "⒫⒜⒭⒠⒩", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> String(Character.toChars(0x1F110 + (c - 'A')))
                    c.isLowerCase() -> String(Character.toChars(0x249C + (c - 'a')))
                    c.isDigit() -> when (c) {
                        '1' -> "\u2474"; '2' -> "\u2475"; '3' -> "\u2476"
                        '4' -> "\u2477"; '5' -> "\u2478"; '6' -> "\u2479"
                        '7' -> "\u247A"; '8' -> "\u247B"; '9' -> "\u247C"
                        '0' -> "\u24EA"
                        else -> c.toString()
                    }
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("fullwidth", "ｆｕｌｌｗｉｄｔｈ", converter = { text ->
            text.map { c ->
                when {
                    c in '!'..'~' -> (c.code + 0xFEE0).toChar()
                    c == ' ' -> '\u3000'
                    else -> c
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("tiny_letters", "ᵗⁱⁿʸ", converter = { text ->
            val map = mapOf(
                'a' to '\u1D43', 'b' to '\u1D47', 'c' to '\u1D9C', 'd' to '\u1D48',
                'e' to '\u1D49', 'f' to '\u1DA0', 'g' to '\u1D4D', 'h' to '\u02B0',
                'i' to '\u2071', 'j' to '\u02B2', 'k' to '\u1D4F', 'l' to '\u02E1',
                'm' to '\u1D50', 'n' to '\u207F', 'o' to '\u1D52', 'p' to '\u1D56',
                'q' to 'q', 'r' to '\u02B3', 's' to '\u02E2', 't' to '\u1D57',
                'u' to '\u1D58', 'v' to '\u1D5B', 'w' to '\u02B7', 'x' to '\u02E3',
                'y' to '\u02B8', 'z' to '\u1DBB',
                'A' to '\u1D2C', 'B' to '\u1D2E', 'D' to '\u1D30', 'E' to '\u1D31',
                'G' to '\u1D33', 'H' to '\u1D34', 'I' to '\u1D35', 'J' to '\u1D36',
                'K' to '\u1D37', 'L' to '\u1D38', 'M' to '\u1D39', 'N' to '\u1D3A',
                'O' to '\u1D3C', 'P' to '\u1D3E', 'R' to '\u1D3F', 'T' to '\u1D40',
                'U' to '\u1D41', 'V' to '\u2C7D', 'W' to '\u1D42',
                '0' to '\u2070', '1' to '\u00B9', '2' to '\u00B2', '3' to '\u00B3',
                '4' to '\u2074', '5' to '\u2075', '6' to '\u2076', '7' to '\u2077',
                '8' to '\u2078', '9' to '\u2079'
            )
            text.map { map[it] ?: it }.joinToString("")
        }))

        fonts.add(FontStyle("strikethrough", "S̶t̶r̶i̶k̶e̶", converter = { text ->
            text.map { "$it\u0336" }.joinToString("")
        }))

        fonts.add(FontStyle("underline", "U̲n̲d̲e̲r̲l̲i̲n̲e̲", converter = { text ->
            text.map { "$it\u0332" }.joinToString("")
        }))

        fonts.add(FontStyle("double_underline", "D̳o̳u̳b̳l̳e̳", converter = { text ->
            text.map { "$it\u0333" }.joinToString("")
        }))

        fonts.add(FontStyle("wavy", "W̰a̰v̰y̰", converter = { text ->
            text.map { "$it\u0330" }.joinToString("")
        }))

        fonts.add(FontStyle("dotted", "Ḋȯṫṫėḋ", converter = { text ->
            text.map { "$it\u0307" }.joinToString("")
        }))

        fonts.add(FontStyle("slash", "S̷l̷a̷s̷h̷", converter = { text ->
            text.map { "$it\u0337" }.joinToString("")
        }))

        fonts.add(FontStyle("cross", "C̶r̶o̶s̶s̶", converter = { text ->
            text.map { "$it\u0336" }.joinToString("")
        }))

        fonts.add(FontStyle("arrow", "A⃗r⃗r⃗o⃗w⃗", converter = { text ->
            text.map { "$it\u20D7" }.joinToString("")
        }))

        fonts.add(FontStyle("heart", "H̩e̩a̩r̩t̩", converter = { text ->
            text.map { "$it\u0329" }.joinToString("")
        }))

        fonts.add(FontStyle("star", "S͙t͙a͙r͙", converter = { text ->
            text.map { "$it\u0359" }.joinToString("")
        }))

        fonts.add(FontStyle("bridge", "B͆r͆i͆d͆g͆e͆", converter = { text ->
            text.map { "$it\u0346" }.joinToString("")
        }))

        fonts.add(FontStyle("smile", "S̑m̑ȋl̑ȇ", converter = { text ->
            text.map { "$it\u0311" }.joinToString("")
        }))

        fonts.add(FontStyle("thunder", "T̽h̽u̽n̽d̽e̽r̽", converter = { text ->
            text.map { "$it\u033D" }.joinToString("")
        }))

        fonts.add(FontStyle("railway", "R̤a̤i̤l̤w̤a̤y̤", converter = { text ->
            text.map { "$it\u0324" }.joinToString("")
        }))

        fonts.add(FontStyle("wheels", "W̥h̥e̥e̥l̥s̥", converter = { text ->
            text.map { "$it\u0325" }.joinToString("")
        }))

        fonts.add(FontStyle("track", "T̪r̪a̪c̪k̪", converter = { text ->
            text.map { "$it\u032A" }.joinToString("")
        }))

        fonts.add(FontStyle("up_above", "U̺p̺", converter = { text ->
            text.map { "$it\u033A" }.joinToString("")
        }))

        fonts.add(FontStyle("sandwich", "S̾a̾n̾d̾w̾i̾c̾h̾", converter = { text ->
            text.map { "$it\u033E" }.joinToString("")
        }))

        fonts.add(FontStyle("fair", "F̒a̒i̒r̒", converter = { text ->
            text.map { "$it\u0312" }.joinToString("")
        }))

        fonts.add(FontStyle("bar", "B̅a̅r̅", converter = { text ->
            text.map { "$it\u0305" }.joinToString("")
        }))

        fonts.add(FontStyle("waves", "W̴a̴v̴e̴s̴", converter = { text ->
            text.map { "$it\u0334" }.joinToString("")
        }))

        fonts.add(FontStyle("sandra_peel", "S̻a̻n̻d̻r̻a̻", converter = { text ->
            text.map { "$it\u033B" }.joinToString("")
        }))

        fonts.add(FontStyle("heater", "H̹e̹a̹t̹e̹r̹", converter = { text ->
            text.map { "$it\u0339" }.joinToString("")
        }))

        fonts.add(FontStyle("seawave", "S̳e̳a̳w̳a̳v̳e̳", converter = { text ->
            text.map { "$it\u0333" }.joinToString("")
        }))

        fonts.add(FontStyle("wall", "W̼a̼l̼l̼", converter = { text ->
            text.map { "$it\u033C" }.joinToString("")
        }))

        fonts.add(FontStyle("joiner", "J̡o̡i̡n̡e̡r̡", converter = { text ->
            text.map { "$it\u0321" }.joinToString("")
        }))

        fonts.add(FontStyle("wrapped", "W̧ŗa̧p̧p̧ȩḑ", converter = { text ->
            text.map { "$it\u0327" }.joinToString("")
        }))

        fonts.add(FontStyle("box_packed", "B̲o̲x̲", converter = { text ->
            text.map { "$it\u0332" }.joinToString("")
        }))

        fonts.add(FontStyle("outer_pack", "O̶u̶t̶e̶r̶", converter = { text ->
            text.map { "$it\u0336" }.joinToString("")
        }))

        fonts.add(FontStyle("dot_packed", "D̤o̤t̤", converter = { text ->
            text.map { "$it\u0324" }.joinToString("")
        }))

        fonts.add(FontStyle("corner", "C̘o̘r̘n̘e̘r̘", converter = { text ->
            text.map { "$it\u0318" }.joinToString("")
        }))

        fonts.add(FontStyle("joints", "J̺o̺i̺n̺t̺s̺", converter = { text ->
            text.map { "$it\u033A" }.joinToString("")
        }))

        fonts.add(FontStyle("directions", "D̻i̻r̻e̻c̻t̻i̻o̻n̻s̻", converter = { text ->
            text.map { "$it\u033B" }.joinToString("")
        }))

        fonts.add(FontStyle("star_join", "S̼t̼a̼r̼", converter = { text ->
            text.map { "$it\u033C" }.joinToString("")
        }))

        fonts.add(FontStyle("currency", "₵ɄⱤⱤɆ₦₵Ɏ", converter = { text ->
            val map = mapOf(
                'A' to '\u20B3', 'B' to '\u20B1', 'C' to '\u20A1', 'D' to '\u20AF',
                'E' to '\u20AC', 'F' to '\u20A3', 'G' to '\u20B2', 'H' to '\u20B4',
                'I' to '\u20A6', 'J' to '\u20A5', 'K' to '\u20AD', 'L' to '\u20A4',
                'M' to '\u20A5', 'N' to '\u20A6', 'O' to '\u20B5', 'P' to '\u20B1',
                'R' to '\u20A8', 'S' to '\u20AA', 'T' to '\u20AE', 'U' to '\u20B5',
                'W' to '\u20A9', 'Y' to '\u00A5', 'Z' to '\u20B5',
                'a' to '\u00A2', 'b' to '\u20B1', 'c' to '\u00A2', 'd' to '\u20AF',
                'e' to '\u20AC', 'f' to '\u0192', 'g' to '\u20B2', 'h' to '\u20B4',
                'i' to '\u17DB', 'k' to '\u20AD', 'l' to '\u00A3', 'm' to '\u20A5',
                'n' to '\u20A6', 'o' to '\u20B5', 'p' to '\u20B1', 'r' to '\u20A8',
                's' to '\u20AA', 't' to '\u20AE', 'u' to '\u20B5', 'w' to '\u20A9',
                'y' to '\u00A5'
            )
            text.map { map[it] ?: it }.joinToString("")
        }))

        fonts.add(FontStyle("warn", "W̸a̸r̸n̸", converter = { text ->
            text.map { "$it\u0338" }.joinToString("")
        }))

        fonts.add(FontStyle("birds", "B̬i̬r̬d̬s̬", converter = { text ->
            text.map { "$it\u032C" }.joinToString("")
        }))

        fonts.add(FontStyle("rays", "R̦a̦y̦ș", converter = { text ->
            text.map { "$it\u0326" }.joinToString("")
        }))

        fonts.add(FontStyle("magna", "M̩a̩g̩n̩a̩", converter = { text ->
            text.map { "$it\u0329" }.joinToString("")
        }))

        fonts.add(FontStyle("fancy", "F⃠a⃠n⃠c⃠y⃠", converter = { text ->
            text.map { "$it\u20E0" }.joinToString("")
        }))

        fonts.add(FontStyle("thin", "T⃩h⃩i⃩n⃩", converter = { text ->
            text.map { "$it\u20E9" }.joinToString("")
        }))

        fonts.add(FontStyle("gloom", "G̷l̷o̷o̷m̷", converter = { text ->
            text.map { "$it\u0337" }.joinToString("")
        }))

        fonts.add(FontStyle("paranormal", "P̶a̶r̶a̶n̶o̶r̶m̶a̶l̶", converter = { text ->
            text.map { "$it\u0336" }.joinToString("")
        }))

        fonts.add(FontStyle("coffee", "C̷o̷f̷f̷e̷e̷", converter = { text ->
            text.map { "$it\u0337" }.joinToString("")
        }))

        fonts.add(FontStyle("bold_serif", "𝐁𝐨𝐥𝐝 𝐒𝐞𝐫𝐢𝐟", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝐀"; 'B' -> "𝐁"; 'C' -> "𝐂"; 'D' -> "𝐃"; 'E' -> "𝐄"
                    'F' -> "𝐅"; 'G' -> "𝐆"; 'H' -> "𝐇"; 'I' -> "𝐈"; 'J' -> "𝐉"
                    'K' -> "𝐊"; 'L' -> "𝐋"; 'M' -> "𝐌"; 'N' -> "𝐍"; 'O' -> "𝐎"
                    'P' -> "𝐏"; 'Q' -> "𝐐"; 'R' -> "𝐑"; 'S' -> "𝐒"; 'T' -> "𝐓"
                    'U' -> "𝐔"; 'V' -> "𝐕"; 'W' -> "𝐖"; 'X' -> "𝐗"; 'Y' -> "𝐘"
                    'Z' -> "𝐙"; 'a' -> "𝐚"; 'b' -> "𝐛"; 'c' -> "𝐜"; 'd' -> "𝐝"
                    'e' -> "𝐞"; 'f' -> "𝐟"; 'g' -> "𝐠"; 'h' -> "𝐡"; 'i' -> "𝐢"
                    'j' -> "𝐣"; 'k' -> "𝐤"; 'l' -> "𝐥"; 'm' -> "𝐦"; 'n' -> "𝐧"
                    'o' -> "𝐨"; 'p' -> "𝐩"; 'q' -> "𝐪"; 'r' -> "𝐫"; 's' -> "𝐬"
                    't' -> "𝐭"; 'u' -> "𝐮"; 'v' -> "𝐯"; 'w' -> "𝐰"; 'x' -> "𝐱"
                    'y' -> "𝐲"; 'z' -> "𝐳"
                    '0' -> "𝟎"; '1' -> "𝟏"; '2' -> "𝟐"; '3' -> "𝟑"; '4' -> "𝟒"
                    '5' -> "𝟓"; '6' -> "𝟔"; '7' -> "𝟕"; '8' -> "𝟖"; '9' -> "𝟗"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("bold_italic", "𝑩𝒐𝒍𝒅 𝑰𝒕𝒂𝒍𝒊𝒄", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝑨"; 'B' -> "𝑩"; 'C' -> "𝑪"; 'D' -> "𝑫"; 'E' -> "𝑬"
                    'F' -> "𝑭"; 'G' -> "𝑮"; 'H' -> "𝑯"; 'I' -> "𝑰"; 'J' -> "𝑱"
                    'K' -> "𝑲"; 'L' -> "𝑳"; 'M' -> "𝑴"; 'N' -> "𝑵"; 'O' -> "𝑶"
                    'P' -> "𝑷"; 'Q' -> "𝑸"; 'R' -> "𝑹"; 'S' -> "𝑺"; 'T' -> "𝑻"
                    'U' -> "𝑼"; 'V' -> "𝑽"; 'W' -> "𝑾"; 'X' -> "𝑿"; 'Y' -> "𝒀"
                    'Z' -> "𝒁"; 'a' -> "𝒂"; 'b' -> "𝒃"; 'c' -> "𝒄"; 'd' -> "𝒅"
                    'e' -> "𝒆"; 'f' -> "𝒇"; 'g' -> "𝒈"; 'h' -> "𝒉"; 'i' -> "𝒊"
                    'j' -> "𝒋"; 'k' -> "𝒌"; 'l' -> "𝒍"; 'm' -> "𝒎"; 'n' -> "𝒏"
                    'o' -> "𝒐"; 'p' -> "𝒑"; 'q' -> "𝒒"; 'r' -> "𝒓"; 's' -> "𝒔"
                    't' -> "𝒕"; 'u' -> "𝒖"; 'v' -> "𝒗"; 'w' -> "𝒘"; 'x' -> "𝒙"
                    'y' -> "𝒚"; 'z' -> "𝒛"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("sans_italic", "𝘚𝘢𝘯𝘴 𝘐𝘵𝘢𝘭𝘪𝘤", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝘈"; 'B' -> "𝘉"; 'C' -> "𝘊"; 'D' -> "𝘋"; 'E' -> "𝘌"
                    'F' -> "𝘍"; 'G' -> "𝘎"; 'H' -> "𝘏"; 'I' -> "𝘐"; 'J' -> "𝘑"
                    'K' -> "𝘒"; 'L' -> "𝘓"; 'M' -> "𝘔"; 'N' -> "𝘕"; 'O' -> "𝘖"
                    'P' -> "𝘗"; 'Q' -> "𝘘"; 'R' -> "𝘙"; 'S' -> "𝘚"; 'T' -> "𝘛"
                    'U' -> "𝘜"; 'V' -> "𝘝"; 'W' -> "𝘞"; 'X' -> "𝘟"; 'Y' -> "𝘠"
                    'Z' -> "𝘡"; 'a' -> "𝘢"; 'b' -> "𝘣"; 'c' -> "𝘤"; 'd' -> "𝘥"
                    'e' -> "𝘦"; 'f' -> "𝘧"; 'g' -> "𝘨"; 'h' -> "𝘩"; 'i' -> "𝘪"
                    'j' -> "𝘫"; 'k' -> "𝘬"; 'l' -> "𝘭"; 'm' -> "𝘮"; 'n' -> "𝘯"
                    'o' -> "𝘰"; 'p' -> "𝘱"; 'q' -> "𝘲"; 'r' -> "𝘳"; 's' -> "𝘴"
                    't' -> "𝘵"; 'u' -> "𝘶"; 'v' -> "𝘷"; 'w' -> "𝘸"; 'x' -> "𝘹"
                    'y' -> "𝘺"; 'z' -> "𝘻"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("bold_sans", "𝗕𝗼𝗹𝗱 𝗦𝗮𝗻𝘀", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝗔"; 'B' -> "𝗕"; 'C' -> "𝗖"; 'D' -> "𝗗"; 'E' -> "𝗘"
                    'F' -> "𝗙"; 'G' -> "𝗚"; 'H' -> "𝗛"; 'I' -> "𝗜"; 'J' -> "𝗝"
                    'K' -> "𝗞"; 'L' -> "𝗟"; 'M' -> "𝗠"; 'N' -> "𝗡"; 'O' -> "𝗢"
                    'P' -> "𝗣"; 'Q' -> "𝗤"; 'R' -> "𝗥"; 'S' -> "𝗦"; 'T' -> "𝗧"
                    'U' -> "𝗨"; 'V' -> "𝗩"; 'W' -> "𝗪"; 'X' -> "𝗫"; 'Y' -> "𝗬"
                    'Z' -> "𝗭"; 'a' -> "𝗮"; 'b' -> "𝗯"; 'c' -> "𝗰"; 'd' -> "𝗱"
                    'e' -> "𝗲"; 'f' -> "𝗳"; 'g' -> "𝗴"; 'h' -> "𝗵"; 'i' -> "𝗶"
                    'j' -> "𝗷"; 'k' -> "𝗸"; 'l' -> "𝗹"; 'm' -> "𝗺"; 'n' -> "𝗻"
                    'o' -> "𝗼"; 'p' -> "𝗽"; 'q' -> "𝗾"; 'r' -> "𝗿"; 's' -> "𝘀"
                    't' -> "𝘁"; 'u' -> "𝘂"; 'v' -> "𝘃"; 'w' -> "𝘄"; 'x' -> "𝘅"
                    'y' -> "𝘆"; 'z' -> "𝘇"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("bold_italic_sans", "𝙎𝙖𝙣𝙨 𝘽𝙤𝙡𝙙 𝙄𝙩𝙖𝙡𝙞𝙘", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝘼"; 'B' -> "𝘽"; 'C' -> "𝘾"; 'D' -> "𝘿"; 'E' -> "𝙀"
                    'F' -> "𝙁"; 'G' -> "𝙂"; 'H' -> "𝙃"; 'I' -> "𝙄"; 'J' -> "𝙅"
                    'K' -> "𝙆"; 'L' -> "𝙇"; 'M' -> "𝙈"; 'N' -> "𝙉"; 'O' -> "𝙊"
                    'P' -> "𝙋"; 'Q' -> "𝙌"; 'R' -> "𝙍"; 'S' -> "𝙎"; 'T' -> "𝙏"
                    'U' -> "𝙐"; 'V' -> "𝙑"; 'W' -> "𝙒"; 'X' -> "𝙓"; 'Y' -> "𝙔"
                    'Z' -> "𝙕"; 'a' -> "𝙖"; 'b' -> "𝙗"; 'c' -> "𝙘"; 'd' -> "𝙙"
                    'e' -> "𝙚"; 'f' -> "𝙛"; 'g' -> "𝙜"; 'h' -> "𝙝"; 'i' -> "𝙞"
                    'j' -> "𝙟"; 'k' -> "𝙠"; 'l' -> "𝙡"; 'm' -> "𝙢"; 'n' -> "𝙣"
                    'o' -> "𝙤"; 'p' -> "𝙥"; 'q' -> "𝙦"; 'r' -> "𝙧"; 's' -> "𝙨"
                    't' -> "𝙩"; 'u' -> "𝙪"; 'v' -> "𝙫"; 'w' -> "𝙬"; 'x' -> "𝙭"
                    'y' -> "𝙮"; 'z' -> "𝙯"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("light_serif", "Sᴇʀɪꜰ Lɪɢʜᴛ", converter = { text ->
            val map = mapOf(
                'A' to "A", 'B' to "B", 'C' to "C", 'D' to "D", 'E' to "E",
                'F' to "F", 'G' to "G", 'H' to "H", 'I' to "I", 'J' to "J",
                'K' to "K", 'L' to "L", 'M' to "M", 'N' to "N", 'O' to "O",
                'P' to "P", 'Q' to "Q", 'R' to "R", 'S' to "S", 'T' to "T",
                'U' to "U", 'V' to "V", 'W' to "W", 'X' to "X", 'Y' to "Y",
                'Z' to "Z",
                'a' to "ᴀ", 'b' to "ʙ", 'c' to "ᴄ", 'd' to "ᴅ", 'e' to "ᴇ",
                'f' to "ꜰ", 'g' to "ɢ", 'h' to "ʜ", 'i' to "ɪ", 'j' to "ᴊ",
                'k' to "ᴋ", 'l' to "ʟ", 'm' to "ᴍ", 'n' to "ɴ", 'o' to "ᴏ",
                'p' to "ᴘ", 'q' to "ǫ", 'r' to "ʀ", 's' to "ꜱ", 't' to "ᴛ",
                'u' to "ᴜ", 'v' to "ᴠ", 'w' to "ᴡ", 'x' to "x", 'y' to "ʏ",
                'z' to "ᴢ"
            )
            text.map { map[it] ?: it }.joinToString("")
        }))

        fonts.add(FontStyle("subscript", "Sᵤbₛcᵣᵢₚₜ", converter = { text ->
            val map = mapOf(
                'a' to "ₐ", 'e' to "ₑ", 'h' to "ₕ", 'i' to "ᵢ", 'j' to "ⱼ",
                'k' to "ₖ", 'l' to "ₗ", 'm' to "ₘ", 'n' to "ₙ", 'o' to "ₒ",
                'p' to "ₚ", 'r' to "ᵣ", 's' to "ₛ", 't' to "ₜ", 'u' to "ᵤ",
                'v' to "ᵥ", 'x' to "ₓ",
                '0' to "₀", '1' to "₁", '2' to "₂", '3' to "₃", '4' to "₄",
                '5' to "₅", '6' to "₆", '7' to "₇", '8' to "₈", '9' to "₉"
            )
            text.map { map[it] ?: it }.joinToString("")
        }))

        fonts.add(FontStyle("medieval", "𝔐𝔢𝔡𝔦𝔢𝔳𝔞𝔩", converter = { text ->
            text.map { c ->
                when (c) {
                    'A' -> "𝔄"; 'B' -> "𝔅"; 'C' -> "ℭ"; 'D' -> "𝔇"; 'E' -> "𝔈"
                    'F' -> "𝔉"; 'G' -> "𝔊"; 'H' -> "ℌ"; 'I' -> "ℑ"; 'J' -> "𝔍"
                    'K' -> "𝔎"; 'L' -> "𝔏"; 'M' -> "𝔐"; 'N' -> "𝔑"; 'O' -> "𝔒"
                    'P' -> "𝔓"; 'Q' -> "𝔔"; 'R' -> "ℜ"; 'S' -> "𝔖"; 'T' -> "𝔗"
                    'U' -> "𝔘"; 'V' -> "𝔙"; 'W' -> "𝔚"; 'X' -> "𝔛"; 'Y' -> "𝔜"
                    'Z' -> "ℨ"; 'a' -> "𝔞"; 'b' -> "𝔟"; 'c' -> "𝔠"; 'd' -> "𝔡"
                    'e' -> "𝔢"; 'f' -> "𝔣"; 'g' -> "𝔤"; 'h' -> "𝔥"; 'i' -> "𝔦"
                    'j' -> "𝔧"; 'k' -> "𝔨"; 'l' -> "𝔩"; 'm' -> "𝔪"; 'n' -> "𝔫"
                    'o' -> "𝔬"; 'p' -> "𝔭"; 'q' -> "𝔮"; 'r' -> "𝔯"; 's' -> "𝔰"
                    't' -> "𝔱"; 'u' -> "𝔲"; 'v' -> "𝔳"; 'w' -> "𝔴"; 'x' -> "𝔵"
                    'y' -> "𝔶"; 'z' -> "𝔷"
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("upside_down", "∩psᴉpǝ poʍu", converter = { text ->
            val map = mapOf(
                'a' to "ɐ", 'b' to "q", 'c' to "ɔ", 'd' to "p", 'e' to "ǝ",
                'f' to "ɟ", 'g' to "ƃ", 'h' to "ɥ", 'i' to "ᴉ", 'j' to "ɾ",
                'k' to "ʞ", 'l' to "l", 'm' to "ɯ", 'n' to "u", 'o' to "o",
                'p' to "d", 'q' to "b", 'r' to "ɹ", 's' to "s", 't' to "ʇ",
                'u' to "n", 'v' to "ʌ", 'w' to "ʍ", 'x' to "x", 'y' to "ʎ",
                'z' to "z",
                'A' to "∀", 'B' to "B", 'C' to "Ɔ", 'D' to "D", 'E' to "Ǝ",
                'F' to "Ⅎ", 'G' to "פ", 'H' to "H", 'I' to "I", 'J' to "ſ",
                'K' to "K", 'L' to "˥", 'M' to "W", 'N' to "N", 'O' to "O",
                'P' to "Ԁ", 'Q' to "Ό", 'R' to "ᴚ", 'S' to "S", 'T' to "⊥",
                'U' to "∩", 'V' to "Λ", 'W' to "M", 'X' to "X", 'Y' to "⅄",
                'Z' to "Z",
                '0' to "0", '1' to "Ɩ", '2' to "ᄅ", '3' to "Ɛ", '4' to "ㄣ",
                '5' to "ϛ", '6' to "9", '7' to "ㄥ", '8' to "8", '9' to "6",
                '.' to "˙", ',' to "'", '?' to "¿", '!' to "¡"
            )
            text.reversed().map { map[it] ?: it }.joinToString("")
        }))

        fonts.add(FontStyle("circled", "Ⓒⓘⓡⓒⓛⓔⓓ", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> String(Character.toChars(0x24B6 + (c - 'A')))
                    c.isLowerCase() -> String(Character.toChars(0x24D0 + (c - 'a')))
                    c.isDigit() -> when (c) {
                        '0' -> "⓪"; '1' -> "①"; '2' -> "②"; '3' -> "③"; '4' -> "④"
                        '5' -> "⑤"; '6' -> "⑥"; '7' -> "⑦"; '8' -> "⑧"; '9' -> "⑨"
                        else -> c.toString()
                    }
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("squared", "🅂🅀🅄🄰🅁🄴🄳", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> String(Character.toChars(0x1F130 + (c - 'A')))
                    c.isLowerCase() -> String(Character.toChars(0x1F130 + (c - 'a')))
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("negative_squared", "🅂🅀🅄🄰🅁🄴🄳", converter = { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> String(Character.toChars(0x1F170 + (c - 'A')))
                    c.isLowerCase() -> String(Character.toChars(0x1F170 + (c - 'a')))
                    else -> c.toString()
                }
            }.joinToString("")
        }))

        fonts.add(FontStyle("regional_indicator", "🇷 🇪 🇬 🇮 🇴 🇳 🇦 🇱", converter = { text ->
            text.map { c ->
                when {
                    c in 'A'..'Z' -> String(Character.toChars(0x1F1E6 + (c - 'A')))
                    c in 'a'..'z' -> String(Character.toChars(0x1F1E6 + (c - 'a')))
                    else -> c.toString()
                }
            }.joinToString(" ")
        }))

        fonts.add(FontStyle("double_overline", "D̿o̿u̿b̿l̿e̿", converter = { text ->
            text.map { "$it\u033F" }.joinToString("")
        }))

        fonts.add(FontStyle("breve", "B̆r̆ĕv̆ĕ", converter = { text ->
            text.map { "$it\u0306" }.joinToString("")
        }))

        fonts.add(FontStyle("caron", "Čǎřǒň", converter = { text ->
            text.map { "$it\u030C" }.joinToString("")
        }))

        fonts.add(FontStyle("circumflex", "Ĉîr̂ĉûm̂f̂l̂êx̂", converter = { text ->
            text.map { "$it\u0302" }.joinToString("")
        }))

        fonts.add(FontStyle("tilde", "T̃ĩl̃d̃ẽ", converter = { text ->
            text.map { "$it\u0303" }.joinToString("")
        }))

        fonts.add(FontStyle("diaeresis", "D̈ïäër̈ës̈ïs̈", converter = { text ->
            text.map { "$it\u0308" }.joinToString("")
        }))

        fonts.add(FontStyle("cedilla", "Çȩḑi̧ļļa̧", converter = { text ->
            text.map { "$it\u0327" }.joinToString("")
        }))

        fonts.add(FontStyle("ogonek", "Ǫg̨ǫn̨ęk̨", converter = { text ->
            text.map { "$it\u0328" }.joinToString("")
        }))

        fonts.add(FontStyle("ring_above", "R̊i̊n̊g̊", converter = { text ->
            text.map { "$it\u030A" }.joinToString("")
        }))

        fonts.add(FontStyle("ring_below", "R̥i̥n̥g̥", converter = { text ->
            text.map { "$it\u0325" }.joinToString("")
        }))

        fonts.add(FontStyle("hook_above", "H̉ỏỏk̉", converter = { text ->
            text.map { "$it\u0309" }.joinToString("")
        }))

        fonts.add(FontStyle("horn", "H̛ơr̛n̛", converter = { text ->
            text.map { "$it\u031B" }.joinToString("")
        }))

        fonts.add(FontStyle("acute", "Áćút́é", converter = { text ->
            text.map { "$it\u0301" }.joinToString("")
        }))

        fonts.add(FontStyle("grave", "G̀r̀àv̀è", converter = { text ->
            text.map { "$it\u0300" }.joinToString("")
        }))

        fonts.add(FontStyle("macron", "M̄āc̄r̄ōn̄", converter = { text ->
            text.map { "$it\u0304" }.joinToString("")
        }))

        fonts.add(FontStyle("double_acute", "D̋őűb̋l̋e̋", converter = { text ->
            text.map { "$it\u030B" }.joinToString("")
        }))

        fonts.add(FontStyle("vertical_line_above", "V̍e̍r̍t̍i̍c̍a̍l̍", converter = { text ->
            text.map { "$it\u030D" }.joinToString("")
        }))

        fonts.add(FontStyle("vertical_line_below", "V̩e̩r̩t̩i̩c̩a̩l̩", converter = { text ->
            text.map { "$it\u0329" }.joinToString("")
        }))

        fonts.add(FontStyle("x_above", "X͓x͓x͓", converter = { text ->
            text.map { "$it\u0353" }.joinToString("")
        }))

        fonts.add(FontStyle("left_half_ring", "L̜e̜f̜t̜", converter = { text ->
            text.map { "$it\u031C" }.joinToString("")
        }))

        fonts.add(FontStyle("right_half_ring", "R̹i̹g̹h̹t̹", converter = { text ->
            text.map { "$it\u0339" }.joinToString("")
        }))

        fonts.add(FontStyle("palatalized", "P̡a̡l̡a̡t̡a̡l̡", converter = { text ->
            text.map { "$it\u0321" }.joinToString("")
        }))

        fonts.add(FontStyle("retroflex", "R̢e̢t̢r̢o̢f̢l̢e̢x̢", converter = { text ->
            text.map { "$it\u0322" }.joinToString("")
        }))

        fonts.add(FontStyle("superscript", "Sᵘᵖᵉʳˢᶜʳⁱᵖᵗ", converter = { text ->
            val map = mapOf(
                'a' to "ᵃ", 'b' to "ᵇ", 'c' to "ᶜ", 'd' to "ᵈ", 'e' to "ᵉ",
                'f' to "ᶠ", 'g' to "ᵍ", 'h' to "ʰ", 'i' to "ⁱ", 'j' to "ʲ",
                'k' to "ᵏ", 'l' to "ˡ", 'm' to "ᵐ", 'n' to "ⁿ", 'o' to "ᵒ",
                'p' to "ᵖ", 'q' to "q", 'r' to "ʳ", 's' to "ˢ", 't' to "ᵗ",
                'u' to "ᵘ", 'v' to "ᵛ", 'w' to "ʷ", 'x' to "ˣ", 'y' to "ʸ",
                'z' to "ᶻ",
                'A' to "ᴬ", 'B' to "ᴮ", 'C' to "C", 'D' to "ᴰ", 'E' to "ᴱ",
                'F' to "F", 'G' to "ᴳ", 'H' to "ᴴ", 'I' to "ᴵ", 'J' to "ᴶ",
                'K' to "ᴷ", 'L' to "ᴸ", 'M' to "ᴹ", 'N' to "ᴺ", 'O' to "ᴼ",
                'P' to "ᴾ", 'Q' to "Q", 'R' to "ᴿ", 'T' to "ᵀ", 'U' to "ᵁ",
                'V' to "ⱽ", 'W' to "ᵂ",
                '0' to "⁰", '1' to "¹", '2' to "²", '3' to "³", '4' to "⁴",
                '5' to "⁵", '6' to "⁶", '7' to "⁷", '8' to "⁸", '9' to "⁹"
            )
            text.map { map[it] ?: it }.joinToString("")
        }))

        DebugLogger.i("FontManager initialized with ${fonts.size} fonts")
    }
}
