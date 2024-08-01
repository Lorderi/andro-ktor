package ru.lorderi.androktor.util

const val PHONE_REGEX = """(\+?\d{11})"""

fun String.handlePhone(): String {
    if (!Regex(PHONE_REGEX).matches(this)) {
        return this
    }

    return when {
        length == 11 && (startsWith("7800") || startsWith("8800")) ->
            "8 (${substring(1, 4)}) ${substring(4, 7)} ${substring(7, 9)} ${substring(9, 11)}"

        length == 11 && (startsWith('7') || startsWith('8')) ->
            "+7 ${substring(1, 4)} ${substring(4, 7)}-${substring(7, 9)}-${substring(9, 11)}"

        length == 12 && startsWith("+7") ->
            "+7 ${substring(2, 5)} ${substring(5, 8)}-${substring(8, 10)}-${substring(10, 12)}"

        else -> this
    }
}
