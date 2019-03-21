package mohamedalaa.mautils.mautils.del_later_for_readme;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import mohamedalaa.mautils.core_android.BundleUtils;
import mohamedalaa.mautils.core_android.ContextUtils;
import mohamedalaa.mautils.core_android.JGetterBundle;
import mohamedalaa.mautils.core_android.SoftKeyboardUtils;
import mohamedalaa.mautils.mautils.R;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/20/2019.
 */
public class TrialActivity extends AppCompatActivity {

    private Context context = this;
    private EditText editText;

    public void f1() {
        // Hide keyboard
        SoftKeyboardUtils.hideKeyboardFrom(context, editText/*, false optional clearFocus param*/);

        // Force Show keyboard
        SoftKeyboardUtils.showKeyboardFor(context, editText/*, false optional requestFocus param*/);

        // Quick inflation of a layout.xml
        //View rootViewOfLayout = ContextUtils.inflateLayout(R.layout.layout/*, parent optional viewGroup, false optional attachToParent*/);
    }

    /*int intVal;
    String stringVal;
    double doubleVal;
    float[] floatArray;

    // Add all variables to Bundle immediately instead of creating keys for it.
    // add any objects that is supported by Bundle
    @Override
    public void onSaveInstanceState(Bundle outState) {
        BundleUtils.addValues(outState,
            intVal,
            stringVal,
            doubleVal,
            floatArray
        );

        super.onSaveInstanceState(outState);
    }

    // But when you retrieve it only limitation is
    // it MUST be retrieved in same order
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // ...
        JGetterBundle getterBundle = BundleUtils.getJGetterBundle(savedInstanceState);

        intVal = getterBundle.get();
        stringVal = getterBundle.getOrNull();
        doubleVal = getterBundle.get();
        floatArray = getterBundle.get();
    }*/

}
