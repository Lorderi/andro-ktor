package ru.lorderi.androktor.ui.viewmodel.myresume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lorderi.androktor.model.Resume
import ru.lorderi.androktor.repository.JobRepository
import ru.lorderi.androktor.repository.SQLiteResumeRepository
import javax.inject.Inject

@HiltViewModel
class MyResumeViewModel @Inject constructor(
    private val networkRepository: JobRepository,
    //TODO!!!
    private val localRepository: SQLiteResumeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyResumeUiState())
    val uiState = _uiState.asStateFlow()

    fun loadLocalResumeById(id: Long) {
        viewModelScope.launch {
            try {
                val resume = localRepository.getResume(id)
                _uiState.update {
                    it.copy(resume = resume)
                }
            } catch (e: Exception) {
                println("Error while load local resume")
            }
        }
    }

    fun saveLocalResume(resume: Resume) {
        viewModelScope.launch {
            try {
                localRepository.saveResume(resume)
            } catch (e: Exception) {
                println("Error while save local resume")
            }
        }
    }

    fun loadResumeById(id: Long) {
        viewModelScope.launch {
            try {
                val resume = networkRepository.getResumeById(id)
                _uiState.update {
                    it.copy(resume = resume)
                }
            } catch (e: Exception) {
                println("Error while load resume")
            }
        }
    }

    fun saveResume(id: Long, resume: Resume) {
        viewModelScope.launch {
            try {
                val resumeReturned = networkRepository.saveResume(id, resume)
                _uiState.update {
                    it.copy(resume = resumeReturned)
                }
            } catch (e: Exception) {
                println("Error while save resume")
            }
        }
    }

    fun deleteResume(id: Long) {
        viewModelScope.launch {
            try {
                localRepository.deleteResume(id)
            } catch (e: Exception) {
                println("Error while delete resume")
            }
        }
    }
}
