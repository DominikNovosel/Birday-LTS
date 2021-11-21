package com.minar.birday.utilities

import java.util.*

// Extension function to quickly capitalize a name, also considering other uppercase letter, multiple words and the apostrophe
@ExperimentalStdlibApi
fun String.smartCapitalize(): String {
    return trim().split(" ")
        .joinToString(" ") { it.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT) }
        .split("'").joinToString("'") { it.capitalize(Locale.ROOT) }
        .split("-").joinToString("-") { it.capitalize(Locale.ROOT) }
}

// Simply checks if the string is written using only letters, emoticons and at most one apostrophe and one hyphen
fun checkString(submission: String): Boolean {
    var apostropheFound = false
    var hyphenFound = false
    if (submission == "\'") return false
    if (submission.startsWith('-')) return false
    if (submission.contains("-\'")) return false
    loop@ for (s in submission.replace("\\s".toRegex(), "")) {
        when {
            s.isSurrogate() -> continue@loop
            s.isLetter() -> continue@loop
            s == '-' && !hyphenFound -> hyphenFound = true
            s == '\'' && !apostropheFound -> apostropheFound = true
            else -> return false
        }
    }
    return true
}