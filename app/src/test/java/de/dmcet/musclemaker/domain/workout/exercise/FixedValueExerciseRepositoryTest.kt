package de.dmcet.musclemaker.domain.workout.exercise

import de.dmcet.musclemaker.domain.workout.api.Exercise
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class FixedValueExerciseRepositoryTest {
    @Test
    fun `availableExercises must return a list of exercises`() =
        runTest {
            val uut = FixedValueExerciseRepository()

            uut.knownExercises.collectLatest {
                Assert.assertEquals(48, it.size)
            }
        }

    @Test
    fun `availableExercises returns the same list list every time when collected`() =
        runTest {
            val uut = FixedValueExerciseRepository()

            var list: List<Exercise>? = null

            uut.knownExercises.collectLatest {
                list = it
                Assert.assertNotNull(list)
                Assert.assertEquals(48, list!!.size)
            }

            uut.knownExercises.collectLatest {
                Assert.assertEquals(list, it)
            }
        }
}
