package ru.lorderi.androktor.model

sealed interface Status {
    data object Idle : Status
    data object Loading : Status
    data class Error(val reason: Throwable) : Status
}
