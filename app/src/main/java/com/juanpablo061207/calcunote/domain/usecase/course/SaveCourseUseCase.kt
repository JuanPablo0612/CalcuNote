package com.juanpablo061207.calcunote.domain.usecase.course

import com.juanpablo061207.calcunote.data.courses.CoursesRepository
import com.juanpablo061207.calcunote.domain.model.Course
import javax.inject.Inject

class SaveCourseUseCase @Inject constructor(private val coursesRepository: CoursesRepository) {
    suspend operator fun invoke(name: String): Long {
        val course = Course(name = name)
        return coursesRepository.saveCourse(course)
    }
}