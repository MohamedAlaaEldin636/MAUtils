/*
 * Copyright (c) 2019 Mohamed Alaa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package mohamedalaa.mautils.sample.module_mautils_gson;

import android.os.Bundle;

import org.junit.Test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import mohamedalaa.mautils.gson.GsonBundleUtils;
import mohamedalaa.mautils.gson.JGetterBundleGson;
import mohamedalaa.mautils.sample.fake_data.CustomObject;
import mohamedalaa.mautils.sample.fake_data.CustomWithTypeParam;

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
        JGetterBundleGson getterBundleGson = GsonBundleUtils.getterBundleGson(bundle);

        CustomObject re = getterBundleGson.get(CustomObject.class);

        System.out.println(customObject);
        System.out.println(re);
    }
}
