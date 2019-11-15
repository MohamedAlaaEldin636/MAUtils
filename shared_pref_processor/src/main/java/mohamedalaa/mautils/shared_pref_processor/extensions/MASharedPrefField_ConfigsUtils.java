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

package mohamedalaa.mautils.shared_pref_processor.extensions;

import java.lang.annotation.Annotation;

import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefFileConfigs;


public class MASharedPrefField_ConfigsUtils {

    public static MASharedPrefFileConfigs getDefaultInstanceOfAnnotation(){
        return new MASharedPrefFileConfigs() {
            @Override
            public boolean addClearFun() {
                return false;
            }

            @Override
            public boolean addFileNameFun() {
                return false;
            }

            @Override
            public boolean addSharedPrefInstanceFun() {
                return false;
            }

            @Override
            public boolean addSharedPrefChangeListener() {
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
            public boolean supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation() {
                return false;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return MASharedPrefFileConfigs.class;
            }
        };
    }

}
