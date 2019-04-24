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

package mohamedalaa.mautils.gson_processor.del_later

import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
/*
https://stackoverflow.com/questions/35918511/get-static-field-value-using-annotation-processing

https://stackoverflow.com/questions/17660469/get-field-class-in-annotations-processor
 */
private lateinit var varElement: VariableElement
private fun a1(annotations: Set<TypeElement>, roundEnv: RoundEnvironment) {
    varElement.constantValue // return primitive or string isa -> so perfect for shared pref isa.
}