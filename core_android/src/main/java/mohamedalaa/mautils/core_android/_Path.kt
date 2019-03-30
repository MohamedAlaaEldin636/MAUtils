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

@file:JvmName("PathUtils")

package mohamedalaa.mautils.core_android

import android.graphics.Path
import android.graphics.PointF

/**
 * Performs [Path.moveTo] with [firstX] & [firstY], the [Path.lineTo] with [secondX] & [secondY] isa.
 */
fun Path.moveThenLineTo(firstX: Float, firstY: Float, secondX: Float, secondY: Float) {
    moveTo(firstX, firstY)
    lineTo(secondX, secondY)
}

/**
 * Performs [Path.moveTo] with [firstPointF], the [Path.lineTo] with [secondPointF] isa.
 */
fun Path.moveThenLineTo(firstPointF: PointF, secondPointF: PointF)
    = moveThenLineTo(firstPointF.x, firstPointF.y, secondPointF.x, secondPointF.y)