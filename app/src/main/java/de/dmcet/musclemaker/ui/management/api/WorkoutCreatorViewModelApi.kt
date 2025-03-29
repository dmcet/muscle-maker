package de.dmcet.musclemaker.ui.management.api

import androidx.lifecycle.ViewModel
import de.dmcet.musclemaker.domain.workout.WorkoutSet
import de.dmcet.musclemaker.domain.workout.exercise.Exercise
import kotlinx.coroutines.flow.Flow

enum class WorkoutCreatorState { ExerciseSelection, SetDefinition }

abstract class WorkoutCreatorViewModelApi : ViewModel() {
    abstract val creatorState: Flow<WorkoutCreatorState>

    abstract val availableExercises: Flow<List<Exercise>>

    abstract val workoutName: Flow<String>
    abstract val selectedExercise: Flow<Exercise>
    abstract val createdSets: Flow<List<WorkoutSet>>

    abstract suspend fun setWorkoutName(name: String)

    abstract suspend fun selectExercise(exercise: Exercise)

    abstract suspend fun addSet(set: WorkoutSet)

    abstract suspend fun closeExercise()

    abstract suspend fun onWorkoutPlanCreationFinished()
}
