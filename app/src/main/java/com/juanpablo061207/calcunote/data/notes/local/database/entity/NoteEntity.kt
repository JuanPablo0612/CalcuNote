package com.juanpablo061207.calcunote.data.notes.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juanpablo061207.calcunote.domain.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val courseId: Long,
    val noteName: String,
    val competenceId: Int,
    val note: Double,
    val percentage: Double?,
    val periodId: Int
)

fun NoteEntity.toDomain() = Note(
    id = id,
    courseId = courseId,
    noteName = noteName,
    competenceId = competenceId,
    note = note,
    percentage = percentage,
    periodId = periodId
)
