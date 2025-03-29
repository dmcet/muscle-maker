package de.dmcet.musclemaker.ui.management

import androidx.lifecycle.ViewModel
import de.dmcet.musclemaker.domain.workout.api.PlanRepository
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger("WorkoutManagerViewModel")

class WorkoutManagerViewModel(
    planRepository: PlanRepository,
) : ViewModel() {
    val workouts = planRepository.knownPlans
}
