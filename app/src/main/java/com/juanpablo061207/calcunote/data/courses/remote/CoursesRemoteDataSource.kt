package com.juanpablo061207.calcunote.data.courses.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.juanpablo061207.calcunote.domain.model.Course
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CoursesRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {
    fun getAllCourses() = callbackFlow<List<Course>> {
        val listener = firestore.collection("courses").addSnapshotListener { value, error ->
            trySend(value?.toObjects() ?: emptyList())
        }

        awaitClose { listener.remove() }
    }

    fun addCourse() {

    }
}