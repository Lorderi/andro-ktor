package ru.lorderi.androktor.util

import android.content.Context
import ru.lorderi.androktor.R
import java.io.IOException

fun Throwable.getText(context: Context): String = when (this) {
    is IOException -> context.getString(R.string.network_error)
    else -> context.getString(R.string.unknown_error)
}
