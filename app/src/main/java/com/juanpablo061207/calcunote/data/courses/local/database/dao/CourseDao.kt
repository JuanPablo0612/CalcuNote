package com.juanpablo061207.calcunote.data.courses.local.database.dao

import androidx.room.*
import com.juanpablo061207.calcunote.data.courses.local.database.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getAll(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE id = :courseId")
    fun getById(courseId: Long): Flow<CourseEntity>

    @Insert
    suspend fun insert(courseEntity: CourseEntity): Long

    @Insert
    suspend fun insertAll(vararg courseEntity: CourseEntity)

    @Update
    suspend fun update(courseEntity: CourseEntity)

    @Update
    suspend fun updateAll(vararg courseEntity: CourseEntity)

    @Delete
    suspend fun delete(vararg courseEntity: CourseEntity)

    @Delete
    suspend fun deleteAll(vararg courseEntity: CourseEntity)
}