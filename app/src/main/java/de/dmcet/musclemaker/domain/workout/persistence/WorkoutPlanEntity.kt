package de.dmcet.musclemaker.domain.workout.persistence

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class WorkoutPlanEntity(
    @PrimaryKey val name: String
)

@Entity
data class WorkoutPlanWithSets(
    @Embedded val workoutPlan: WorkoutPlanEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "workoutPlanName"
    )
    val sets: List<WorkoutSetEntity>
)