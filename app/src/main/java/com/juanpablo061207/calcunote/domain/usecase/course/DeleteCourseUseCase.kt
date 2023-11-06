package com.juanpablo061207.calcunote.domain.usecase.course

import com.juanpablo061207.calcunote.data.notes.NotesRepository
import com.juanpablo061207.calcunote.domain.model.Course
import com.juanpablo061207.calcunote.data.courses.CoursesRepository
import com.juanpablo061207.calcunote.domain.model.Note
import javax.inject.Inject

class DeleteCourseUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke(course: Course, notes: List<Note>) {
        coursesRepository.deleteCourse(course)
        notesRepository.deleteNotes(notes)
    }
}