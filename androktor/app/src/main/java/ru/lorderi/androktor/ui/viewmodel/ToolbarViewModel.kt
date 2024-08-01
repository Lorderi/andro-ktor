package ru.lorderi.androktor.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ToolbarViewModel : ViewModel() {
    private val _cancellation = MutableStateFlow(false)
    val cancellation = _cancellation.asStateFlow()

    fun changeCancellation() {
        _cancellation.value = !_cancellation.value
    }

    fun setNullCancellation() {
        _cancellation.value = false
    }
}
