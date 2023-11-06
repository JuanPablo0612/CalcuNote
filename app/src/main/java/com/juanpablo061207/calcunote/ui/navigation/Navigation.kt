package com.juanpablo061207.calcunote.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.juanpablo061207.calcunote.ui.coursedetails.CourseDetailsScreen
import com.juanpablo061207.calcunote.ui.login.LoginScreen
import com.juanpablo061207.calcunote.ui.main.HomeScreen
import com.juanpablo061207.calcunote.ui.signup.SignUpScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(viewModel: NavigationViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    val navController = rememberAnimatedNavController()

    val startDestination = if (uiState.isLoggedIn) Screen.Main.route else Screen.Login.route

    if (!uiState.isLoading) {
        AnimatedNavHost(navController = navController, startDestination = startDestination) {
            composable(
                route = Screen.Login.route,
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popEnterTransition = {
                    slideInHorizontally { height -> -height }
                }
            ) {
                LoginScreen(navController = navController)
            }

            composable(
                route = Screen.SignUp.route,
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popExitTransition = {
                    slideOutHorizontally { height -> height }
                }
            ) {
                SignUpScreen(navController = navController)
            }

            composable(
                route = Screen.Main.route,
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popEnterTransition = {
                    slideInHorizontally { height -> -height }
                }
            ) {
                HomeScreen(navController = navController)
            }

            composable(
                route = "${Screen.CourseDetails.route}?courseId={courseId}",
                arguments = listOf(navArgument("courseId") {
                    type = NavType.LongType
                    defaultValue = 0L
                }),
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popEnterTransition = {
                    slideInHorizontally { height -> -height }
                },
                popExitTransition = {
                    slideOutHorizontally { height -> height }
                }
            ) {
                CourseDetailsScreen(courseId = it.arguments?.getLong("courseId") ?: 0L)
            }
        }
    }
}