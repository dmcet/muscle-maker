package de.dmcet.musclemaker.domain.workout.plan.room

import de.dmcet.musclemaker.domain.workout.api.PlanRepository
import de.dmcet.musclemaker.domain.workout.api.WorkoutPlan
import kotlinx.coroutines.flow.Flow

class RoomPlanRepository: PlanRepository {
    override val knownPlans: Flow<List<WorkoutPlan>>
        get() = TODO("Not yet implemented")

    override suspend fun storeWorkoutPlan(workoutPlan: WorkoutPlan) {
        TODO("Not yet implemented")
    }

    override suspend fun overwriteExisting(existing: WorkoutPlan, new: WorkoutPlan) {
        TODO("Not yet implemented")
    }
}