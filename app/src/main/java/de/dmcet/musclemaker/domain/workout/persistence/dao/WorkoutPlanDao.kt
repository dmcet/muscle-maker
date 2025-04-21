package de.dmcet.musclemaker.domain.workout.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import de.dmcet.musclemaker.domain.workout.persistence.entities.WorkoutPlanEntity
import de.dmcet.musclemaker.domain.workout.persistence.entities.WorkoutPlanWithSets
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutPlanDao {
    @Insert
    suspend fun insertAll(vararg workoutPlans: WorkoutPlanEntity)

    @Delete
    suspend fun delete(workoutPlan: WorkoutPlanEntity)

    @Transaction
    @Query("SELECT * FROM workout_plans")
    fun getAll(): Flow<List<WorkoutPlanWithSets>>
}