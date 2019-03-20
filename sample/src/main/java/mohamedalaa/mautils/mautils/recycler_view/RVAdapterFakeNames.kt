package mohamedalaa.mautils.mautils.recycler_view

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.my_rc_item.view.*
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.recycler_view.custom_classes.MAListRVAdapter
import mohamedalaa.mautils.recycler_view.custom_classes.MAListRVAdapterDB
import mohamedalaa.mautils.recycler_view.custom_classes.MAListRVAdapterMultiDB

class RVAdapterFakeNames(dataList: List<String>, var layoutManager: LinearLayoutManager)
    : MAListRVAdapter<String>(dataList) {

    override fun getLayoutRes(): Int
        = if (layoutManager.orientation == LinearLayoutManager.VERTICAL) R.layout.my_rc_item else R.layout.my_rc_item_hz

    override fun onBindViewHolder(itemView: View, position: Int) {
        itemView.textView.text = dataList[position]

        itemView.setOnClickListener {
            removeItemAt(position)
        }
    }

}

class RVAdapterFakeNamesDB22(dataList: List<String>, var layoutManager: LinearLayoutManager)
    : MAListRVAdapterDB<String, ViewDataBinding>(dataList, R.layout.my_rc_item) {

    override fun onBindViewHolder(binding: ViewDataBinding, position: Int) {

    }

}

class RVAdapterFakeNamesDB(dataList: List<String>, var layoutManager: LinearLayoutManager)
    : MAListRVAdapterMultiDB<String>(dataList) {

    override fun getLayoutRes(): Int {
        return super.getLayoutRes()
    }

    override fun onBindViewHolder(binding: ViewDataBinding, position: Int) {

    }

}