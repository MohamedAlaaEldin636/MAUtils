package mohamedalaa.mautils.recycler_view.extensions.internal

import android.graphics.Path
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.custom_classes.RCItemDecoration

internal fun RCItemDecoration.addTopPath(list: MutableList<Path>, child: View, bounds: Rect, layoutManager: RecyclerView.LayoutManager) {
    val path = Path()

    // Top left point
    var x = child.translationX + bounds.left + layoutManager.getLeftDecorationWidth(child)
    var y = child.translationY + bounds.top
    path.moveTo(x, y)

    x += child.width
    path.lineTo(x, y)

    y += fullDimen
    path.lineTo(x, y)

    x -= child.width
    path.lineTo(x, y)

    y -= fullDimen
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun RCItemDecoration.addBottomPath(list: MutableList<Path>, child: View, bounds: Rect, layoutManager: RecyclerView.LayoutManager) {
    val path = Path()

    // Bottom left point
    var x = child.translationX + bounds.left + layoutManager.getLeftDecorationWidth(child)
    var y = child.translationY + bounds.bottom
    path.moveTo(x, y)

    y -= fullDimen
    path.lineTo(x, y)

    x += child.width
    path.lineTo(x, y)

    y += fullDimen
    path.lineTo(x, y)

    x -= child.width
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun RCItemDecoration.addRightPath(list: MutableList<Path>, child: View, bounds: Rect, layoutManager: RecyclerView.LayoutManager) {
    val path = Path()

    // Top right point
    var x = child.translationX + bounds.right
    var y = child.translationY + bounds.top + layoutManager.getTopDecorationHeight(child)
    path.moveTo(x, y)

    y += child.height
    path.lineTo(x, y)

    x -= fullDimen
    path.lineTo(x, y)

    y -= child.height
    path.lineTo(x, y)

    x += fullDimen
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun RCItemDecoration.addLeftPath(list: MutableList<Path>, child: View, bounds: Rect, layoutManager: RecyclerView.LayoutManager) {
    val path = Path()

    // Top left point
    var x = child.translationX + bounds.left
    var y = child.translationY + bounds.top + layoutManager.getTopDecorationHeight(child)
    path.moveTo(x, y)

    x += fullDimen
    path.lineTo(x, y)

    y += child.height
    path.lineTo(x, y)

    x -= fullDimen
    path.lineTo(x, y)

    y -= child.height
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun RCItemDecoration.addTopRightCornerPath(list: MutableList<Path>, child: View, bounds: Rect) {
    val path = Path()

    // Top right point
    var x = child.translationX + bounds.right
    var y = child.translationY + bounds.top
    path.moveTo(x, y)

    y += fullDimen
    path.lineTo(x, y)

    x -= fullDimen
    path.lineTo(x, y)

    y -= fullDimen
    path.lineTo(x, y)

    x += fullDimen
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun RCItemDecoration.addTopLeftCornerPath(list: MutableList<Path>, child: View, bounds: Rect) {
    val path = Path()

    // Top left point
    var x = child.translationX + bounds.left
    var y = child.translationY + bounds.top
    path.moveTo(x, y)

    x += fullDimen
    path.lineTo(x, y)

    y += fullDimen
    path.lineTo(x, y)

    x -= fullDimen
    path.lineTo(x, y)

    y -= fullDimen
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun RCItemDecoration.addBottomRightCornerPath(list: MutableList<Path>, child: View, bounds: Rect) {
    val path = Path()

    // Bottom right point
    var x = child.translationX + bounds.right
    var y = child.translationY + bounds.bottom
    path.moveTo(x, y)

    x -= fullDimen
    path.lineTo(x, y)

    y -= fullDimen
    path.lineTo(x, y)

    x += fullDimen
    path.lineTo(x, y)

    y += fullDimen
    path.lineTo(x, y)

    path.close()

    list += path
}

internal fun RCItemDecoration.addBottomLeftCornerPath(list: MutableList<Path>, child: View, bounds: Rect) {
    val path = Path()

    // Bottom left point
    var x = child.translationX + bounds.left
    var y = child.translationY + bounds.bottom
    path.moveTo(x, y)

    y -= fullDimen
    path.lineTo(x, y)

    x += fullDimen
    path.lineTo(x, y)

    y += fullDimen
    path.lineTo(x, y)

    x -= fullDimen
    path.lineTo(x, y)

    path.close()

    list += path
}