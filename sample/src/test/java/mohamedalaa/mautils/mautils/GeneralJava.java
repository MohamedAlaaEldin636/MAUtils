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

package mohamedalaa.mautils.mautils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.junit.Test;

import java.io.IOException;

import mohamedalaa.mautils.mautils.lifecycle_extensions.YourViewModel;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/5/2019.
 */
public class GeneralJava {

    @Test
    public void getTypeParam() {
        CheckTypeParam<YourViewModel> checkTypeParam = new CheckTypeParam<YourViewModel>(){};
        checkTypeParam.performPrintln();

        TypeAdapter ta = new TypeAdapter<Integer>(){
            @Override
            public void write(JsonWriter out, Integer value) throws IOException {

            }

            @Override
            public Integer read(JsonReader in) throws IOException {
                return null;
            }
        };
    }

}
