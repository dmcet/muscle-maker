package de.dmcet.musclemaker.domain.workout.persistence.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "workout_plans")
data class WorkoutPlanEntity(
    @PrimaryKey val name: String
)

data class WorkoutPlanWithSets(
    @Embedded val workoutPlan: WorkoutPlanEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "workoutPlanName"
    )
    val sets: List<WorkoutSetWithExercise>
)