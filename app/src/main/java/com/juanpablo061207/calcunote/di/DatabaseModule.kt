package com.juanpablo061207.calcunote.di

import android.content.Context
import androidx.room.Room
import com.juanpablo061207.calcunote.data.local.database.CourseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideCourseDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CourseDatabase::class.java, "course-database").build()

    @Singleton
    @Provides
    fun provideCourseDao(courseDatabase: CourseDatabase) = courseDatabase.courseDao()

    @Singleton
    @Provides
    fun provideNoteDao(courseDatabase: CourseDatabase) = courseDatabase.noteDao()
}