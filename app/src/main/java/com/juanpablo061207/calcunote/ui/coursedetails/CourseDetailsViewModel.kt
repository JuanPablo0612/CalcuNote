package com.juanpablo061207.calcunote.ui.coursedetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo061207.calcunote.domain.model.Note
import com.juanpablo061207.calcunote.domain.model.YearFinalNotes
import com.juanpablo061207.calcunote.domain.usecase.note.CalculateYearFinalNotesUseCase
import com.juanpablo061207.calcunote.domain.usecase.course.GetCourseWithNotesUseCase
import com.juanpablo061207.calcunote.domain.usecase.course.SaveCourseUseCase
import com.juanpablo061207.calcunote.domain.usecase.course.UpdateCourseUseCase
import com.juanpablo061207.calcunote.domain.usecase.note.DeleteNoteUseCase
import com.juanpablo061207.calcunote.domain.usecase.note.SaveNoteUseCase
import com.juanpablo061207.calcunote.domain.usecase.note.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailsViewModel @Inject constructor(
    private val getCourseWithNotesUseCase: GetCourseWithNotesUseCase,
    private val saveCourseUseCase: SaveCourseUseCase,
    private val updateCourseUseCase: UpdateCourseUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val calculateYearFinalNotesUseCase: CalculateYearFinalNotesUseCase
) : ViewModel() {
    var uiState by mutableStateOf(CourseDetailsUiState())
        private set

    fun start(courseId: Long) {
        uiState = uiState.copy(courseId = courseId, isValidCourseName = true)
        getCourse()
    }

    private fun getCourse() {
        viewModelScope.launch {
            getCourseWithNotesUseCase(uiState.courseId).collect { courseWithNotes ->
                uiState = uiState.copy(
                    courseName = courseWithNotes.course.name,
                    notes = courseWithNotes.notes,
                    yearFinalNotes = calculateYearFinalNotesUseCase(courseWithNotes.notes)
                )
            }
        }
    }

    fun onCourseNameTextChanged(text: String) {
        uiState = uiState.copy(courseName = text)
        validateCourseName()
    }

    private fun validateCourseName() {
        val isValidCourseName = uiState.courseName.isNotBlank()
        uiState = uiState.copy(isValidCourseName = isValidCourseName)
    }

    fun onSelectedNotesTypeChanged(noteType: Int) {
        uiState = uiState.copy(selectedCompetenceId = noteType)
    }

    fun onSelectedPeriodIdChanged(period: Int) {
        uiState = uiState.copy(selectedPeriodId = period)
    }

    fun onNoteAdded() {
        viewModelScope.launch {
            saveNoteUseCase(
                courseId = uiState.courseId,
                periodId = uiState.selectedPeriodId,
                noteType = uiState.selectedCompetenceId
            )
        }
    }

    fun onNoteUpdated(note: Note) {
        viewModelScope.launch {
            updateNoteUseCase(note)
        }
    }

    fun onNoteDeleted(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }

    fun onEditCourseNameChanged() {
        uiState = uiState.copy(editCourseName = !uiState.editCourseName)
    }

    fun onTogglePeriodSelector() {
        uiState = uiState.copy(isPeriodSelectorExpanded = !uiState.isPeriodSelectorExpanded)
    }

    fun onSaveCourse() {
        viewModelScope.launch {
            uiState = uiState.copy(editCourseName = false)
            val courseName = uiState.courseName.trim()

            if (uiState.courseId == 0L) {
                val id = saveCourseUseCase(courseName)
                start(id)
            } else {
                updateCourseUseCase(uiState.courseId, courseName)
            }
        }
    }
}

data class CourseDetailsUiState(
    val courseId: Long = 0,
    val courseName: String = "",
    val isValidCourseName: Boolean = false,
    val editCourseName: Boolean = true,
    val isPeriodSelectorExpanded: Boolean = false,
    val notes: List<Note> = emptyList(),
    val selectedCompetenceId: Int = 0,
    val selectedPeriodId: Int = 0,
    val yearFinalNotes: YearFinalNotes = YearFinalNotes()
)