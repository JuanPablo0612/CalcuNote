package com.juanpablo061207.calcunote.domain.usecase.note

import com.juanpablo061207.calcunote.data.notes.NotesRepository
import com.juanpablo061207.calcunote.domain.model.Note
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(note: Note) = notesRepository.updateNote(note)
}