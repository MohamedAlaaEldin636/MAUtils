package mohamedalaa.mautils.material_design.binding_adapter

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

/** app:tabLayout_ */
object TabLayout {


    @BindingAdapter("app:tabLayout_viewPager")
    fun setupWithViewPager(tabLayout: TabLayout, viewPager: ViewPager?) {
        viewPager?.apply { tabLayout.setupWithViewPager(this) }
    }


}