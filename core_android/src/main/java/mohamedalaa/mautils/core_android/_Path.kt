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
fun Path.moveThenLineTo(firstPointF: PointF, secondPointF: PointF) {
    moveTo(firstPointF.x, firstPointF.y)
    lineTo(secondPointF.x, secondPointF.y)
}