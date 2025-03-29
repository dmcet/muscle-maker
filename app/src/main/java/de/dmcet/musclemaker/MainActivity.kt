package de.dmcet.musclemaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.dmcet.musclemaker.domain.koin.domainModule
import de.dmcet.musclemaker.ui.home.Home
import de.dmcet.musclemaker.ui.koin.uiModule
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

                    NavHost(navController = navController, startDestination = Routes.Home) {
                        composable<Routes.Home> {
                            Home(
                                onStartWorkout = {},
                                onManageWorkouts = {},
                                onSeeHistory = {},
                            )
                        }
                    }
                }
            }
        }
    }
}
