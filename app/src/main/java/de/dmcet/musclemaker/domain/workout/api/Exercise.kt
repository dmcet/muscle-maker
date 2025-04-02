package de.dmcet.musclemaker.domain.workout.api

enum class BodyArea {
    Shoulders,
    Chest,
    Back,
    Legs,
    Arms,
    Abs,
}

data class Exercise(
    val name: String,
    val bodyArea: BodyArea,
) {
    companion object {
        val None = Exercise("None", BodyArea.Chest)
    }
}
