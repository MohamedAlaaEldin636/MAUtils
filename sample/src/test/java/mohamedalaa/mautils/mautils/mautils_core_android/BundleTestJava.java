package mohamedalaa.mautils.mautils.mautils_core_android;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import mohamedalaa.mautils.core_android.BundleUtils;
import mohamedalaa.mautils.core_android.JGetterBundle;

import static org.junit.Assert.assertEquals;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/1/2019.
 */
@RunWith(RobolectricTestRunner.class)
public class BundleTestJava {

    private int[] primitiveIntArray = new int[2];
    private Long[] longArray = new Long[1];
    private List<String> stringList = new ArrayList<>();
    private SparseArray<Parcelable> sparseArray = new SparseArray<>();
    private List<Float> nullableElementsFloatList = new ArrayList<>();
    private List<Float> allNullableElementsFloatList = new ArrayList<>();

    @Before
    public void setups() {
        primitiveIntArray[0] = 4;
        primitiveIntArray[0] = 99;

        longArray[0] = 600L;

        stringList.add("s1");
        stringList.add("s2");
        stringList.add("s3");

        sparseArray.put(1, BundleUtils.buildBundle("abc"));

        nullableElementsFloatList.add(null);
        nullableElementsFloatList.add(4f);
        nullableElementsFloatList.add(3.5f);

        allNullableElementsFloatList.add(null);
        allNullableElementsFloatList.add(null);
    }

    @Test
    public void simulates_onSaveInstanceState_and_retrievingValues() {
        Bundle bundle = new Bundle();

        // On save instance state
        BundleUtils.addValues(bundle, primitiveIntArray, longArray, stringList, sparseArray);

        // Retrieving values ( Note must be in same order isa. )
        JGetterBundle getterBundle = BundleUtils.getJGetterBundle(bundle);
        int[] primitiveIntArray = getterBundle.getOrNull();
        assertEquals(primitiveIntArray, primitiveIntArray);
        //noinspection deprecation
        assertEquals(longArray, getterBundle.get());
        List<String> reStringList = getterBundle.get();
        assertEquals(stringList, reStringList);
        assertEquals(sparseArray, getterBundle.get());
    }

    @Test
    public void nullableElementsList() {
        Bundle bundle = BundleUtils.buildBundle(nullableElementsFloatList, allNullableElementsFloatList);

        JGetterBundle getterBundle = BundleUtils.getJGetterBundle(bundle);
        assertEquals(nullableElementsFloatList, getterBundle.get());
        assertEquals(allNullableElementsFloatList, getterBundle.get());
    }
}
