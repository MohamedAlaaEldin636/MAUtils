package mohamedalaa.mautils.mautils.lifecycle_extensions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import mohamedalaa.mautils.mautils_view_model.extensions.getViewModel

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 3/4/2019.
 *
 */
class YourActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inside your activity
        //val yourViewModel = getViewModel<YourViewModel>()

        // Instead of
        //val yourViewModel = ViewModelProviders.of(this).get(YourViewModel::class.java)

    }

}