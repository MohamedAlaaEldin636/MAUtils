package mohamedalaa.mautils.mautils.recycler_view

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.my_rc_item.view.*
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.recycler_view.custom_classes.MAListRCAdapter
import mohamedalaa.mautils.recycler_view_data_binding.MAListRCAdapterDB
import mohamedalaa.mautils.recycler_view_data_binding.MAListRCAdapterMultipleDB

class RCAdapterFakeNames(dataList: List<String>, var layoutManager: LinearLayoutManager)
    : MAListRCAdapter<String>(dataList) {

    override fun getLayoutRes(): Int
        = if (layoutManager.orientation == LinearLayoutManager.VERTICAL) R.layout.my_rc_item else R.layout.my_rc_item_hz

    override fun onBindViewHolder(itemView: View, position: Int) {
        itemView.textView.text = dataList[position]

        itemView.setOnClickListener {
            removeItemAt(position)
        }
    }

}

class RCAdapterFakeNamesDB22(dataList: List<String>, var layoutManager: LinearLayoutManager)
    : MAListRCAdapterDB<String, ViewDataBinding>(dataList) {

    override fun onBindViewHolder(binding: ViewDataBinding, position: Int) {

    }

}

class RCAdapterFakeNamesDB(dataList: List<String>, var layoutManager: LinearLayoutManager)
    : MAListRCAdapterMultipleDB<String>(dataList) {

    override fun getLayoutRes(): Int {
        return super.getLayoutRes()
    }

    override fun onBindViewHolder(binding: ViewDataBinding, position: Int) {

    }

}