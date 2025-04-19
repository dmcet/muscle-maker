package de.dmcet.musclemaker.domain.workout.persistence

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class WorkoutSetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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