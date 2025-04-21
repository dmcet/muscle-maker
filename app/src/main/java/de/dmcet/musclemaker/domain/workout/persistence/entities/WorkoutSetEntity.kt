package de.dmcet.musclemaker.domain.workout.persistence.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(
    tableName = "workout_sets",
    primaryKeys = ["reps", "weight", "exerciseName", "workoutPlanName"]
)
data class WorkoutSetEntity(
    val reps: Int,
    val weight: Double,
    val exerciseName: String,
    val workoutPlanName: String
)

@Entity
data class WorkoutSetWithExercise(
    @Embedded val workoutSet: WorkoutSetEntity,
    @Relation(
        parentColumn = "exerciseName",
        entityColumn = "name"
    )
    val exercise: ExerciseEntity
)