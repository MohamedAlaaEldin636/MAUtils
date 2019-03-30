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

package mohamedalaa.mautils.gson_annotation

/**
 * Used only for gson_processor library module isa.
 *
 * Name of generated code should be as follows isa,
 *
 * [package name of annotation].[prefixOfAllAnnotations].[Annotation class simple name]
 */
object GsonAnnotationConstants {

    private const val prefixOfAllAnnotations = "_Generated"

    val maSealedAbstractOrInterfaceJClass = MASealedAbstractOrInterface::class.java

    val generatedMASealedAbstractOrInterfacePackageName: String
        = maSealedAbstractOrInterfaceJClass.getPackage().name

    val generatedMASealedAbstractOrInterfaceSimpleName: String
        = prefixOfAllAnnotations + maSealedAbstractOrInterfaceJClass.simpleName

    val generatedMASealedAbstractOrInterfaceFullName: String
        = "$generatedMASealedAbstractOrInterfacePackageName.$generatedMASealedAbstractOrInterfaceSimpleName"

}