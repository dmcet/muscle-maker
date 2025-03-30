package de.dmcet.musclemaker.domain.koin

import de.dmcet.musclemaker.domain.workout.InMemoryPlanRepository
import de.dmcet.musclemaker.domain.workout.api.ExerciseRepository
import de.dmcet.musclemaker.domain.workout.api.PlanRepository
import de.dmcet.musclemaker.domain.workout.exercise.FixedValueExerciseRepository
import org.koin.dsl.module

val domainModule =
    module {
        single<ExerciseRepository> { FixedValueExerciseRepository() }
        single<PlanRepository> { InMemoryPlanRepository() }
    }
