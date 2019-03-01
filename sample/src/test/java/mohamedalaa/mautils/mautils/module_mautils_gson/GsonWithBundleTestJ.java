package mohamedalaa.mautils.mautils.module_mautils_gson;

import android.os.Bundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import mohamedalaa.mautils.mautils.fake_data.CustomObject;
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam;
import mohamedalaa.mautils.mautils_gson.GsonBundleUtils;
import mohamedalaa.mautils.mautils_gson.JGetterBundleGson;
import mohamedalaa.mautils.mautils_gson.java.GsonConverter;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/1/2019.
 */
@RunWith(RobolectricTestRunner.class)
public class GsonWithBundleTestJ {

    @Test
    public void nothing() {

    }

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
    public void customObjects() {
        Bundle bundle = GsonBundleUtils.buildBundleGson(customObject);

        JGetterBundleGson getterBundleGson = GsonBundleUtils.getJGetterBundleGson(bundle);

        CustomObject reCustomObject = getterBundleGson.get(CustomObject.class);

        Assert.assertEquals(customObject, reCustomObject);
    }

    @Test
    public void custom_withTypeParam() {
        Bundle bundle = GsonBundleUtils.buildBundleGson(customWithTypeParam, listOfCustomObject);

        JGetterBundleGson getterBundleGson = GsonBundleUtils.getJGetterBundleGson(bundle);

        GsonConverter<CustomWithTypeParam<CustomObject, Integer>> gsonConverter;
        gsonConverter = new GsonConverter<CustomWithTypeParam<CustomObject, Integer>>() {
        };
        CustomWithTypeParam<CustomObject, Integer> reCustomWithTypeParam
                = getterBundleGson.getWithConverter(gsonConverter);

        Assert.assertEquals(customWithTypeParam, reCustomWithTypeParam);
        Assert.assertEquals(listOfCustomObject, getterBundleGson.getWithConverter(
                new GsonConverter<List<CustomObject>>() {}));
    }

}
