package com.juanpablo061207.calcunote.data.courses.local.database.entity

import androidx.room.*
import com.juanpablo061207.calcunote.domain.model.Course

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
)

fun CourseEntity.toDomain() = Course(
    id = id,
    name = name
)