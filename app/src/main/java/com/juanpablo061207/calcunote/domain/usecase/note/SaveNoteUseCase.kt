package com.juanpablo061207.calcunote.domain.usecase.note

import com.juanpablo061207.calcunote.data.notes.NotesRepository
import com.juanpablo061207.calcunote.domain.model.Note
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(courseId: Long, periodId: Int, noteType: Int) {
        val note = Note(courseId = courseId, competenceId = noteType, periodId = periodId)
        notesRepository.saveNote(note)
    }
}