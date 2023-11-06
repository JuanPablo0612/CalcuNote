package com.juanpablo061207.calcunote.domain.usecase.course

import com.juanpablo061207.calcunote.data.courses.CoursesRepository
import com.juanpablo061207.calcunote.data.notes.NotesRepository
import com.juanpablo061207.calcunote.domain.model.CourseWithNotes
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAllCoursesWithNotesUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val notesRepository: NotesRepository
) {
    operator fun invoke() =
        coursesRepository.courses.combine(notesRepository.notes) { courses, notes ->
            courses.map { course ->
                CourseWithNotes(course = course, notes = notes.filter { it.courseId == course.id })
            }
        }
}