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

package mohamedalaa.mautils.sample_java_only_3;

import mohamedalaa.mautils.shared_pref_annotation.MAParameterizedKClass;
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefFileConfigs;
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair;

@MASharedPrefFileConfigs(
    supportJavaConsumerCode = true
)
@MASharedPrefKeyValuePair(
    name = "keepScreenOn",
    type = @MAParameterizedKClass(
        nonNullKClasses = {
            Boolean.class
        }
    )
)
public class _Settings {}
