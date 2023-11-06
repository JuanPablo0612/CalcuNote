package com.juanpablo061207.calcunote.domain.usecase.note

import com.juanpablo061207.calcunote.data.notes.NotesRepository
import javax.inject.Inject

class GetNotesByCourseAndPeriodUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke(courseId: Long, periodId: Int) =
        notesRepository.getNotesByCourseAndPeriod(courseId, periodId)
}