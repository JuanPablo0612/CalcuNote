package com.juanpablo061207.calcunote.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juanpablo061207.calcunote.data.courses.local.database.dao.CourseDao
import com.juanpablo061207.calcunote.data.notes.local.database.dao.NoteDao
import com.juanpablo061207.calcunote.data.courses.local.database.entity.CourseEntity
import com.juanpablo061207.calcunote.data.notes.local.database.entity.NoteEntity

@Database(entities = [CourseEntity::class, NoteEntity::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun noteDao(): NoteDao
}