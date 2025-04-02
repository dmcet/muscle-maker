package de.dmcet.musclemaker.domain.workout.api

import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    val knownExercises: Flow<List<Exercise>>
}
