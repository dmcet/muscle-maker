package de.dmcet.musclemaker.domain.workout.plan

import de.dmcet.musclemaker.domain.workout.api.PlanRepository
import de.dmcet.musclemaker.domain.workout.api.WorkoutPlan
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

private val logger = KotlinLogging.logger("InMemoryWorkoutRepository")

class InMemoryPlanRepository : PlanRepository {
    private val mutableKnownWorkouts: MutableStateFlow<List<WorkoutPlan>> =
        MutableStateFlow(emptyList())

    override val knownPlans: Flow<List<WorkoutPlan>> = mutableKnownWorkouts

    override suspend fun storeWorkoutPlan(workoutPlan: WorkoutPlan) {
        logger.info { "Adding new workout $workoutPlan" }
        mutableKnownWorkouts.emit(mutableKnownWorkouts.value + workoutPlan)
    }

    override suspend fun overwriteExisting(
        existing: WorkoutPlan,
        new: WorkoutPlan,
    ) {
        logger.info { "Replacing workout $existing with $new" }
        mutableKnownWorkouts.emit(mutableKnownWorkouts.value - existing + new)
    }
}
