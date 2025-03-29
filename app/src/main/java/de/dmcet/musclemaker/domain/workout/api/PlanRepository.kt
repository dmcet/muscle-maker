package de.dmcet.musclemaker.domain.workout.api

import de.dmcet.musclemaker.domain.workout.WorkoutPlan
import kotlinx.coroutines.flow.Flow

interface PlanRepository {
    val knownPlans: Flow<List<WorkoutPlan>>

    suspend fun storeWorkoutPlan(workoutPlan: WorkoutPlan)

    suspend fun overwriteExisting(
        existing: WorkoutPlan,
        new: WorkoutPlan,
    )
}
