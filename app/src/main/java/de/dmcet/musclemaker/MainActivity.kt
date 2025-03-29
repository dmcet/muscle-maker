package de.dmcet.musclemaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import de.dmcet.musclemaker.domain.koin.domainModule
import de.dmcet.musclemaker.ui.home.Home
import de.dmcet.musclemaker.ui.koin.uiModule
import de.dmcet.musclemaker.ui.management.WorkoutCreator
import de.dmcet.musclemaker.ui.management.WorkoutManager
import de.dmcet.musclemaker.ui.navigation.Routes
import de.dmcet.musclemaker.ui.theme.MusclemakerTheme
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusclemakerTheme {
                KoinApplication(application = {
                    modules(
                        uiModule,
                        domainModule,
                    )
                }) {
                    val navController = rememberNavController()
                    val insets = WindowInsets.systemBars.union(WindowInsets.ime)

                    NavHost(
                        modifier = Modifier.windowInsetsPadding(insets),
                        navController = navController,
                        startDestination = Routes.Home,
                    ) {
                        composable<Routes.Home> {
                            Home(
                                onStartWorkout = {},
                                onManageWorkouts = { navController.navigate(Routes.WorkoutManager) },
                                onSeeHistory = {},
                            )
                        }

                        navigation<Routes.ManageWorkouts>(startDestination = Routes.WorkoutManager) {
                            composable<Routes.WorkoutManager> {
                                WorkoutManager(
                                    onAddNewWorkout = { navController.navigate(Routes.WorkoutCreator) },
                                )
                            }

                            composable<Routes.WorkoutCreator> {
                                WorkoutCreator(navigateBackToHome = {
                                    navController.navigate(Routes.Home) {
                                        popUpTo(Routes.Home) {
                                            inclusive = true
                                        }
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}
