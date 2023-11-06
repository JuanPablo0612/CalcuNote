package com.juanpablo061207.calcunote.data.courses

import com.juanpablo061207.calcunote.data.courses.local.database.dao.CourseDao
import com.juanpablo061207.calcunote.data.courses.local.database.entity.CourseEntity
import com.juanpablo061207.calcunote.data.courses.local.database.entity.toDomain
import com.juanpablo061207.calcunote.data.courses.remote.CoursesRemoteDataSource
import com.juanpablo061207.calcunote.domain.model.Course
import com.juanpablo061207.calcunote.domain.model.toEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoursesRepository @Inject constructor(
    private val coursesRemoteDataSource: CoursesRemoteDataSource,
    private val courseDao: CourseDao
) {
    val courses = courseDao.getAll().map { it.map { entity -> entity.toDomain() } }

    suspend fun updateCourses() {
        coursesRemoteDataSource.getAllCourses().collect { updatedCourses ->
            val actualCourses = courseDao.getAll().first()
            val coursesToInsert = mutableListOf<CourseEntity>()
            val coursesToUpdate = mutableListOf<CourseEntity>()
            val coursesToDelete = mutableListOf<CourseEntity>()

            for (actual in actualCourses) {
                val updated = updatedCourses.find { it.id == actual.id }
                if (updated != null) {
                    coursesToUpdate.add(updated.toEntity())
                }
            }

            for (updated in updatedCourses) {
                if (!actualCourses.any { it.id == updated.id }) {
                    coursesToInsert.add(updated.toEntity())
                }
            }

            courseDao.insertAll(*coursesToInsert.toTypedArray())
            courseDao.updateAll(*coursesToUpdate.toTypedArray())
        }
    }

    fun getCourse(courseId: Long) = courseDao.getById(courseId).map { it.toDomain() }

    suspend fun saveCourse(course: Course): Long {
        val courseEntity = course.toEntity()
        return courseDao.insert(courseEntity)
    }

    suspend fun updateCourse(course: Course) {
        val courseEntity = course.toEntity()
        courseDao.update(courseEntity)
    }

    suspend fun deleteCourse(course: Course) {
        val courseEntity = course.toEntity()
        courseDao.delete(courseEntity)
    }

}