package com.juanpablo061207.calcunote.domain.usecase.note

import com.juanpablo061207.calcunote.domain.model.Note
import java.math.RoundingMode
import javax.inject.Inject

class CalculateFinalNoteUseCase @Inject constructor() {
    operator fun invoke(notes: List<Note>): Double {
        var remainingPercentage = 100.0
        var nullCount = 0

        notes.forEach { note ->
            note.percentage?.let { percentage ->
                remainingPercentage -= percentage
            } ?: nullCount++
        }

        val defaultPercentage = remainingPercentage / nullCount
        val finalNote =
            notes.sumOf { note -> note.note * (note.percentage ?: defaultPercentage) / 100 }
        return finalNote.toBigDecimal().setScale(1, RoundingMode.HALF_UP).toDouble()
    }
}