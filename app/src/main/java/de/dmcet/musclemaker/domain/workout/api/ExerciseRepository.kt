package de.dmcet.musclemaker.domain.workout.api

import de.dmcet.musclemaker.domain.workout.exercise.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    val knownExercises: Flow<List<Exercise>>
}
