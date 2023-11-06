package com.juanpablo061207.calcunote.data.notes.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.juanpablo061207.calcunote.data.notes.local.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE courseId = :courseId")
    fun getByCourse(courseId: Long): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE courseId = :courseId AND periodId = :periodId")
    fun getByCourseAndPeriod(courseId: Long, periodId: Int): Flow<List<NoteEntity>>

    @Insert
    suspend fun insertAll(vararg notes: NoteEntity)

    @Insert
    suspend fun insert(noteEntity: NoteEntity)

    @Update
    suspend fun updateAll(vararg notes: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteAll(vararg notes: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: NoteEntity)
}