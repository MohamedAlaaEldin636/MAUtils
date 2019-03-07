package mohamedalaa.mautils.recycler_view.extensions

import androidx.recyclerview.widget.RecyclerView

val RecyclerView.itemDecorations: List<RecyclerView.ItemDecoration>
    get() = (0 until itemDecorationCount).map {
        getItemDecorationAt(it)
    }

inline fun RecyclerView.forEachItemDecorations(action: (RecyclerView.ItemDecoration) -> Unit) {
    for (element in itemDecorations) action(element)
}