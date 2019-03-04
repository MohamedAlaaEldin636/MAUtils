package mohamedalaa.mautils.mautils.lifecycle_extensions;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import mohamedalaa.mautils.mautils_view_model.custom_classes.QuickMutableLiveData;
import mohamedalaa.mautils.mautils_view_model.extensions.FragmentActivityUtils;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/4/2019.
 */
public class YourActivityJava extends AppCompatActivity {

    class YourClass {
        MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

        public YourClass() {
            mutableLiveData.setValue("Initial Value");
        }
    }

    QuickMutableLiveData<String> quickMutableLiveData = new QuickMutableLiveData<>("Initial Value");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
