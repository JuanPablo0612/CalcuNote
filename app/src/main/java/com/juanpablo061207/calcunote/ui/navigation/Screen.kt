package com.juanpablo061207.calcunote.ui.navigation

sealed class Screen(val route: String) {
    object CourseDetails: Screen("course_details")
    object Main: Screen("home")
    object Login: Screen("login")
    object SignUp: Screen("sign_up")
}
