package com.juanpablo061207.calcunote.domain.usecase.course

import com.juanpablo061207.calcunote.data.courses.CoursesRepository
import com.juanpablo061207.calcunote.domain.model.Course
import javax.inject.Inject

class UpdateCourseUseCase @Inject constructor(private val coursesRepository: CoursesRepository) {
    suspend operator fun invoke(courseId: Long, name: String) {
        val course = Course(id = courseId, name = name)

        coursesRepository.updateCourse(course)
    }
}