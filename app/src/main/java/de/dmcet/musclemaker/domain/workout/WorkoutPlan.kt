package de.dmcet.musclemaker.domain.workout

import de.dmcet.musclemaker.domain.workout.exercise.Exercise

data class WorkoutSet(
    val reps: Int,
    val weight: Double,
    val exercise: Exercise,
) {
    companion object {
        val Empty = WorkoutSet(reps = -1, weight = -1.0, exercise = Exercise.None)
    }
}

data class WorkoutPlan(
    val name: String,
    val sets: List<WorkoutSet>,
)
