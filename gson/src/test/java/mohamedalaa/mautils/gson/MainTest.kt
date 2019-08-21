/*
 * Copyright (c) 2019 Mohamed Alaa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package mohamedalaa.mautils.gson

/*import mohamedalaa.mautils.sample.custom_classes.helper_classes.GameEstimationFastRoundsSort
import mohamedalaa.mautils.sample.custom_classes.helper_classes.GameEstimationRoundsConfiguration
import mohamedalaa.mautils.sample.custom_classes.helper_classes.GameEstimationType
import mohamedalaa.mautils.sample.custom_classes.helper_classes.GameTrumpSuit*/
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class MainTest {
/*

    @Test
    fun severalTests() {
        val t2 = GameTrumpSuit.NoTrump
        val j2 = t2.toJson<GameTrumpSuit>()
        val r2 = j2.fromJson<GameTrumpSuit>()

        assertEquals(r2, GameTrumpSuit.NoTrump)

        val t1 = GameEstimationRoundsConfiguration(
            2,
            2,
            GameTrumpSuit.NoTrump,
            GameEstimationFastRoundsSort.ASC
        )
        val j1 = t1.toJson()
        val r1 = j1.fromJson<GameEstimationRoundsConfiguration>()

        assertEquals(r1.fastRoundsStarter, GameTrumpSuit.NoTrump)

        val t31 = GameEstimationType.CustomBola(0, "name", GameEstimationRoundsConfiguration(
            2,
            4,
            GameTrumpSuit.NoTrump,
            GameEstimationFastRoundsSort.RANDOM
        ))
        val j31 = t31.toJson<GameEstimationType.CustomBola>()
        val r31 = j31.fromJson<GameEstimationType.CustomBola>()

        assertEquals(
            r31.gameEstimationRoundsConfiguration.fastRoundsStarter,
            GameTrumpSuit.NoTrump
        )

        val t3 = GameEstimationType.CustomBola(0, "name", GameEstimationRoundsConfiguration(
            2,
            4,
            GameTrumpSuit.NoTrump,
            GameEstimationFastRoundsSort.RANDOM
        ))
        val j3 = t3.toJson<GameEstimationType>()
        val r3 = j3.fromJson<GameEstimationType>()

        println(j3)
        assertEquals(r3, t3)
        assertEquals(
            r3.gameEstimationRoundsConfiguration.fastRoundsStarter,
            GameTrumpSuit.NoTrump
        )
    }
*/

}
