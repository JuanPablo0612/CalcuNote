package com.juanpablo061207.calcunote.domain.model

data class YearFinalNotes(
    val firstPeriod: Period = Period(),
    val secondPeriod: Period = Period(),
    val thirdPeriod: Period = Period(),
    val finalNote: Double = 0.0
)
