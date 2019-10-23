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

package mohamedalaa.mautils.core_android_processor.extensions;

import java.lang.annotation.Annotation;

import mohamedalaa.mautils.core_android_annotation.MASharedPrefField_Configs;

public class MASharedPrefField_ConfigsUtils {

    public static MASharedPrefField_Configs getDefaultInstanceOfAnnotation(){
        return new MASharedPrefField_Configs() {
            @Override
            public boolean addClearFun() {
                return false;
            }

            @Override
            public boolean addFileNameFun() {
                return false;
            }

            @Override
            public String[] imports() {
                return new String[0];
            }

            @Override
            public boolean supportJavaConsumerCode() {
                return false;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return MASharedPrefField_Configs.class;
            }
        };
    }

}
