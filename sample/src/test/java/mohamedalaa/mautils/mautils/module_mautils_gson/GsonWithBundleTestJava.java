package mohamedalaa.mautils.mautils.module_mautils_gson;

import android.os.Bundle;

import org.junit.Test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import mohamedalaa.mautils.gson.GsonBundleUtils;
import mohamedalaa.mautils.gson.JGetterBundleGson;
import mohamedalaa.mautils.mautils.fake_data.CustomObject;
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam;

@RunWith(RobolectricTestRunner.class)
public class GsonWithBundleTestJava {

    private CustomObject customObject;
    private CustomWithTypeParam<CustomObject, Integer> customWithTypeParam;
    private List<CustomObject> listOfCustomObject;

    @Before
    public void setups() {
        customObject = new CustomObject();

        customWithTypeParam = new CustomWithTypeParam<>();

        ArrayList<String> stringList1 = new ArrayList<>();
        stringList1.add("sub f1 1");

        ArrayList<String> stringList2 = new ArrayList<>();
        stringList1.add("sub f2 1");
        stringList1.add("sub f2 2");

        listOfCustomObject = new ArrayList<>();
        listOfCustomObject.add(new CustomObject());
        listOfCustomObject.add(new CustomObject("n1", 1, "a1", stringList1));
        listOfCustomObject.add(new CustomObject("n2", 2, "a2", stringList2));
    }

    @Test
    public void custom_objects() {
        Bundle bundle = GsonBundleUtils.buildBundleGson(customObject);
        JGetterBundleGson getterBundleGson = GsonBundleUtils.getJGetterBundleGson(bundle);

        CustomObject re = getterBundleGson.get(CustomObject.class);

        System.out.println(customObject);
        System.out.println(re);
    }
}
