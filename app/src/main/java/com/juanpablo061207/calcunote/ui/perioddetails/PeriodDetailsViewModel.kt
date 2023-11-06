package com.juanpablo061207.calcunote.ui.perioddetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo061207.calcunote.domain.model.Note
import com.juanpablo061207.calcunote.domain.usecase.note.GetNotesByCourseAndPeriodUseCase
import com.juanpablo061207.calcunote.domain.usecase.note.SaveNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeriodDetailsViewModel @Inject constructor(
    private val getNotesByCourseAndPeriodUseCase: GetNotesByCourseAndPeriodUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(PeriodDetailsUiState())
        private set

    fun start(courseId: Long, periodId: Int) {
        uiState = uiState.copy(courseId = courseId, periodId = periodId)
        getPeriodNotes()
    }

    private fun getPeriodNotes() {
        viewModelScope.launch {
            getNotesByCourseAndPeriodUseCase(uiState.courseId, uiState.periodId).collect { notes ->
                uiState = uiState.copy(notes = notes)
            }
        }
    }

    fun addNote() {
        viewModelScope.launch {
            saveNoteUseCase(
                courseId = uiState.courseId,
                periodId = uiState.periodId,
                noteType = uiState.selectedNotesType
            )
        }
    }
}

data class PeriodDetailsUiState(
    val courseId: Long = 0,
    val periodId: Int = 0,
    val selectedNotesType: Int = 0,
    val notes: List<Note> = emptyList()
)