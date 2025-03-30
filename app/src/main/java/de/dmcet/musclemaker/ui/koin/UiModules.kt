package de.dmcet.musclemaker.ui.koin

import de.dmcet.musclemaker.ui.management.WorkoutCreatorViewModel
import de.dmcet.musclemaker.ui.management.WorkoutManagerViewModel
import de.dmcet.musclemaker.ui.management.api.WorkoutCreatorViewModelApi
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule =
    module {
        viewModel { WorkoutManagerViewModel(get()) }
        viewModel<WorkoutCreatorViewModelApi> { WorkoutCreatorViewModel(get(), get()) }
    }
