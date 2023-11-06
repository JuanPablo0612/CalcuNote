package com.juanpablo061207.calcunote.domain.model

data class CourseWithNotes(
    val course: Course,
    val notes: List<Note>
)
