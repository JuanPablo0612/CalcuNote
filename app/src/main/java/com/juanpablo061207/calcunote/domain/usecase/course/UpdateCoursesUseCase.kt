package com.juanpablo061207.calcunote.domain.usecase.course

import com.juanpablo061207.calcunote.data.courses.CoursesRepository
import javax.inject.Inject

class UpdateCoursesUseCase @Inject constructor(private val coursesRepository: CoursesRepository) {
    suspend operator fun invoke() = coursesRepository.updateCourses()
}