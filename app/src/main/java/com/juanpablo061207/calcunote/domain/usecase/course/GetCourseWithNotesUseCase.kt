package com.juanpablo061207.calcunote.domain.usecase.course

import com.juanpablo061207.calcunote.data.courses.CoursesRepository
import com.juanpablo061207.calcunote.data.notes.NotesRepository
import com.juanpablo061207.calcunote.domain.model.CourseWithNotes
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCourseWithNotesUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val notesRepository: NotesRepository
) {
    operator fun invoke(courseId: Long) =
        coursesRepository.getCourse(courseId)
            .combine(notesRepository.getNotesByCourse(courseId)) { course, notes ->
                CourseWithNotes(course = course, notes = notes)
            }
}