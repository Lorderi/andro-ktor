package ru.lorderi.androktor.ui.viewmodel.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lorderi.androktor.model.ClientCompanies
import ru.lorderi.androktor.model.ClientVacancies
import ru.lorderi.androktor.model.Status
import ru.lorderi.androktor.repository.JobRepository
import ru.lorderi.androktor.repository.SQLiteJobRepository
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val networkRepository: JobRepository,
    // TODO!!!!
    private val localRepository: SQLiteJobRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(JobUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadLocalCompanies()
        loadLocalVacancies()
        loadCompanies()
        loadVacancies()
    }

    fun loadLocalCompanies() {
        viewModelScope.launch {
            try {
                val companies = localRepository.getCompanies()
                _uiState.update {
                    it.copy(companies = ClientCompanies(companies))
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun loadLocalVacancies() {
        viewModelScope.launch {
            try {
                val vacancies = localRepository.getVacancies()
                _uiState.update {
                    it.copy(vacancies = ClientVacancies(vacancies))
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun loadCompanies() {
        _uiState.update { it.copy(status = Status.Loading) }
        viewModelScope.launch {
            try {
                val companies = networkRepository.getCompanies()
                _uiState.update {
                    it.copy(companies = companies, status = Status.Idle)
                }
                companies.listOfCompanies.forEach { company ->
                    localRepository.saveCompany(company)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun loadVacancies() {
        _uiState.update { it.copy(status = Status.Loading) }
        viewModelScope.launch {
            try {
                val vacancies = networkRepository.getVacancies()

                _uiState.update {
                    it.copy(vacancies = vacancies, status = Status.Idle)
                }
                vacancies.listOfVacancies.forEach { vacancy ->
                    localRepository.saveVacancy(vacancy)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun loadCompanyById(id: Long) {
        viewModelScope.launch {
            try {
                val companyDetailed = networkRepository.getCompanyById(id)
                _uiState.update {
                    it.copy(detailedCompany = companyDetailed, status = Status.Idle)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun loadVacancyById(id: Long) {
        viewModelScope.launch {
            try {
                val vacancyDetailed = networkRepository.getVacanciesById(id)
                _uiState.update {
                    it.copy(detailedVacancy = vacancyDetailed)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    // Migration case
    fun changeFavorite() {
        val vacancy = _uiState.value.detailedVacancy
        val value = if (vacancy?.isFavourite == null) {
            true
        } else {
            !vacancy.isFavourite
        }
        _uiState.update {
            it.copy(detailedVacancy = vacancy?.copy(isFavourite = value))
        }
    }

    fun consumeError() {
        _uiState.update {
            if (it.status is Status.Error) {
                it.copy(status = Status.Idle)
            } else {
                it
            }
        }
    }
}
