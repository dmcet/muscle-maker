package de.dmcet.musclemaker.ui.management

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.dmcet.musclemaker.R
import de.dmcet.musclemaker.domain.workout.api.WorkoutSet
import de.dmcet.musclemaker.domain.workout.api.Exercise
import de.dmcet.musclemaker.ui.management.api.WorkoutCreatorState
import de.dmcet.musclemaker.ui.management.api.WorkoutCreatorViewModelApi
import de.dmcet.musclemaker.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun WorkoutCreator(
    navigateBackToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutCreatorViewModelApi = koinViewModel(),
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val creatorState by viewModel.creatorState.collectAsState(WorkoutCreatorState.ExerciseSelection)
        val availableExercises = viewModel.availableExercises.collectAsState(emptyList())
        val selectedExercise = viewModel.selectedExercise.collectAsState(Exercise.None)
        val workoutName = viewModel.workoutName.collectAsState("")
        val createdSets = viewModel.createdSets.collectAsState(emptyList())

        when (creatorState) {
            WorkoutCreatorState.ExerciseSelection ->
                ExerciseSelectionList(
                    availableExercisesProvider = { availableExercises.value },
                    createdSetsProvider = { createdSets.value },
                    workoutNameProvider = { workoutName.value },
                    onSelectExercise = viewModel::selectExercise,
                    onFinishWorkoutCreation = {
                        viewModel.onWorkoutPlanCreationFinished()
                        navigateBackToHome()
                    },
                    onSetWorkoutName = viewModel::setWorkoutName,
                )

            WorkoutCreatorState.SetDefinition ->
                SetDefinitionMenu(
                    selectedExerciseProvider = { selectedExercise.value },
                    createdSetProvider = { createdSets.value },
                    onAddSet = viewModel::addSet,
                    onCloseExercise = viewModel::closeExercise,
                )
        }
    }
}

@Composable
private fun ColumnScope.SetDefinitionMenu(
    selectedExerciseProvider: () -> Exercise,
    createdSetProvider: () -> List<WorkoutSet>,
    onAddSet: (WorkoutSet) -> Unit,
    onCloseExercise: () -> Unit,
) {
    val createdSets = createdSetProvider().filter { it.exercise == selectedExerciseProvider() }

    val newSetReps = remember(createdSets) { mutableStateOf("") }
    val newSetWeight = remember(createdSets) { mutableStateOf("") }

    WorkoutCreatorScreenLayout(
        headlineTextProvider = { "Add sets for ${selectedExerciseProvider().name}" },
        listContent = {
            items(createdSets + WorkoutSet.Empty) {
                val isEmptySet = it == WorkoutSet.Empty

                val setReps =
                    if (isEmptySet) {
                        newSetReps
                    } else {
                        remember { mutableStateOf(it.reps.toString()) }
                    }

                val setWeight =
                    if (isEmptySet) {
                        newSetWeight
                    } else {
                        remember { mutableStateOf(it.weight.toString()) }
                    }

                EditableSetRow(
                    reps = setReps,
                    weight = setWeight,
                    onChangeReps = setReps::value::set,
                    onChangeWeight = setWeight::value::set,
                    isEditable = isEmptySet,
                )
            }
        },
        bottomRowContent = {
            Button(
                modifier = Modifier.weight(0.5f),
                onClick = {
                    onAddSet(
                        WorkoutSet(
                            newSetReps.value.toInt(),
                            newSetWeight.value.toDouble(),
                            selectedExerciseProvider(),
                        ),
                    )
                },
                enabled = isValidSet(newSetReps.value, newSetWeight.value),
            ) {
                Text(text = stringResource(R.string.add_set))
            }

            Button(
                modifier = Modifier.weight(0.5f),
                onClick = onCloseExercise,
            ) {
                Text(text = stringResource(R.string.finalize_exercise))
            }
        },
    )
}

private fun isValidSet(
    reps: String,
    weight: String,
): Boolean = reps.toIntOrNull() != null && weight.toDoubleOrNull() != null

@Composable
private fun EditableSetRow(
    reps: State<String>,
    onChangeReps: (String) -> Unit,
    weight: State<String>,
    onChangeWeight: (String) -> Unit,
    isEditable: Boolean,
) {
    Card(
        modifier =
            Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(0.45f),
                value = reps.value,
                onValueChange = onChangeReps,
                placeholder = { Text(text = "Reps") },
                enabled = isEditable,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
            )

            Icon(
                modifier = Modifier.weight(0.1f),
                imageVector = Icons.Default.Close,
                contentDescription = null,
            )

            OutlinedTextField(
                modifier = Modifier.weight(0.45f),
                value = weight.value,
                onValueChange = onChangeWeight,
                placeholder = { Text(text = "Weight") },
                enabled = isEditable,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
            )
        }
    }
}

@Composable
private fun ColumnScope.WorkoutCreatorScreenLayout(
    headlineTextProvider: () -> String,
    listContent: LazyListScope.() -> Unit,
    bottomRowContent: @Composable RowScope.() -> Unit,
) {
    Text(
        text = headlineTextProvider(),
        modifier =
            Modifier
                .weight(0.1f)
                .padding(top = 6.dp),
    )

    LazyColumn(
        modifier =
            Modifier
                .weight(0.80f)
                .border(Dp.Hairline, Color.Black),
        content = listContent,
    )

    Row(modifier = Modifier.weight(0.1f), content = bottomRowContent)
}

@Composable
private fun ColumnScope.ExerciseSelectionList(
    availableExercisesProvider: () -> List<Exercise>,
    createdSetsProvider: () -> List<WorkoutSet>,
    workoutNameProvider: () -> String,
    onSelectExercise: (Exercise) -> Unit,
    onFinishWorkoutCreation: () -> Unit,
    onSetWorkoutName: (String) -> Unit,
) {
    val createdSets = createdSetsProvider()
    val headline = stringResource(R.string.select_exercise)

    WorkoutCreatorScreenLayout(
        headlineTextProvider = { headline },
        listContent = {
            items(availableExercisesProvider()) {
                ExerciseInfoCard(
                    it,
                    onSelectExercise,
                    createdSets.any { set -> set.exercise == it },
                )
            }
        },
        bottomRowContent = {
            val workoutName = workoutNameProvider()

            OutlinedTextField(
                modifier = Modifier.weight(0.7f),
                value = workoutName,
                onValueChange = onSetWorkoutName,
                placeholder = { Text("Workout name") },
            )

            Button(
                modifier = Modifier.weight(0.3f),
                onClick = onFinishWorkoutCreation,
                enabled = createdSets.isNotEmpty() && workoutName.isNotBlank(),
            ) {
                Text(text = stringResource(R.string.finalize_workout))
            }
        },
    )
}

@Composable
private fun ExerciseInfoCard(
    exercise: Exercise,
    onSelectExercise: (Exercise) -> Unit,
    alreadyInWorkout: Boolean,
) {
    val cardColors =
        if (alreadyInWorkout) {
            CardDefaults.elevatedCardColors()
        } else {
            CardDefaults.cardColors()
        }

    Card(
        onClick = {
            onSelectExercise(exercise)
        },
        colors = cardColors,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = exercise.name)
            Text(text = exercise.bodyArea.toString(), style = Typography.labelSmall)
        }
    }
}
