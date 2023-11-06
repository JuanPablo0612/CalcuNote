package com.juanpablo061207.calcunote.domain.model

import com.juanpablo061207.calcunote.data.courses.local.database.entity.CourseEntity

data class Course(
    val id: Long = 0,
    val name: String = ""
)

fun Course.toEntity() = CourseEntity(
    id = id,
    name = name
)