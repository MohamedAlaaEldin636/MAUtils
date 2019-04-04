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

package mohamedalaa.mautils.gson_processor.utils

/**
 * The thought below is not done since it's execution will need the following
 * add kotlin reflection is mandatory to work
 * a lot of code added in gson_annotation
 * a little long reflection one time only before gson serialization
 *
 * so that is not needed at all, so ignore below but keep it just in case in future found a better
 * way for it isa.
 */

/*
boolean includeSelf() default true;
boolean checkSelfTypeParams() default false;

boolean checkDeclaredProperties() default false;
boolean checkNestedDeclaredProperties() default false;
boolean checkAllDeclaredPropertiesTypeParams() default false;

boolean excludeInterfaces() default false;
boolean excludeAbstractClasses() default false;
boolean excludeSealedClasses() default false;
Class<?>[] excludeClasses() default {};
 */