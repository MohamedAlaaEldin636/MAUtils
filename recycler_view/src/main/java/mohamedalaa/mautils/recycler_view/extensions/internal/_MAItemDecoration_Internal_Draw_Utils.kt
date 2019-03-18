@file:Suppress("unused")

package mohamedalaa.mautils.recycler_view.extensions.internal

import android.graphics.Path
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.custom_classes.MAItemDecoration

internal fun MAItemDecoration.addTopPath(
    list: MutableList<Path>,
    child: View,
    bounds: Rect,
    layoutManager: RecyclerView.LayoutManager
) {
    val path = Path()

    // Top left point
    var x = child.translationX + bounds.left + layoutManager.getLeftDecorationWidth(child)
    var y = child.translationY + bounds.top
    path.moveTo(x, y)

    val decorationThickness = layoutManager.getTopDecorationHeight(child).apply { if (this == 0) return }

    x += child.width
    path.lineTo(x, y)

    y += decorationThickness
    path.lineTo(x, y)

    x -= child.width
    path.lineTo(x, y)

    y -= decorationThickness
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun MAItemDecoration.addBottomPath(
    list: MutableList<Path>,
    child: View,
    bounds: Rect,
    layoutManager: RecyclerView.LayoutManager
) {
    val path = Path()

    // Bottom left point
    var x = child.translationX + bounds.left + layoutManager.getLeftDecorationWidth(child)
    var y = child.translationY + bounds.bottom
    path.moveTo(x, y)

    val decorationThickness = layoutManager.getBottomDecorationHeight(child).apply { if (this == 0) return }

    y -= decorationThickness
    path.lineTo(x, y)

    x += child.width
    path.lineTo(x, y)

    y += decorationThickness
    path.lineTo(x, y)

    x -= child.width
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun MAItemDecoration.addLeftPath(
    list: MutableList<Path>,
    child: View,
    bounds: Rect,
    layoutManager: RecyclerView.LayoutManager
) {
    val path = Path()

    // Top left point
    var x = child.translationX + bounds.left
    var y = child.translationY + bounds.top + layoutManager.getTopDecorationHeight(child)
    path.moveTo(x, y)

    val decorationThickness = layoutManager.getLeftDecorationWidth(child).apply { if (this == 0) return }

    x += decorationThickness
    path.lineTo(x, y)

    y += child.height
    path.lineTo(x, y)

    x -= decorationThickness
    path.lineTo(x, y)

    y -= child.height
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun MAItemDecoration.addRightPath(
    list: MutableList<Path>,
    child: View,
    bounds: Rect,
    layoutManager: RecyclerView.LayoutManager
) {
    val path = Path()

    // Top right point
    var x = child.translationX + bounds.right
    var y = child.translationY + bounds.top + layoutManager.getTopDecorationHeight(child)
    path.moveTo(x, y)

    val decorationThickness = layoutManager.getRightDecorationWidth(child).apply { if (this == 0) return }.toFloat()

    y += child.height
    path.lineTo(x, y)

    x -= decorationThickness
    path.lineTo(x, y)

    y -= child.height
    path.lineTo(x, y)

    x += decorationThickness
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun MAItemDecoration.addTopRightCornerPath(
    list: MutableList<Path>,
    child: View,
    bounds: Rect,
    layoutManager: RecyclerView.LayoutManager
) {
    val path = Path()

    // Top right point
    var x = child.translationX + bounds.right
    var y = child.translationY + bounds.top
    path.moveTo(x, y)

    val topDecoration = layoutManager.getTopDecorationHeight(child)
    val rightDecoration = layoutManager.getRightDecorationWidth(child)

    y += topDecoration
    path.lineTo(x, y)

    x -= rightDecoration
    path.lineTo(x, y)

    y -= topDecoration
    path.lineTo(x, y)

    x += rightDecoration
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun MAItemDecoration.addTopLeftCornerPath(
    list: MutableList<Path>,
    child: View,
    bounds: Rect,
    layoutManager: RecyclerView.LayoutManager) {
    val path = Path()

    // Top left point
    var x = child.translationX + bounds.left
    var y = child.translationY + bounds.top
    path.moveTo(x, y)

    val topDecoration = layoutManager.getTopDecorationHeight(child)
    val leftDecoration = layoutManager.getLeftDecorationWidth(child)

    x += leftDecoration
    path.lineTo(x, y)

    y += topDecoration
    path.lineTo(x, y)

    x -= leftDecoration
    path.lineTo(x, y)

    y -= topDecoration
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun MAItemDecoration.addBottomRightCornerPath(
    list: MutableList<Path>,
    child: View,
    bounds: Rect,
    layoutManager: RecyclerView.LayoutManager
) {
    val path = android.graphics.Path()

    // Bottom right point
    var x = child.translationX + bounds.right
    var y = child.translationY + bounds.bottom
    path.moveTo(x, y)

    val bottomDecoration = layoutManager.getBottomDecorationHeight(child)
    val rightDecoration = layoutManager.getRightDecorationWidth(child)

    x -= rightDecoration
    path.lineTo(x, y)

    y -= bottomDecoration
    path.lineTo(x, y)

    x += rightDecoration
    path.lineTo(x, y)

    y += bottomDecoration
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun MAItemDecoration.addBottomLeftCornerPath(
    list: MutableList<Path>,
    child: View,
    bounds: Rect,
    layoutManager: RecyclerView.LayoutManager
) {
    val path = Path()

    // Bottom left point
    var x = child.translationX + bounds.left
    var y = child.translationY + bounds.bottom
    path.moveTo(x, y)

    val bottomDecoration = layoutManager.getBottomDecorationHeight(child)
    val leftDecoration = layoutManager.getLeftDecorationWidth(child)

    y -= bottomDecoration
    path.lineTo(x, y)

    x += leftDecoration
    path.lineTo(x, y)

    y += bottomDecoration
    path.lineTo(x, y)

    x -= leftDecoration
    path.lineTo(x, y)

    path.close()

    list += path
}