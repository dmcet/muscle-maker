package de.dmcet.musclemaker.domain.workout.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import de.dmcet.musclemaker.domain.workout.persistence.entities.WorkoutSetEntity
import de.dmcet.musclemaker.domain.workout.persistence.entities.WorkoutSetWithExercise

@Dao
interface WorkoutSetDao {
    @Insert
    fun insertAll(vararg workoutSets: WorkoutSetEntity)

    @Delete
    fun delete(workoutSet: WorkoutSetEntity)

    @Transaction
    @Query("SELECT * FROM workout_sets")
    fun getAll(): List<WorkoutSetWithExercise>
}