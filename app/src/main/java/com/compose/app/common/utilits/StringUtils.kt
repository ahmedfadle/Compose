package com.compose.app.common.utilits


/**
 * Converts a 2-letter ISO 3166-1 alpha-2 country code (e.g., "US", "EG", "FR") into its corresponding flag emoji.
 *
 * This works by mapping each letter to the Unicode regional indicator symbol,
 * which together represent the country's flag when displayed.
 *
 * Example:
 * ```kotlin
 * "US".countryCodeToEmoji() // 🇺🇸
 * "EG".countryCodeToEmoji() // 🇪🇬
 * ```
 *
 * @receiver A 2-character uppercase country code string (e.g., "US").
 * @return A string containing the corresponding country flag emoji.
 * @throws StringIndexOutOfBoundsException if the string is shorter than 2 characters.
 */
fun String.countryCodeToEmoji(): String {
    val firstChar = Character.codePointAt(this, 0) - 0x41 + 0x1F1E6
    val secondChar = Character.codePointAt(this, 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
}