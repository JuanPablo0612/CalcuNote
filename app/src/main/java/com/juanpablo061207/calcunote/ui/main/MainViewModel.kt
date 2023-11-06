package com.juanpablo061207.calcunote.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo061207.calcunote.domain.model.CourseWithNotes
import com.juanpablo061207.calcunote.domain.usecase.note.CalculateYearFinalNotesUseCase
import com.juanpablo061207.calcunote.domain.usecase.course.GetAllCoursesWithNotesUseCase
import com.juanpablo061207.calcunote.domain.usecase.course.UpdateCoursesUseCase
import com.juanpablo061207.calcunote.domain.usecase.course.DeleteCourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateCoursesUseCase: UpdateCoursesUseCase,
    private val getAllCoursesWithNotesUseCase: GetAllCoursesWithNotesUseCase,
    private val deleteCourseUseCase: DeleteCourseUseCase,
    private val calculateYearFinalNotesUseCase: CalculateYearFinalNotesUseCase
) : ViewModel() {
    var uiState by mutableStateOf(MainUiState())
        private set

    init {
        updateCourses()
        getAllCourses()
    }

    private fun updateCourses() {
        viewModelScope.launch {
            updateCoursesUseCase()
        }
    }

    private fun getAllCourses() {
        viewModelScope.launch {
            getAllCoursesWithNotesUseCase().collect {
                val courses = it.map { courseWithNotes ->
                    val yearFinalNotes = calculateYearFinalNotesUseCase(courseWithNotes.notes)

                    CourseUiState(
                        courseWithNotes = courseWithNotes,
                        yearFinalNote = yearFinalNotes.finalNote
                    )
                }

                uiState = uiState.copy(courses = courses, isLoading = false)
            }
        }
    }

    fun deleteCourse(courseUiState: CourseUiState) {
        viewModelScope.launch {
            deleteCourseUseCase(
                courseUiState.courseWithNotes.course,
                courseUiState.courseWithNotes.notes
            )
        }
    }
}

data class MainUiState(
    val courses: List<CourseUiState> = emptyList(),
    val isLoading: Boolean = true
)

data class CourseUiState(
    val courseWithNotes: CourseWithNotes,
    val yearFinalNote: Double = 0.0
)