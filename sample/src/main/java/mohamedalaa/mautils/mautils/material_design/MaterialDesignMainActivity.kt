package mohamedalaa.mautils.mautils.material_design

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.mautils.databinding.ActivityMaterialDesignMainBinding

class MaterialDesignMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMaterialDesignMainBinding>(this, R.layout.activity_material_design_main)
    }
}
