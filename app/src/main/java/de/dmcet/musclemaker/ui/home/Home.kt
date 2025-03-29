package de.dmcet.musclemaker.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.dmcet.musclemaker.R
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

@Composable
fun Home(
    onStartWorkout: () -> Unit,
    onManageWorkouts: () -> Unit,
    onSeeHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val buttonModifier =
            Modifier
                .fillMaxWidth()
                .padding(5.dp)

        Image(
            painter = painterResource(R.drawable.musclemaker_splash),
            contentDescription = "",
        )

        FunctionNavigationButton(
            modifier = buttonModifier,
            functionNameResource = R.string.start_workout,
            onClick = onStartWorkout,
            functionIcon = Icons.Default.PlayArrow,
        )

        FunctionNavigationButton(
            modifier = buttonModifier,
            functionNameResource = R.string.manage_workouts,
            onClick = onManageWorkouts,
            functionIcon = Icons.Default.Create,
        )

        FunctionNavigationButton(
            modifier = buttonModifier,
            functionNameResource = R.string.see_history,
            onClick = onSeeHistory,
            functionIcon = Icons.Default.DateRange,
        )
    }
}

@Composable
private fun FunctionNavigationButton(
    @StringRes functionNameResource: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    functionIcon: ImageVector? = null,
) {
    val functionName = stringResource(functionNameResource)
    Button(
        modifier = modifier,
        onClick = {
            logger.info { "navigating to function $functionName" }
            onClick()
        },
    ) {
        functionIcon?.let {
            Icon(
                functionIcon,
                contentDescription = null,
                modifier = Modifier.padding(end = 5.dp),
            )
        }

        Text(functionName)
    }
}
