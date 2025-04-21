package de.dmcet.musclemaker.domain.workout.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import de.dmcet.musclemaker.domain.workout.persistence.entities.ExerciseEntity

@Dao
interface ExerciseDao {
    @Insert
    fun insertAll(vararg exercises: ExerciseEntity)

    @Delete
    fun delete(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercises")
    fun getAll(): List<ExerciseEntity>
}