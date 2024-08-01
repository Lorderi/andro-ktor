package ru.lorderi.androktor.ui.viewmodel.myresume

import ru.lorderi.androktor.model.Resume
import ru.lorderi.androktor.model.Status

data class MyResumeUiState(
    val resume: Resume? = null,
    val status: Status = Status.Idle,
)
