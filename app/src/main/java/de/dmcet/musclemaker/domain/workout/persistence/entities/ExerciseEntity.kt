package de.dmcet.musclemaker.domain.workout.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.dmcet.musclemaker.domain.workout.api.BodyArea
import de.dmcet.musclemaker.domain.workout.api.Exercise

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val name: String,
    val bodyArea: String,
) {
    fun Exercise.toEntity() = ExerciseEntity(this.name, this.bodyArea.name)
    fun toExercise() = Exercise(this.name, BodyArea.valueOf(this.bodyArea))
}