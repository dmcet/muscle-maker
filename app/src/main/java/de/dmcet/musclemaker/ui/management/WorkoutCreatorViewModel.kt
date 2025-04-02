package de.dmcet.musclemaker.ui.management

import androidx.lifecycle.viewModelScope
import de.dmcet.musclemaker.domain.workout.api.WorkoutPlan
import de.dmcet.musclemaker.domain.workout.api.WorkoutSet
import de.dmcet.musclemaker.domain.workout.api.ExerciseRepository
import de.dmcet.musclemaker.domain.workout.api.PlanRepository
import de.dmcet.musclemaker.domain.workout.api.Exercise
import de.dmcet.musclemaker.ui.management.api.WorkoutCreatorState
import de.dmcet.musclemaker.ui.management.api.WorkoutCreatorViewModelApi
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private val logger = KotlinLogging.logger("WorkoutEditorViewModel")

class WorkoutCreatorViewModel(
    exerciseRepository: ExerciseRepository,
    private val planRepository: PlanRepository,
) : WorkoutCreatorViewModelApi() {
    private val creatorStateFlow = MutableStateFlow(WorkoutCreatorState.ExerciseSelection)
    private val selectedExerciseStateFlow = MutableStateFlow(Exercise.None)
    private val nameStateFlow = MutableStateFlow("")
    private val createdSetsStateFlow = MutableStateFlow(emptyList<WorkoutSet>())

    override val availableExercises: Flow<List<Exercise>> = exerciseRepository.knownExercises
    override val workoutName: Flow<String> = nameStateFlow
    override val creatorState: Flow<WorkoutCreatorState> = creatorStateFlow
    override val createdSets = createdSetsStateFlow
    override val selectedExercise: Flow<Exercise> = selectedExerciseStateFlow

    override fun setWorkoutName(name: String) {
        logger.info { "Setting name to $name" }
        viewModelScope.launch {
            nameStateFlow.emit(name)
        }
    }

    override fun selectExercise(exercise: Exercise) {
        logger.info { "Exercise $exercise was selected" }
        viewModelScope.launch {
            selectedExerciseStateFlow.emit(exercise)
            creatorStateFlow.emit(WorkoutCreatorState.SetDefinition)
        }
    }

    override fun addSet(set: WorkoutSet) {
        logger.info { "Adding set $set to workout ${nameStateFlow.value}" }
        viewModelScope.launch {
            createdSetsStateFlow.emit(createdSetsStateFlow.value + set)
        }
    }

    override fun closeExercise() {
        logger.info { "Closing exercise ${selectedExerciseStateFlow.value}" }
        viewModelScope.launch {
            creatorStateFlow.emit(WorkoutCreatorState.ExerciseSelection)
        }
    }

    override fun onWorkoutPlanCreationFinished() {
        logger.info { "Workout creation has finished, constructing workout and storing it" }
        val newWorkoutPlan = WorkoutPlan(nameStateFlow.value, createdSetsStateFlow.value)
        logger.info { "New workout plan: $newWorkoutPlan" }
        viewModelScope.launch {
            planRepository.storeWorkoutPlan(newWorkoutPlan)
            resetCreator()
        }
    }

    private suspend fun resetCreator() {
        logger.info { "Resetting creator" }
        nameStateFlow.emit("")
        selectedExerciseStateFlow.emit(Exercise.None)
        createdSetsStateFlow.emit(emptyList())
    }
}
