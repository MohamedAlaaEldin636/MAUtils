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

package mohamedalaa.mautils.sample.custom_classes.helper_classes

/**
 * @param fastRoundsStarter Starter round of these fast rounds [GameTrumpSuit] if null then it means it will be random isa.
 */
data class GameEstimationRoundsConfiguration(
    var normalRoundsNumber: Int,

    var fastRoundsNumber: Int,

    var fastRoundsStarter: GameTrumpSuit?,

    var gameFastRoundsSort: GameEstimationFastRoundsSort
)
