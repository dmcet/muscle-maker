package de.dmcet.musclemaker.domain.koin

import androidx.room.Room
import de.dmcet.musclemaker.domain.workout.plan.InMemoryPlanRepository
import de.dmcet.musclemaker.domain.workout.api.ExerciseRepository
import de.dmcet.musclemaker.domain.workout.api.PlanRepository
import de.dmcet.musclemaker.domain.workout.exercise.RoomExerciseRepository
import de.dmcet.musclemaker.domain.workout.persistence.MuscleMakerDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val domainModule =
    module {
        single<ExerciseRepository> { RoomExerciseRepository(get<MuscleMakerDatabase>().getExerciseDao()) }
        single<PlanRepository> { InMemoryPlanRepository() }
        single<MuscleMakerDatabase> {
            Room.databaseBuilder(
                androidApplication(),
                MuscleMakerDatabase::class.java,
                "musclemaker"
            ).build()
        }
    }
