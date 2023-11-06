package com.juanpablo061207.calcunote.ui.common

import androidx.compose.ui.graphics.Color
import com.juanpablo061207.calcunote.R

fun getNoteColor(note: Double): Color {
    return when (note) {
        in 4.6..5.0 -> Color.Green
        in 4.0..4.5 -> Color(0xFF2196F3)
        in 3.0..3.9 -> Color(0xFFFFFF00)
        else -> Color.Red
    }
}

fun getNoteValueAsText(note: Double): Int {
    return when (note) {
        in 4.6..5.0 -> R.string.grade_status_superior
        in 4.0..4.5 -> R.string.grade_status_high
        in 3.0..3.9 -> R.string.grade_status_basic
        else -> R.string.grade_status_reproached
    }
}

fun getPeriodTextId(periodNumber: Int): Int {
    return when (periodNumber) {
        0 -> R.string.academic_period_first
        1 -> R.string.academic_period_second
        2 -> R.string.academic_period_third
        else -> R.string.course_final_note_label
    }
}

fun getNoteTypeTextId(noteId: Int): Int {
    return when (noteId) {
        0 -> R.string.competence_to_be
        1 -> R.string.competence_to_know
        else -> R.string.competence_to_do
    }
}

