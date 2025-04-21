package de.dmcet.musclemaker.domain.workout.exercise

import de.dmcet.musclemaker.domain.workout.api.BodyArea
import de.dmcet.musclemaker.domain.workout.api.Exercise
import de.dmcet.musclemaker.domain.workout.api.ExerciseRepository
import de.dmcet.musclemaker.domain.workout.persistence.dao.ExerciseDao
import de.dmcet.musclemaker.domain.workout.persistence.entities.ExerciseEntity
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

private val logger = KotlinLogging.logger("RoomExerciseRepository")

private val defaultExercises by lazy {
    listOf(
        // Shoulders
        Exercise("Dumbbell Shoulder Press", BodyArea.Shoulders),
        Exercise("Lateral Raise", BodyArea.Shoulders),
        Exercise("Front Raise", BodyArea.Shoulders),
        Exercise("Bent-Over Lateral Raise", BodyArea.Shoulders),
        Exercise("Arnold Press", BodyArea.Shoulders),
        Exercise("Upright Row", BodyArea.Shoulders),
        Exercise("Face Pull", BodyArea.Shoulders),
        Exercise("Barbell Shoulder Press", BodyArea.Shoulders),
        // Chest
        Exercise("Barbell Bench Press", BodyArea.Chest),
        Exercise("Incline Dumbbell Press", BodyArea.Chest),
        Exercise("Dumbbell Flyes", BodyArea.Chest),
        Exercise("Push-Up", BodyArea.Chest),
        Exercise("Chest Dips", BodyArea.Chest),
        Exercise("Pec Deck Machine", BodyArea.Chest),
        Exercise("Cable Crossover", BodyArea.Chest),
        Exercise("Decline Bench Press", BodyArea.Chest),
        // Back
        Exercise("Deadlift", BodyArea.Back),
        Exercise("Pull-Up", BodyArea.Back),
        Exercise("Bent-Over Barbell Row", BodyArea.Back),
        Exercise("One-Arm Dumbbell Row", BodyArea.Back),
        Exercise("Lat Pulldown", BodyArea.Back),
        Exercise("Seated Cable Row", BodyArea.Back),
        Exercise("T-Bar Row", BodyArea.Back),
        Exercise("Good Morning", BodyArea.Back),
        // Legs
        Exercise("Barbell Squat", BodyArea.Legs),
        Exercise("Leg Press", BodyArea.Legs),
        Exercise("Dumbbell Lunge", BodyArea.Legs),
        Exercise("Leg Extension", BodyArea.Legs),
        Exercise("Leg Curl", BodyArea.Legs),
        Exercise("Standing Calf Raise", BodyArea.Legs),
        Exercise("Seated Calf Raise", BodyArea.Legs),
        Exercise("Sumo Squat", BodyArea.Legs),
        // Arms
        Exercise("Dumbbell Bicep Curl", BodyArea.Arms),
        Exercise("Hammer Curl", BodyArea.Arms),
        Exercise("Concentration Curl", BodyArea.Arms),
        Exercise("Tricep Pushdown", BodyArea.Arms),
        Exercise("Tricep Kickback", BodyArea.Arms),
        Exercise("French Press", BodyArea.Arms),
        Exercise("Tricep Dips", BodyArea.Arms),
        Exercise("Zottman Curl", BodyArea.Arms),
        // Abs
        Exercise("Crunch", BodyArea.Abs),
        Exercise("Hanging Leg Raise", BodyArea.Abs),
        Exercise("Russian Twist", BodyArea.Abs),
        Exercise("Plank", BodyArea.Abs),
        Exercise("Bicycle Crunch", BodyArea.Abs),
        Exercise("V-Up", BodyArea.Abs),
        Exercise("Sit-Up", BodyArea.Abs),
        Exercise("Mountain Climber", BodyArea.Abs),
    )
}

class RoomExerciseRepository(exerciseDao: ExerciseDao) :
    ExerciseRepository {
    @OptIn(ExperimentalCoroutinesApi::class)

    override val knownExercises: Flow<List<Exercise>> =
        exerciseDao.getAll().mapLatest { exercises ->
            if (exercises.isEmpty()) {
                logger.info { "No exercises yet, prepopulating..." }
                prepopulateExercises(exerciseDao)
                emptyList()
            } else {
                logger.info { "Found the following exercises: $exercises" }
                exercises.map { Exercise(it.name, BodyArea.valueOf(it.bodyArea)) }
            }
        }

    private fun prepopulateExercises(exerciseDao: ExerciseDao) {
        CoroutineScope(Dispatchers.IO).launch {
            exerciseDao.insertAll(*defaultExercises.map {
                ExerciseEntity(
                    it.name,
                    it.bodyArea.name
                )
            }.toTypedArray())
        }
    }
}