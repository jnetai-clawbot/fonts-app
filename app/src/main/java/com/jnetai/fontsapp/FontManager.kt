package com.jnetai.fontsapp

data class FontStyle(
    val name: String,
    val displayName: String,
    val converter: (String) -> String,
    val isUnicode: Boolean = true
)

object FontManager {
    private val fonts = mutableListOf<FontStyle>()
    private val customFonts = mutableListOf<FontStyle>()

    val allFonts: List<FontStyle>
        get() {
            val ordered = SettingsManager.getFontOrder()
            if (ordered.isEmpty()) return fonts + customFonts
            val orderedList = mutableListOf<FontStyle>()
            val remaining = (fonts + customFonts).toMutableList()
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

        // Box Letters (black filled squares with white letters)
        fonts.add(FontStyle("box_black", "Box Black") { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> "\uD83C\uDD70" + (c - 'A').toChar()
                    c.isLowerCase() -> "\uD83C\uDD70" + (c - 'a').toChar()
                    c.isDigit() -> (0x1F7E0 + (c - '0')).toChar().toString()
                    c == ' ' -> "  "
                    else -> c.toString()
                }
            }.joinToString("")
        })

        // Box White (white squares with black letters)
        fonts.add(FontStyle("box_white", "Box White") { text ->
            text.map { c ->
                when {
                    c.isUpperCase() -> "\uD83C\uDD70" + (c - 'A').toChar()
                    c.isLowerCase() -> "\uD83C\uDD70" + (c - 'a').toChar()
                    c.isDigit() -> (0x1F7E0 + (c - '0')).toChar().toString()
                    c == ' ' -> "  "
                    else -> c.toString()
                }
            }.joinToString("")
        })

        // Gothic (Fraktur bold)
        fonts.add(FontStyle("gothic", "𝕲𝖔𝖙𝖍𝖎𝖈") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDD6C'; 'B' -> '\uD835\uDD6D'; 'C' -> '\uD835\uDD6E'
                    'D' -> '\uD835\uDD6F'; 'E' -> '\uD835\uDD70'; 'F' -> '\uD835\uDD71'
                    'G' -> '\uD835\uDD72'; 'H' -> '\uD835\uDD73'; 'I' -> '\uD835\uDD74'
                    'J' -> '\uD835\uDD75'; 'K' -> '\uD835\uDD76'; 'L' -> '\uD835\uDD77'
                    'M' -> '\uD835\uDD78'; 'N' -> '\uD835\uDD79'; 'O' -> '\uD835\uDD7A'
                    'P' -> '\uD835\uDD7B'; 'Q' -> '\uD835\uDD7C'; 'R' -> '\uD835\uDD7D'
                    'S' -> '\uD835\uDD7E'; 'T' -> '\uD835\uDD7F'; 'U' -> '\uD835\uDD80'
                    'V' -> '\uD835\uDD81'; 'W' -> '\uD835\uDD82'; 'X' -> '\uD835\uDD83'
                    'Y' -> '\uD835\uDD84'; 'Z' -> '\uD835\uDD85'
                    'a' -> '\uD835\uDD86'; 'b' -> '\uD835\uDD87'; 'c' -> '\uD835\uDD88'
                    'd' -> '\uD835\uDD89'; 'e' -> '\uD835\uDD8A'; 'f' -> '\uD835\uDD8B'
                    'g' -> '\uD835\uDD8C'; 'h' -> '\uD835\uDD8D'; 'i' -> '\uD835\uDD8E'
                    'j' -> '\uD835\uDD8F'; 'k' -> '\uD835\uDD90'; 'l' -> '\uD835\uDD91'
                    'm' -> '\uD835\uDD92'; 'n' -> '\uD835\uDD93'; 'o' -> '\uD835\uDD94'
                    'p' -> '\uD835\uDD95'; 'q' -> '\uD835\uDD96'; 'r' -> '\uD835\uDD97'
                    's' -> '\uD835\uDD98'; 't' -> '\uD835\uDD99'; 'u' -> '\uD835\uDD9A'
                    'v' -> '\uD835\uDD9B'; 'w' -> '\uD835\uDD9C'; 'x' -> '\uD835\uDD9D'
                    'y' -> '\uD835\uDD9E'; 'z' -> '\uD835\uDD9F'
                    else -> c
                }
            }.joinToString("")
        })

        // Typewriter (monospace)
        fonts.add(FontStyle("typewriter", "𝚃𝚢𝚙𝚎𝚠𝚛𝚒𝚝𝚎𝚛") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDEA8'; 'B' -> '\uD835\uDEA9'; 'C' -> '\uD835\uDEAA'
                    'D' -> '\uD835\uDEAB'; 'E' -> '\uD835\uDEAC'; 'F' -> '\uD835\uDEAD'
                    'G' -> '\uD835\uDEAE'; 'H' -> '\uD835\uDEAF'; 'I' -> '\uD835\uDEB0'
                    'J' -> '\uD835\uDEB1'; 'K' -> '\uD835\uDEB2'; 'L' -> '\uD835\uDEB3'
                    'M' -> '\uD835\uDEB4'; 'N' -> '\uD835\uDEB5'; 'O' -> '\uD835\uDEB6'
                    'P' -> '\uD835\uDEB7'; 'Q' -> '\uD835\uDEB8'; 'R' -> '\uD835\uDEB9'
                    'S' -> '\uD835\uDEBA'; 'T' -> '\uD835\uDEBB'; 'U' -> '\uD835\uDEBC'
                    'V' -> '\uD835\uDEBD'; 'W' -> '\uD835\uDEBE'; 'X' -> '\uD835\uDEBF'
                    'Y' -> '\uD835\uDEC0'; 'Z' -> '\uD835\uDEC1'
                    'a' -> '\uD835\uDEC2'; 'b' -> '\uD835\uDEC3'; 'c' -> '\uD835\uDEC4'
                    'd' -> '\uD835\uDEC5'; 'e' -> '\uD835\uDEC6'; 'f' -> '\uD835\uDEC7'
                    'g' -> '\uD835\uDEC8'; 'h' -> '\uD835\uDEC9'; 'i' -> '\uD835\uDECA'
                    'j' -> '\uD835\uDECB'; 'k' -> '\uD835\uDECC'; 'l' -> '\uD835\uDECD'
                    'm' -> '\uD835\uDECE'; 'n' -> '\uD835\uDECF'; 'o' -> '\uD835\uDED0'
                    'p' -> '\uD835\uDED1'; 'q' -> '\uD835\uDED2'; 'r' -> '\uD835\uDED3'
                    's' -> '\uD835\uDED4'; 't' -> '\uD835\uDED5'; 'u' -> '\uD835\uDED6'
                    'v' -> '\uD835\uDED7'; 'w' -> '\uD835\uDED8'; 'x' -> '\uD835\uDED9'
                    'y' -> '\uD835\uDEDA'; 'z' -> '\uD835\uDEDB'
                    '0' -> '\uD835\uDFEC'; '1' -> '\uD835\uDFED'; '2' -> '\uD835\uDFEE'
                    '3' -> '\uD835\uDFEF'; '4' -> '\uD835\uDFF0'; '5' -> '\uD835\uDFF1'
                    '6' -> '\uD835\uDFF2'; '7' -> '\uD835\uDFF3'; '8' -> '\uD835\uDFF4'
                    '9' -> '\uD835\uDFF5'
                    else -> c
                }
            }.joinToString("")
        })

        // Comic (bold script)
        fonts.add(FontStyle("comic", "𝓒𝓸𝓶𝓲𝓬") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDCF0'; 'B' -> '\uD835\uDCF1'; 'C' -> '\uD835\uDCF2'
                    'D' -> '\uD835\uDCF3'; 'E' -> '\uD835\uDCF4'; 'F' -> '\uD835\uDCF5'
                    'G' -> '\uD835\uDCF6'; 'H' -> '\uD835\uDCF7'; 'I' -> '\uD835\uDCF8'
                    'J' -> '\uD835\uDCF9'; 'K' -> '\uD835\uDCFA'; 'L' -> '\uD835\uDCFB'
                    'M' -> '\uD835\uDCFC'; 'N' -> '\uD835\uDCFD'; 'O' -> '\uD835\uDCFE'
                    'P' -> '\uD835\uDCFF'; 'Q' -> '\uD835\uDD00'; 'R' -> '\uD835\uDD01'
                    'S' -> '\uD835\uDD02'; 'T' -> '\uD835\uDD03'; 'U' -> '\uD835\uDD04'
                    'V' -> '\uD835\uDD05'; 'W' -> '\uD835\uDD06'; 'X' -> '\uD835\uDD07'
                    'Y' -> '\uD835\uDD08'; 'Z' -> '\uD835\uDD09'
                    'a' -> '\uD835\uDD0A'; 'b' -> '\uD835\uDD0B'; 'c' -> '\uD835\uDD0C'
                    'd' -> '\uD835\uDD0D'; 'e' -> '\uD835\uDD0E'; 'f' -> '\uD835\uDD0F'
                    'g' -> '\uD835\uDD10'; 'h' -> '\uD835\uDD11'; 'i' -> '\uD835\uDD12'
                    'j' -> '\uD835\uDD13'; 'k' -> '\uD835\uDD14'; 'l' -> '\uD835\uDD15'
                    'm' -> '\uD835\uDD16'; 'n' -> '\uD835\uDD17'; 'o' -> '\uD835\uDD18'
                    'p' -> '\uD835\uDD19'; 'q' -> '\uD835\uDD1A'; 'r' -> '\uD835\uDD1B'
                    's' -> '\uD835\uDD1C'; 't' -> '\uD835\uDD1D'; 'u' -> '\uD835\uDD1E'
                    'v' -> '\uD835\uDD1F'; 'w' -> '\uD835\uDD20'; 'x' -> '\uD835\uDD21'
                    'y' -> '\uD835\uDD22'; 'z' -> '\uD835\uDD23'
                    else -> c
                }
            }.joinToString("")
        })

        // Script (cursive)
        fonts.add(FontStyle("script", "𝒮𝒸𝓇𝒾𝓅𝓉") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDC9C'; 'B' -> '\uD835\uDCB5'; 'C' -> '\uD835\uDC9E'
                    'D' -> '\uD835\uDC9F'; 'E' -> '\uD835\uDCB7'; 'F' -> '\uD835\uDCB8'
                    'G' -> '\uD835\uDCA2'; 'H' -> '\uD835\uDCB9'; 'I' -> '\uD835\uDCA4'
                    'J' -> '\uD835\uDCA5'; 'K' -> '\uD835\uDCA6'; 'L' -> '\uD835\uDCBA'
                    'M' -> '\uD835\uDCBB'; 'N' -> '\uD835\uDCA9'; 'O' -> '\uD835\uDCAA'
                    'P' -> '\uD835\uDCAB'; 'Q' -> '\uD835\uDCAC'; 'R' -> '\uD835\uDCAD'
                    'S' -> '\uD835\uDCAE'; 'T' -> '\uD835\uDCAF'; 'U' -> '\uD835\uDCB0'
                    'V' -> '\uD835\uDCB1'; 'W' -> '\uD835\uDCB2'; 'X' -> '\uD835\uDCB3'
                    'Y' -> '\uD835\uDCB4'; 'Z' -> '\uD835\uDCB5'
                    'a' -> '\uD835\uDCB6'; 'b' -> '\uD835\uDCB7'; 'c' -> '\uD835\uDCB8'
                    'd' -> '\uD835\uDCB9'; 'e' -> '\uD835\uDCF0'; 'f' -> '\uD835\uDCBB'
                    'g' -> '\uD835\uDCF2'; 'h' -> '\uD835\uDCBD'; 'i' -> '\uD835\uDCBE'
                    'j' -> '\uD835\uDCBF'; 'k' -> '\uD835\uDCC0'; 'l' -> '\uD835\uDCC1'
                    'm' -> '\uD835\uDCC2'; 'n' -> '\uD835\uDCC3'; 'o' -> '\uD835\uDCC4'
                    'p' -> '\uD835\uDCC5'; 'q' -> '\uD835\uDCC6'; 'r' -> '\uD835\uDCC7'
                    's' -> '\uD835\uDCC8'; 't' -> '\uD835\uDCC9'; 'u' -> '\uD835\uDCCA'
                    'v' -> '\uD835\uDCCB'; 'w' -> '\uD835\uDCCC'; 'x' -> '\uD835\uDCCD'
                    'y' -> '\uD835\uDCCE'; 'z' -> '\uD835\uDCCF'
                    else -> c
                }
            }.joinToString("")
        })

        // Serif Bold
        fonts.add(FontStyle("serif_bold", "𝐒𝐞𝐫𝐢𝐟 𝐁𝐨𝐥𝐝") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDC00'; 'B' -> '\uD835\uDC01'; 'C' -> '\uD835\uDC02'
                    'D' -> '\uD835\uDC03'; 'E' -> '\uD835\uDC04'; 'F' -> '\uD835\uDC05'
                    'G' -> '\uD835\uDC06'; 'H' -> '\uD835\uDC07'; 'I' -> '\uD835\uDC08'
                    'J' -> '\uD835\uDC09'; 'K' -> '\uD835\uDC0A'; 'L' -> '\uD835\uDC0B'
                    'M' -> '\uD835\uDC0C'; 'N' -> '\uD835\uDC0D'; 'O' -> '\uD835\uDC0E'
                    'P' -> '\uD835\uDC0F'; 'Q' -> '\uD835\uDC10'; 'R' -> '\uD835\uDC11'
                    'S' -> '\uD835\uDC12'; 'T' -> '\uD835\uDC13'; 'U' -> '\uD835\uDC14'
                    'V' -> '\uD835\uDC15'; 'W' -> '\uD835\uDC16'; 'X' -> '\uD835\uDC17'
                    'Y' -> '\uD835\uDC18'; 'Z' -> '\uD835\uDC19'
                    'a' -> '\uD835\uDC1A'; 'b' -> '\uD835\uDC1B'; 'c' -> '\uD835\uDC1C'
                    'd' -> '\uD835\uDC1D'; 'e' -> '\uD835\uDC1E'; 'f' -> '\uD835\uDC1F'
                    'g' -> '\uD835\uDC20'; 'h' -> '\uD835\uDC21'; 'i' -> '\uD835\uDC22'
                    'j' -> '\uD835\uDC23'; 'k' -> '\uD835\uDC24'; 'l' -> '\uD835\uDC25'
                    'm' -> '\uD835\uDC26'; 'n' -> '\uD835\uDC27'; 'o' -> '\uD835\uDC28'
                    'p' -> '\uD835\uDC29'; 'q' -> '\uD835\uDC2A'; 'r' -> '\uD835\uDC2B'
                    's' -> '\uD835\uDC2C'; 't' -> '\uD835\uDC2D'; 'u' -> '\uD835\uDC2E'
                    'v' -> '\uD835\uDC2F'; 'w' -> '\uD835\uDC30'; 'x' -> '\uD835\uDC31'
                    'y' -> '\uD835\uDC32'; 'z' -> '\uD835\uDC33'
                    '0' -> '\uD835\uDFCE'; '1' -> '\uD835\uDFCF'; '2' -> '\uD835\uDFD0'
                    '3' -> '\uD835\uDFD1'; '4' -> '\uD835\uDFD2'; '5' -> '\uD835\uDFD3'
                    '6' -> '\uD835\uDFD4'; '7' -> '\uD835\uDFD5'; '8' -> '\uD835\uDFD6'
                    '9' -> '\uD835\uDFD7'
                    else -> c
                }
            }.joinToString("")
        })

        // Sans Bold
        fonts.add(FontStyle("sans_bold", "𝗦𝗮𝗻𝘀 𝗕𝗼𝗹𝗱") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDDB4'; 'B' -> '\uD835\uDDB5'; 'C' -> '\uD835\uDDB6'
                    'D' -> '\uD835\uDDB7'; 'E' -> '\uD835\uDDB8'; 'F' -> '\uD835\uDDB9'
                    'G' -> '\uD835\uDDBA'; 'H' -> '\uD835\uDDBB'; 'I' -> '\uD835\uDDBC'
                    'J' -> '\uD835\uDDBD'; 'K' -> '\uD835\uDDBE'; 'L' -> '\uD835\uDDBF'
                    'M' -> '\uD835\uDDC0'; 'N' -> '\uD835\uDDC1'; 'O' -> '\uD835\uDDC2'
                    'P' -> '\uD835\uDDC3'; 'Q' -> '\uD835\uDDC4'; 'R' -> '\uD835\uDDC5'
                    'S' -> '\uD835\uDDC6'; 'T' -> '\uD835\uDDC7'; 'U' -> '\uD835\uDDC8'
                    'V' -> '\uD835\uDDC9'; 'W' -> '\uD835\uDDCA'; 'X' -> '\uD835\uDDCB'
                    'Y' -> '\uD835\uDDCC'; 'Z' -> '\uD835\uDDCD'
                    'a' -> '\uD835\uDDCE'; 'b' -> '\uD835\uDDCF'; 'c' -> '\uD835\uDDD0'
                    'd' -> '\uD835\uDDD1'; 'e' -> '\uD835\uDDD2'; 'f' -> '\uD835\uDDD3'
                    'g' -> '\uD835\uDDD4'; 'h' -> '\uD835\uDDD5'; 'i' -> '\uD835\uDDD6'
                    'j' -> '\uD835\uDDD7'; 'k' -> '\uD835\uDDD8'; 'l' -> '\uD835\uDDD9'
                    'm' -> '\uD835\uDDDA'; 'n' -> '\uD835\uDDDB'; 'o' -> '\uD835\uDDDC'
                    'p' -> '\uD835\uDDDD'; 'q' -> '\uD835\uDDDE'; 'r' -> '\uD835\uDDDF'
                    's' -> '\uD835\uDDE0'; 't' -> '\uD835\uDDE1'; 'u' -> '\uD835\uDDE2'
                    'v' -> '\uD835\uDDE3'; 'w' -> '\uD835\uDDE4'; 'x' -> '\uD835\uDDE5'
                    'y' -> '\uD835\uDDE6'; 'z' -> '\uD835\uDDE7'
                    '0' -> '\uD835\uDFF6'; '1' -> '\uD835\uDFF7'; '2' -> '\uD835\uDFF8'
                    '3' -> '\uD835\uDFF9'; '4' -> '\uD835\uDFFA'; '5' -> '\uD835\uDFFB'
                    '6' -> '\uD835\uDFFC'; '7' -> '\uD835\uDFFD'; '8' -> '\uD835\uDFFE'
                    '9' -> '\uD835\uDFFF'
                    else -> c
                }
            }.joinToString("")
        })

        // Circles Black (black circles with white letters)
        fonts.add(FontStyle("circles_black", "⬤ Black") { text ->
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
        })

        // Circles White (white circles with black letters)
        fonts.add(FontStyle("circles_white", "○ White") { text ->
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
        })

        // Squares Black
        fonts.add(FontStyle("squares_black", "■ Black") { text ->
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
        })

        // Squares White
        fonts.add(FontStyle("squares_white", "□ White") { text ->
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
        })

        // Reverse (upside down)
        fonts.add(FontStyle("reverse", "ɹǝʌǝɹsǝ") { text ->
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
                '\"' to '\u201E', '\'' to ',', '&' to '\u214B'
            )
            text.reversed().map { map[it] ?: it }.joinToString("")
        })

        // Double Struck (outline)
        fonts.add(FontStyle("double_struck", "𝔻𝕠𝕦𝕓𝕝𝕖") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDD38'; 'B' -> '\uD835\uDD39'; 'C' -> '\u2102'
                    'D' -> '\uD835\uDD3B'; 'E' -> '\uD835\uDD3C'; 'F' -> '\uD835\uDD3D'
                    'G' -> '\uD835\uDD3E'; 'H' -> '\u210D'; 'I' -> '\uD835\uDD40'
                    'J' -> '\uD835\uDD41'; 'K' -> '\uD835\uDD42'; 'L' -> '\uD835\uDD43'
                    'M' -> '\uD835\uDD44'; 'N' -> '\u2115'; 'O' -> '\uD835\uDD46'
                    'P' -> '\u2119'; 'Q' -> '\u211A'; 'R' -> '\u211D'
                    'S' -> '\uD835\uDD4A'; 'T' -> '\uD835\uDD4B'; 'U' -> '\uD835\uDD4C'
                    'V' -> '\uD835\uDD4D'; 'W' -> '\uD835\uDD4E'; 'X' -> '\uD835\uDD4F'
                    'Y' -> '\uD835\uDD50'; 'Z' -> '\u2124'
                    'a' -> '\uD835\uDD52'; 'b' -> '\uD835\uDD53'; 'c' -> '\uD835\uDD54'
                    'd' -> '\uD835\uDD55'; 'e' -> '\uD835\uDD56'; 'f' -> '\uD835\uDD57'
                    'g' -> '\uD835\uDD58'; 'h' -> '\uD835\uDD59'; 'i' -> '\uD835\uDD5A'
                    'j' -> '\uD835\uDD5B'; 'k' -> '\uD835\uDD5C'; 'l' -> '\uD835\uDD5D'
                    'm' -> '\uD835\uDD5E'; 'n' -> '\uD835\uDD5F'; 'o' -> '\uD835\uDD60'
                    'p' -> '\uD835\uDD61'; 'q' -> '\uD835\uDD62'; 'r' -> '\uD835\uDD63'
                    's' -> '\uD835\uDD64'; 't' -> '\uD835\uDD65'; 'u' -> '\uD835\uDD66'
                    'v' -> '\uD835\uDD67'; 'w' -> '\uD835\uDD68'; 'x' -> '\uD835\uDD69'
                    'y' -> '\uD835\uDD6A'; 'z' -> '\uD835\uDD6B'
                    '0' -> '\uD835\uDFD8'; '1' -> '\uD835\uDFD9'; '2' -> '\uD835\uDFDA'
                    '3' -> '\uD835\uDFDB'; '4' -> '\uD835\uDFDC'; '5' -> '\uD835\uDFDD'
                    '6' -> '\uD835\uDFDE'; '7' -> '\uD835\uDFDF'; '8' -> '\uD835\uDFE0'
                    '9' -> '\uD835\uDFE1'
                    else -> c
                }
            }.joinToString("")
        })

        // Small Caps
        fonts.add(FontStyle("small_caps", "Sᴍᴀʟʟ Cᴀᴘs") { text ->
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
        })

        // Bubble
        fonts.add(FontStyle("bubble", "ⓑⓤⓑⓑⓛⓔ") { text ->
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
        })

        // Parenthesized
        fonts.add(FontStyle("parenthesized", "⒫⒜⒭⒠⒩") { text ->
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
        })

        // Fullwidth
        fonts.add(FontStyle("fullwidth", "ｆｕｌｌｗｉｄｔｈ") { text ->
            text.map { c ->
                when {
                    c in '!'..'~' -> (c.code + 0xFEE0).toChar()
                    c == ' ' -> '\u3000'
                    else -> c
                }
            }.joinToString("")
        })

        // Monospace unicode
        fonts.add(FontStyle("monospace_unicode", "𝚖𝚘𝚗𝚘𝚜𝚙𝚊𝚌𝚎") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDEA8'; 'B' -> '\uD835\uDEA9'; 'C' -> '\uD835\uDEAA'
                    'D' -> '\uD835\uDEAB'; 'E' -> '\uD835\uDEAC'; 'F' -> '\uD835\uDEAD'
                    'G' -> '\uD835\uDEAE'; 'H' -> '\uD835\uDEAF'; 'I' -> '\uD835\uDEB0'
                    'J' -> '\uD835\uDEB1'; 'K' -> '\uD835\uDEB2'; 'L' -> '\uD835\uDEB3'
                    'M' -> '\uD835\uDEB4'; 'N' -> '\uD835\uDEB5'; 'O' -> '\uD835\uDEB6'
                    'P' -> '\uD835\uDEB7'; 'Q' -> '\uD835\uDEB8'; 'R' -> '\uD835\uDEB9'
                    'S' -> '\uD835\uDEBA'; 'T' -> '\uD835\uDEBB'; 'U' -> '\uD835\uDEBC'
                    'V' -> '\uD835\uDEBD'; 'W' -> '\uD835\uDEBE'; 'X' -> '\uD835\uDEBF'
                    'Y' -> '\uD835\uDEC0'; 'Z' -> '\uD835\uDEC1'
                    'a' -> '\uD835\uDEC2'; 'b' -> '\uD835\uDEC3'; 'c' -> '\uD835\uDEC4'
                    'd' -> '\uD835\uDEC5'; 'e' -> '\uD835\uDEC6'; 'f' -> '\uD835\uDEC7'
                    'g' -> '\uD835\uDEC8'; 'h' -> '\uD835\uDEC9'; 'i' -> '\uD835\uDECA'
                    'j' -> '\uD835\uDECB'; 'k' -> '\uD835\uDECC'; 'l' -> '\uD835\uDECD'
                    'm' -> '\uD835\uDECE'; 'n' -> '\uD835\uDECF'; 'o' -> '\uD835\uDED0'
                    'p' -> '\uD835\uDED1'; 'q' -> '\uD835\uDED2'; 'r' -> '\uD835\uDED3'
                    's' -> '\uD835\uDED4'; 't' -> '\uD835\uDED5'; 'u' -> '\uD835\uDED6'
                    'v' -> '\uD835\uDED7'; 'w' -> '\uD835\uDED8'; 'x' -> '\uD835\uDED9'
                    'y' -> '\uD835\uDEDA'; 'z' -> '\uD835\uDEDB'
                    '0' -> '\uD835\uDFEC'; '1' -> '\uD835\uDFED'; '2' -> '\uD835\uDFEE'
                    '3' -> '\uD835\uDFEF'; '4' -> '\uD835\uDFF0'; '5' -> '\uD835\uDFF1'
                    '6' -> '\uD835\uDFF2'; '7' -> '\uD835\uDFF3'; '8' -> '\uD835\uDFF4'
                    '9' -> '\uD835\uDFF5'
                    else -> c
                }
            }.joinToString("")
        })

        // Tiny Letters (superscript)
        fonts.add(FontStyle("tiny_letters", "ᵗⁱⁿʸ") { text ->
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
        })

        // Strikethrough
        fonts.add(FontStyle("strikethrough", "S̶t̶r̶i̶k̶e̶") { text ->
            text.map { "$it\u0336" }.joinToString("")
        })

        // Underline
        fonts.add(FontStyle("underline", "U̲n̲d̲e̲r̲l̲i̲n̲e̲") { text ->
            text.map { "$it\u0332" }.joinToString("")
        })

        // Double Underline
        fonts.add(FontStyle("double_underline", "D̳o̳u̳b̳l̳e̳") { text ->
            text.map { "$it\u0333" }.joinToString("")
        })

        // Wavy underline
        fonts.add(FontStyle("wavy", "W̰a̰v̰y̰") { text ->
            text.map { "$it\u0330" }.joinToString("")
        })

        // Dotted
        fonts.add(FontStyle("dotted", "Ḋȯṫṫėḋ") { text ->
            text.map { "$it\u0307" }.joinToString("")
        })

        // Slash through
        fonts.add(FontStyle("slash", "S̷l̷a̷s̷h̷") { text ->
            text.map { "$it\u0337" }.joinToString("")
        })

        // Cross above
        fonts.add(FontStyle("cross", "C̶r̶o̶s̶s̶") { text ->
            text.map { "$it\u0336" }.joinToString("")
        })

        // Arrow above
        fonts.add(FontStyle("arrow", "A⃗r⃗r⃗o⃗w⃗") { text ->
            text.map { "$it\u20D7" }.joinToString("")
        })

        // Heart above
        fonts.add(FontStyle("heart", "H̩e̩a̩r̩t̩") { text ->
            text.map { "$it\u0329" }.joinToString("")
        })

        // Star above
        fonts.add(FontStyle("star", "S͙t͙a͙r͙") { text ->
            text.map { "$it\u0359" }.joinToString("")
        })

        // Bridge above
        fonts.add(FontStyle("bridge", "B͆r͆i͆d͆g͆e͆") { text ->
            text.map { "$it\u0346" }.joinToString("")
        })

        // Smile
        fonts.add(FontStyle("smile", "S̑m̑ȋl̑ȇ") { text ->
            text.map { "$it\u0311" }.joinToString("")
        })

        // Thunder
        fonts.add(FontStyle("thunder", "T̽h̽u̽n̽d̽e̽r̽") { text ->
            text.map { "$it\u033D" }.joinToString("")
        })

        // Railway
        fonts.add(FontStyle("railway", "R̤a̤i̤l̤w̤a̤y̤") { text ->
            text.map { "$it\u0324" }.joinToString("")
        })

        // Wheels
        fonts.add(FontStyle("wheels", "W̥h̥e̥e̥l̥s̥") { text ->
            text.map { "$it\u0325" }.joinToString("")
        })

        // Track
        fonts.add(FontStyle("track", "T̪r̪a̪c̪k̪") { text ->
            text.map { "$it\u032A" }.joinToString("")
        })

        // Up Above
        fonts.add(FontStyle("up_above", "U̺p̺") { text ->
            text.map { "$it\u033A" }.joinToString("")
        })

        // Sandwich
        fonts.add(FontStyle("sandwich", "S̾a̾n̾d̾w̾i̾c̾h̾") { text ->
            text.map { "$it\u033E" }.joinToString("")
        })

        // Fair
        fonts.add(FontStyle("fair", "F̒a̒i̒r̒") { text ->
            text.map { "$it\u0312" }.joinToString("")
        })

        // Bar
        fonts.add(FontStyle("bar", "B̅a̅r̅") { text ->
            text.map { "$it\u0305" }.joinToString("")
        })

        // Waves
        fonts.add(FontStyle("waves", "W̴a̴v̴e̴s̴") { text ->
            text.map { "$it\u0334" }.joinToString("")
        })

        // Sandra Peel
        fonts.add(FontStyle("sandra_peel", "S̻a̻n̻d̻r̻a̻") { text ->
            text.map { "$it\u033B" }.joinToString("")
        })

        // Heater
        fonts.add(FontStyle("heater", "H̹e̹a̹t̹e̹r̹") { text ->
            text.map { "$it\u0339" }.joinToString("")
        })

        // Seawave
        fonts.add(FontStyle("seawave", "S̳e̳a̳w̳a̳v̳e̳") { text ->
            text.map { "$it\u0333" }.joinToString("")
        })

        // Wall
        fonts.add(FontStyle("wall", "W̼a̼l̼l̼") { text ->
            text.map { "$it\u033C" }.joinToString("")
        })

        // Joiner
        fonts.add(FontStyle("joiner", "J̡o̡i̡n̡e̡r̡") { text ->
            text.map { "$it\u0321" }.joinToString("")
        })

        // Wrapped
        fonts.add(FontStyle("wrapped", "W̧ŗa̧p̧p̧ȩḑ") { text ->
            text.map { "$it\u0327" }.joinToString("")
        })

        // Box Packed
        fonts.add(FontStyle("box_packed", "B̲o̲x̲") { text ->
            text.map { "$it\u0332" }.joinToString("")
        })

        // Outer Pack
        fonts.add(FontStyle("outer_pack", "O̶u̶t̶e̶r̶") { text ->
            text.map { "$it\u0336" }.joinToString("")
        })

        // Dot Packed
        fonts.add(FontStyle("dot_packed", "D̤o̤t̤") { text ->
            text.map { "$it\u0324" }.joinToString("")
        })

        // Corner
        fonts.add(FontStyle("corner", "C̘o̘r̘n̘e̘r̘") { text ->
            text.map { "$it\u0318" }.joinToString("")
        })

        // Joints
        fonts.add(FontStyle("joints", "J̺o̺i̺n̺t̺s̺") { text ->
            text.map { "$it\u033A" }.joinToString("")
        })

        // Directions
        fonts.add(FontStyle("directions", "D̻i̻r̻e̻c̻t̻i̻o̻n̻s̻") { text ->
            text.map { "$it\u033B" }.joinToString("")
        })

        // Star Join
        fonts.add(FontStyle("star_join", "S̼t̼a̼r̼") { text ->
            text.map { "$it\u033C" }.joinToString("")
        })

        // Italic Bold
        fonts.add(FontStyle("italic_bold", "𝑰𝒕𝒂𝒍𝒊𝒄") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDC68'; 'B' -> '\uD835\uDC69'; 'C' -> '\uD835\uDC6A'
                    'D' -> '\uD835\uDC6B'; 'E' -> '\uD835\uDC6C'; 'F' -> '\uD835\uDC6D'
                    'G' -> '\uD835\uDC6E'; 'H' -> '\uD835\uDC6F'; 'I' -> '\uD835\uDC70'
                    'J' -> '\uD835\uDC71'; 'K' -> '\uD835\uDC72'; 'L' -> '\uD835\uDC73'
                    'M' -> '\uD835\uDC74'; 'N' -> '\uD835\uDC75'; 'O' -> '\uD835\uDC76'
                    'P' -> '\uD835\uDC77'; 'Q' -> '\uD835\uDC78'; 'R' -> '\uD835\uDC79'
                    'S' -> '\uD835\uDC7A'; 'T' -> '\uD835\uDC7B'; 'U' -> '\uD835\uDC7C'
                    'V' -> '\uD835\uDC7D'; 'W' -> '\uD835\uDC7E'; 'X' -> '\uD835\uDC7F'
                    'Y' -> '\uD835\uDC80'; 'Z' -> '\uD835\uDC81'
                    'a' -> '\uD835\uDC82'; 'b' -> '\uD835\uDC83'; 'c' -> '\uD835\uDC84'
                    'd' -> '\uD835\uDC85'; 'e' -> '\uD835\uDC86'; 'f' -> '\uD835\uDC87'
                    'g' -> '\uD835\uDC88'; 'h' -> '\uD835\uDC89'; 'i' -> '\uD835\uDC8A'
                    'j' -> '\uD835\uDC8B'; 'k' -> '\uD835\uDC8C'; 'l' -> '\uD835\uDC8D'
                    'm' -> '\uD835\uDC8E'; 'n' -> '\uD835\uDC8F'; 'o' -> '\uD835\uDC90'
                    'p' -> '\uD835\uDC91'; 'q' -> '\uD835\uDC92'; 'r' -> '\uD835\uDC93'
                    's' -> '\uD835\uDC94'; 't' -> '\uD835\uDC95'; 'u' -> '\uD835\uDC96'
                    'v' -> '\uD835\uDC97'; 'w' -> '\uD835\uDC98'; 'x' -> '\uD835\uDC99'
                    'y' -> '\uD835\uDC9A'; 'z' -> '\uD835\uDC9B'
                    else -> c
                }
            }.joinToString("")
        })

        // Italic Sans
        fonts.add(FontStyle("italic_sans", "𝘪𝘵𝘢𝘭𝘪𝘤 𝘴𝘢𝘯𝘴") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDE08'; 'B' -> '\uD835\uDE09'; 'C' -> '\uD835\uDE0A'
                    'D' -> '\uD835\uDE0B'; 'E' -> '\uD835\uDE0C'; 'F' -> '\uD835\uDE0D'
                    'G' -> '\uD835\uDE0E'; 'H' -> '\uD835\uDE0F'; 'I' -> '\uD835\uDE10'
                    'J' -> '\uD835\uDE11'; 'K' -> '\uD835\uDE12'; 'L' -> '\uD835\uDE13'
                    'M' -> '\uD835\uDE14'; 'N' -> '\uD835\uDE15'; 'O' -> '\uD835\uDE16'
                    'P' -> '\uD835\uDE17'; 'Q' -> '\uD835\uDE18'; 'R' -> '\uD835\uDE19'
                    'S' -> '\uD835\uDE1A'; 'T' -> '\uD835\uDE1B'; 'U' -> '\uD835\uDE1C'
                    'V' -> '\uD835\uDE1D'; 'W' -> '\uD835\uDE1E'; 'X' -> '\uD835\uDE1F'
                    'Y' -> '\uD835\uDE20'; 'Z' -> '\uD835\uDE21'
                    'a' -> '\uD835\uDE22'; 'b' -> '\uD835\uDE23'; 'c' -> '\uD835\uDE24'
                    'd' -> '\uD835\uDE25'; 'e' -> '\uD835\uDE26'; 'f' -> '\uD835\uDE27'
                    'g' -> '\uD835\uDE28'; 'h' -> '\uD835\uDE29'; 'i' -> '\uD835\uDE2A'
                    'j' -> '\uD835\uDE2B'; 'k' -> '\uD835\uDE2C'; 'l' -> '\uD835\uDE2D'
                    'm' -> '\uD835\uDE2E'; 'n' -> '\uD835\uDE2F'; 'o' -> '\uD835\uDE30'
                    'p' -> '\uD835\uDE31'; 'q' -> '\uD835\uDE32'; 'r' -> '\uD835\uDE33'
                    's' -> '\uD835\uDE34'; 't' -> '\uD835\uDE35'; 'u' -> '\uD835\uDE36'
                    'v' -> '\uD835\uDE37'; 'w' -> '\uD835\uDE38'; 'x' -> '\uD835\uDE39'
                    'y' -> '\uD835\uDE3A'; 'z' -> '\uD835\uDE3B'
                    else -> c
                }
            }.joinToString("")
        })

        // Bold Fraktur
        fonts.add(FontStyle("bold_fraktur", "𝕱𝖗𝖆𝖐𝖙𝖚𝖗") { text ->
            text.map { c ->
                when (c) {
                    'A' -> '\uD835\uDD6C'; 'B' -> '\uD835\uDD6D'; 'C' -> '\uD835\uDD6E'
                    'D' -> '\uD835\uDD6F'; 'E' -> '\uD835\uDD70'; 'F' -> '\uD835\uDD71'
                    'G' -> '\uD835\uDD72'; 'H' -> '\uD835\uDD73'; 'I' -> '\uD835\uDD74'
                    'J' -> '\uD835\uDD75'; 'K' -> '\uD835\uDD76'; 'L' -> '\uD835\uDD77'
                    'M' -> '\uD835\uDD78'; 'N' -> '\uD835\uDD79'; 'O' -> '\uD835\uDD7A'
                    'P' -> '\uD835\uDD7B'; 'Q' -> '\uD835\uDD7C'; 'R' -> '\uD835\uDD7D'
                    'S' -> '\uD835\uDD7E'; 'T' -> '\uD835\uDD7F'; 'U' -> '\uD835\uDD80'
                    'V' -> '\uD835\uDD81'; 'W' -> '\uD835\uDD82'; 'X' -> '\uD835\uDD83'
                    'Y' -> '\uD835\uDD84'; 'Z' -> '\uD835\uDD85'
                    'a' -> '\uD835\uDD86'; 'b' -> '\uD835\uDD87'; 'c' -> '\uD835\uDD88'
                    'd' -> '\uD835\uDD89'; 'e' -> '\uD835\uDD8A'; 'f' -> '\uD835\uDD8B'
                    'g' -> '\uD835\uDD8C'; 'h' -> '\uD835\uDD8D'; 'i' -> '\uD835\uDD8E'
                    'j' -> '\uD835\uDD8F'; 'k' -> '\uD835\uDD90'; 'l' -> '\uD835\uDD91'
                    'm' -> '\uD835\uDD92'; 'n' -> '\uD835\uDD93'; 'o' -> '\uD835\uDD94'
                    'p' -> '\uD835\uDD95'; 'q' -> '\uD835\uDD96'; 'r' -> '\uD835\uDD97'
                    's' -> '\uD835\uDD98'; 't' -> '\uD835\uDD99'; 'u' -> '\uD835\uDD9A'
                    'v' -> '\uD835\uDD9B'; 'w' -> '\uD835\uDD9C'; 'x' -> '\uD835\uDD9D'
                    'y' -> '\uD835\uDD9E'; 'z' -> '\uD835\uDD9F'
                    else -> c
                }
            }.joinToString("")
        })

        // Currency
        fonts.add(FontStyle("currency", "₵ɄⱤⱤɆ₦₵Ɏ") { text ->
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
        })

        // Warn
        fonts.add(FontStyle("warn", "W̸a̸r̸n̸") { text ->
            text.map { "$it\u0338" }.joinToString("")
        })

        // Birds
        fonts.add(FontStyle("birds", "B̬i̬r̬d̬s̬") { text ->
            text.map { "$it\u032C" }.joinToString("")
        })

        // Rays
        fonts.add(FontStyle("rays", "R̦a̦y̦ș") { text ->
            text.map { "$it\u0326" }.joinToString("")
        })

        // Magna
        fonts.add(FontStyle("magna", "M̩a̩g̩n̩a̩") { text ->
            text.map { "$it\u0329" }.joinToString("")
        })

        // Fancy (combined effects)
        fonts.add(FontStyle("fancy", "F⃠a⃠n⃠c⃠y⃠") { text ->
            text.map { "$it\u20E0" }.joinToString("")
        })

        // Thin (light)
        fonts.add(FontStyle("thin", "T⃩h⃩i⃩n⃩") { text ->
            text.map { "$it\u20E9" }.joinToString("")
        })

        // Gloom
        fonts.add(FontStyle("gloom", "G̷l̷o̷o̷m̷") { text ->
            text.map { "$it\u0337" }.joinToString("")
        })

        // Paranormal
        fonts.add(FontStyle("paranormal", "P̶a̶r̶a̶n̶o̶r̶m̶a̶l̶") { text ->
            text.map { "$it\u0336" }.joinToString("")
        })

        // Coffee
        fonts.add(FontStyle("coffee", "C̷o̷f̷f̷e̷e̷") { text ->
            text.map { "$it\u0337" }.joinToString("")
        })

        DebugLogger.i("FontManager initialized with ${fonts.size} fonts")
    }
}
