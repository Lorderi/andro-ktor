package ru.lorderi.androktor.ui.viewmodel.job

import ru.lorderi.androktor.model.AdvancedClientCompany
import ru.lorderi.androktor.model.ClientCompanies
import ru.lorderi.androktor.model.ClientVacancies
import ru.lorderi.androktor.model.ClientVacancy
import ru.lorderi.androktor.model.Status

data class JobUiState(
    val companies: ClientCompanies? = null,
    val vacancies: ClientVacancies? = null,
    val detailedCompany: AdvancedClientCompany? = null,
    val detailedVacancy: ClientVacancy? = null,
    val status: Status = Status.Idle,
) {
    fun isRefreshing(any: Any?): Boolean = status == Status.Loading && any != null
    fun isEmptyLoading(any: Any?): Boolean = status == Status.Loading && any == null
    fun emptyError(any: Any?): Throwable? =
        (status as? Status.Error)?.reason?.takeIf { any == null }

    fun refreshingError(any: Any?): Throwable? =
        (status as? Status.Error)?.reason?.takeIf { any != null }
}
