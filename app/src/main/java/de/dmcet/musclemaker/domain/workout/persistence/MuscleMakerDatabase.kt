package de.dmcet.musclemaker.domain.workout.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import de.dmcet.musclemaker.domain.workout.persistence.dao.ExerciseDao
import de.dmcet.musclemaker.domain.workout.persistence.dao.WorkoutPlanDao
import de.dmcet.musclemaker.domain.workout.persistence.dao.WorkoutSetDao
import de.dmcet.musclemaker.domain.workout.persistence.entities.ExerciseEntity
import de.dmcet.musclemaker.domain.workout.persistence.entities.WorkoutPlanEntity
import de.dmcet.musclemaker.domain.workout.persistence.entities.WorkoutSetEntity

@Database(
    entities = [ExerciseEntity::class, WorkoutPlanEntity::class,
        WorkoutSetEntity::class],
    version = 1
)
abstract class MuscleMakerDatabase: RoomDatabase() {
    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getWorkoutPlanDao(): WorkoutPlanDao
    abstract fun getWorkoutSetDao(): WorkoutSetDao
}