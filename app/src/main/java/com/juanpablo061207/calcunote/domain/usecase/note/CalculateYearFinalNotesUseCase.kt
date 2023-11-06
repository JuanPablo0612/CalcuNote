package com.juanpablo061207.calcunote.domain.usecase.note

import com.juanpablo061207.calcunote.domain.model.Note
import com.juanpablo061207.calcunote.domain.model.Period
import com.juanpablo061207.calcunote.domain.model.YearFinalNotes
import java.math.RoundingMode
import javax.inject.Inject

class CalculateYearFinalNotesUseCase @Inject constructor() {
    operator fun invoke(notes: List<Note>): YearFinalNotes {
        val periodFinalNotes = (0..2).map { calculatePeriodFinalNotes(notes, it) }

        val notesWithPercentage = periodFinalNotes.mapIndexed { index, period ->
            Note(note = period.finalNote, percentage = if (index == 2) 40.0 else 30.0)
        }

        val yearFinalNote = calculateFinalNote(notesWithPercentage)

        val (firstPeriodFinalNotes, secondPeriodFinalNotes, thirdPeriodFinalNotes) = periodFinalNotes

        return YearFinalNotes(
            firstPeriod = firstPeriodFinalNotes,
            secondPeriod = secondPeriodFinalNotes,
            thirdPeriod = thirdPeriodFinalNotes,
            finalNote = yearFinalNote
        )
    }

    private fun calculatePeriodFinalNotes(notes: List<Note>, periodNumber: Int): Period {
        val notesByType = notes.filter { it.periodId == periodNumber }.groupBy { it.competenceId }
        val toBeNotes = notesByType[0] ?: emptyList()
        val toKnowNotes = notesByType[1] ?: emptyList()
        val toDoNotes = notesByType[2] ?: emptyList()

        val toBeFinalNote =
            Note(note = calculateFinalNote(toBeNotes), percentage = 30.0)
        val toKnowFinalNote =
            Note(note = calculateFinalNote(toKnowNotes), percentage = 35.0)
        val toDoFinalNote =
            Note(note = calculateFinalNote(toDoNotes), percentage = 35.0)
        val periodFinalNote =
            calculateFinalNote(listOf(toBeFinalNote, toKnowFinalNote, toDoFinalNote))

        return Period(
            toBeFinalNote = toBeFinalNote.note,
            toKnowFinalNote = toKnowFinalNote.note,
            toDoFinalNote = toDoFinalNote.note,
            finalNote = periodFinalNote
        )
    }

    private fun calculateFinalNote(notes: List<Note>): Double {
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