package com.juanpablo061207.calcunote.domain.model

import com.juanpablo061207.calcunote.data.notes.local.database.entity.NoteEntity

data class Note(
    val id: Long = 0,
    val courseId: Long = 0,
    val noteName: String = "",
    val note: Double = 0.0,
    val competenceId: Int = 0,
    val percentage: Double? = null,
    val periodId: Int = 0
)

fun Note.toEntity() = NoteEntity(
    id = id,
    courseId = courseId,
    noteName = noteName,
    note = note,
    competenceId = competenceId,
    percentage = percentage,
    periodId = periodId
)