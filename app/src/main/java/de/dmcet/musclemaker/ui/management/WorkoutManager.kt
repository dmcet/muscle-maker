package de.dmcet.musclemaker.ui.management

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.dmcet.musclemaker.R
import de.dmcet.musclemaker.domain.workout.WorkoutPlan
import de.dmcet.musclemaker.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun WorkoutManager(
    onAddNewWorkout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutManagerViewModel = koinViewModel(),
) {
    val workouts by viewModel.workouts.collectAsState(emptyList())

    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
        if (workouts.isNotEmpty()) {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .weight(0.9f),
            ) {
                items(workouts) {
                    WorkoutOverviewCard(it)
                }
            }
        }

        Button(
            onClick = onAddNewWorkout,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Default.Add, null)
                Text(text = stringResource(R.string.add_workout))
            }
        }
    }
}

@Composable
private fun WorkoutOverviewCard(
    workoutPlan: WorkoutPlan,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
    ) {
        val paddingModifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 3.dp)

        Text(text = workoutPlan.name, modifier = paddingModifier)
        WorkoutDetailLine(
            R.string.exercises,
            workoutPlan.sets
                .distinctBy { it.exercise }
                .size
                .toString(),
            paddingModifier,
        )

        WorkoutDetailLine(
            R.string.total_weight,
            "${workoutPlan.totalWeight()} kg",
            paddingModifier,
        )
        WorkoutDetailLine(
            R.string.areas,
            workoutPlan.sets
                .map { it.exercise.bodyArea }
                .distinct()
                .joinToString(", "),
            paddingModifier,
        )
    }
}

private fun WorkoutPlan.totalWeight() = sets.sumOf { it.weight * it.reps }

@Composable
private fun WorkoutDetailLine(
    @StringRes label: Int,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = stringResource(label), style = Typography.labelSmall)
        Text(
            text = value,
            style = Typography.labelSmall,
        )
    }
}
