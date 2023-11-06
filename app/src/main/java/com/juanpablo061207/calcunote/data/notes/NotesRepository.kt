package com.juanpablo061207.calcunote.data.notes

import com.juanpablo061207.calcunote.data.notes.local.database.dao.NoteDao
import com.juanpablo061207.calcunote.data.notes.local.database.entity.toDomain
import com.juanpablo061207.calcunote.domain.model.Note
import com.juanpablo061207.calcunote.domain.model.toEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepository @Inject constructor(private val noteDao: NoteDao) {
    val notes = noteDao.getAll().map { it.map { entity -> entity.toDomain() } }

    fun getNotesByCourse(courseId: Long) =
        noteDao.getByCourse(courseId).map { it.map { entity -> entity.toDomain() } }

    fun getNotesByCourseAndPeriod(courseId: Long, periodId: Int) =
        noteDao.getByCourseAndPeriod(courseId, periodId)
            .map { it.map { entity -> entity.toDomain() } }

    suspend fun saveNote(note: Note) {
        val noteEntity = note.toEntity()
        noteDao.insert(noteEntity)
    }

    suspend fun saveNotes(notes: List<Note>) {
        val noteEntities = notes.map { it.toEntity() }
        noteDao.insertAll(*noteEntities.toTypedArray())
    }

    suspend fun updateNote(note: Note) {
        val noteEntity = note.toEntity()
        noteDao.update(noteEntity)
    }

    suspend fun updateNotes(notes: List<Note>) {
        val noteEntities = notes.map { it.toEntity() }
        noteDao.updateAll(*noteEntities.toTypedArray())
    }

    suspend fun deleteNote(note: Note) {
        val noteEntity = note.toEntity()
        noteDao.delete(noteEntity)
    }

    suspend fun deleteNotes(notes: List<Note>) {
        val noteEntities = notes.map { it.toEntity() }
        noteDao.deleteAll(*noteEntities.toTypedArray())
    }
}