# MAUtils
[![](https://jitpack.io/v/MohamedAlaaEldin636/MAUtils.svg)](https://jitpack.io/#MohamedAlaaEldin636/MAUtils)
[![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)


# Library is in Progress (Alpha Development Stage)

- There are some modules which are stable (via stable releases of this library) so they have been tested and won't change but those are the ones only documented in README.md otherwise the rest are experimental (alpha release), so don't get confused when you see releases without the extension `-alpha.#` this yes means stable but for some modules not the whole library.  
- for a temp. in production use, Ex. Google Play publish you can use the stable modules but surely this might mean implementing other non-stable modules, So **MAKE THAT AS A LAST RESORT** until the whole library is stable.

# Contents [▴](#mautils)

- [Currently stable modules](#currently-stable-modules-)
    - [shared_pref_core module](#shared_pref_core-module-)
        - [Features](#features--shared_pref_core--)
        - [VIP Notes](#vip-notes--shared_pref_core--)
        - [Download](#download--shared_pref_core--)
        - [How to use](#how-to-use--shared_pref_core--)
            - [Simple Use Cases](#simple-use-cases--shared_pref_core--)
            - [Complex Use Cases](#complex-use-cases--shared_pref_core--)
            - [All Cases](#all-cases--shared_pref_core--)
    - [shared_pref_annotation & shared_pref_processor modules](#shared_pref_annotation--shared_pref_processor-modules-)
        - [Features](#features--shared_pref_annotation--shared_pref_processor--)
        - [Download](#download--shared_pref_annotation--shared_pref_processor--)
        - [How to use](#how-to-use--shared_pref_annotation--shared_pref_processor--) 
            - [Simple Use Cases](#simple-use-cases--shared_pref_annotation--shared_pref_processor--)
            - [Complex Use Cases](#complex-use-cases--shared_pref_annotation--shared_pref_processor--)
            - [All Cases](#all-cases--shared_pref_annotation--shared_pref_processor--)         
- [License](#license-)

# Currently stable modules [▴](#contents-)

- [shared_pref_core module](#shared_pref_core-module-)
- [shared_pref_annotation & shared_pref_processor modules](#shared_pref_annotation--shared_pref_processor-modules-)

## shared_pref_core module [▴](#currently-stable-modules-)

### Features ( shared_pref_core ) [▴](#shared_pref_core-module-)

1. for quicker coding it would be better instead of putString putInt to have one function name here is why `Context.sharedPrefSet` exists use it for all types, Also quicked than getting the `SharedPreferences` instance than edit then commit or apply changes all of that in one function with default parameter for common use cases so actually you only provide 3 things fileName, key and value. <br/>
`/*for Java*/ SharedPrefUtils.set`

2. Supports **Any Type** thanks to the other module in this library which is [gson module](#gson-module) module auto conversion will be done for you so you can save custom object to `SharedPreference` easily without making the conversion to/from String yourself. <br/>
Remember that's only in case the custom type is suitable for `SharedPreferences` otherwise for non-small sizes consider other options such as databases, files or cloud.

3. additonal functions are added for quick access instead of getting instance to `SharedPreferences` then performing edits for ex. clearing all keys removing secific key in the file or checking if a key exists you just provide the file's name and we do the rest but also all other parameters are optional for customizability so No trade-offs for the concise code. 

### VIP Notes ( shared_pref_core ) [▴](#shared_pref_core-module-)

- this modules better be used with `shared_pref_annotation` & `shared_pref_processor`
since these will enable you to generate with annotation and eliminate all the constant
strings for the shared pref file names and keys, it's highly recommended to use annotations
but this module is public instead of being internal in case of old projects which
don't need to migrate to annotations [Take a look](#shared_pref_annotation--shared_pref_processor-modules).

### Download ( shared_pref_core ) [▴](#shared_pref_core-module-)

- add below in module level gradle
``` groovy
dependencies {
      implementation 'com.github.MohamedAlaaEldin636.MAUtils:shared_pref_core:$mautils_version'
}
```

### How to use ( shared_pref_core ) [▴](#shared_pref_core-module-)

- [Simple Use Cases](#simple-use-cases--shared_pref_core--)
- [Complex Use Cases](#complex-use-cases--shared_pref_core--)
- [All Cases](#all-cases--shared_pref_core--)

#### Simple Use Cases ( shared_pref_core ) [▴](#how-to-use--shared_pref_core--)

``` kotlin
// No need for specific type name like putString
val string = "string"
context.sharedPrefSet("fileName", "key", string)
string == context.sharedPrefGet("fileName", "key", "") // true

// since Shared preference donot save null types then fallback 
// to delete the key if is null
val int: Int? = /*some code which might be null*/
context.sharedPrefSet("fileName", "key", int, removeKeyIfValueIsNull = true)
assertEquals(
    int,
    context.sharedPrefGet<Int?>("fileName", "key", null)
) // true
```

<details>
<summary><i><strong>Java Code</strong></i></summary>
<p>

``` java
// No need for specific type name like putString
String string = "string";
SharedPrefUtils.set(context, "fileName", "key", string, String.class);
string == SharedPrefUtils.get(context, "fileName", "k1", "", String.class); // true

// since Shared preference donot save null types then fallback
// to delete the key if is null
Integer someInt = /*some code which might be null*/
SharedPrefUtils.set(
    context, "fileName", "key", someInt, Integer.class, true /* removeKeyIfValueIsNull */
);
Integer newSomeInt = SharedPrefUtils.get(
    context, "fileName", "key", null, Integer.class
);
someInt == newSomeInt // true
```

</p>
</details>

#### Complex Use Cases ( shared_pref_core ) [▴](#how-to-use--shared_pref_core--)

``` kotlin
// Custom Type
data class Address(val fullDetails: String, val abbreviation: String)
data class Person(val name: String, val age: Int, val address: Address)
val listOfPerson = listOf(person1, person2)
context.sharedPrefSet(
    "fileName", 
    "key", 
    listOfPerson, 
    commit = true /* default to false meaning using .apply() to make the change */, 
    mode = Context.MODE_PRIVATE /* default value */
)
assertEquals(
    listOfPerson,
    context.sharedPrefGet("fileName", "key", emptyList())
) // true

// Custom type with nested type params, this as the `gson` module in this
// library states needs GsonConverter class so you can as well add it
// for the conversion so that ANY type can be saved in shared preferences
val value: Pair<Boolean, List<Pair<Int, Pair<Float, Double>>>> = true to listOf(
    5 to (6f to 5.6)
)
context.sharedPrefSet(
    fileName,
    "k3",
    value,
    gsonConverter = GsonConverterPairBooleanAndListPairIntAndPairFloatAndDouble()
)
```

#### All Cases ( shared_pref_core ) [▴](#how-to-use--shared_pref_core--)

``` kotlin
// clear all keys in a file
context.sharedPrefClearAll("fileName")

// remove 1 key
context.sharedPrefRemoveKey("fileName", "key")

// check if a key exists
context.sharedPrefHasKey("fileName", "key")
```

## shared_pref_annotation & shared_pref_processor modules [▴](#currently-stable-modules-)

### Features ( shared_pref_annotation & shared_pref_processor ) [▴](#shared_pref_annotation--shared_pref_processor-modules-)

1. eliminates the need of a lot of constants in your codebase to define your `SharedPreferences` files names and their keys now by an annotation a specific functions made accoding to that specific key so more concise code in generating and calling the `SharedPreferences` key/value pairs.

2. Since it's built on top of [shared_pref_core module](#shared_pref_core-module) it supports **Any Type** not just the types supported by `SharedPreferences` and in addition to that it supports manual conversion in case auto conversion by [gson module](#gson-module) of this library isn't fulfilling your needs.

3. Supports addional functions not just setter/getter for key/value pairs for ex. to have an on change listener for `SharedPreferences` you would need the file name and get an instance then register now a function will be created for you only if you require it in the annotation to register/unregister and a lot of other functions as well to have full control to do whatever suits your use case.

4. supports default value in annotation so when you call `Context.sharedPref${FileName}_GetName()` function to get a value of a key `name` for example there will be only optional arguments no required arguments needed for more concise and better and faster coding for you, also if you don't specify a default value then it will be auto created for you which is gonna be `0` for numbers, empty string for `String`, `false` for `Boolean` and `null` for custom types. 

5. supports java consumer code but with a very little limitation, it will work ok but to use the full potential of the module you will need to know some kotlin code.

### Download ( shared_pref_annotation & shared_pref_processor ) [▴](#shared_pref_annotation--shared_pref_processor-modules-)

- add below in module level gradle
``` groovy
// Note since generated code by annotation processor is kotlin code
// so below is needed even if you are a java developer for the generated
// code to work properly
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-kapt'
// ...
dependencies {
      implementation 'com.github.MohamedAlaaEldin636.MAUtils:shared_pref_core:$mautils_version'
      implementation 'com.github.MohamedAlaaEldin636.MAUtils:shared_pref_annotation:$mautils_version'
      kapt 'com.github.MohamedAlaaEldin636.MAUtils:shared_pref_processor:$mautils_version'
}
```

### How to use ( shared_pref_annotation & shared_pref_processor ) [▴](#shared_pref_annotation--shared_pref_processor-modules-)

- [Simple Use Cases](#simple-use-cases--shared_pref_annotation--shared_pref_processor--)
- [Complex Use Cases](#complex-use-cases--shared_pref_annotation--shared_pref_processor--)
- [All Cases](#all-cases--shared_pref_annotation--shared_pref_processor--)

#### Simple Use Cases ( shared_pref_annotation & shared_pref_processor ) [▴](#how-to-use--shared_pref_annotation--shared_pref_processor--)

``` kotlin
// have a key/value pair of type Boolean? ( auto default value false )
@MASharedPrefKeyValuePair(
    name = "keepScreenOn",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Boolean::class
        ]
    )
)
// have a key/value pair of type Set<String?> 
// ( nullable type param + special default value + support java consumer code )
@MASharedPrefKeyValuePair(
    name = "mySetOfStrings",
    defaultValue = "setOf()",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Set::class),
            MAKClass(String::class, true)
        ]
    ),
    supportJavaConsumerCode = MASharedPrefKeyValuePair.JavaConsumerCode.SUPPORT
)
class _SomeClassName

// which will generate the following code.

@Synchronized
fun Context.sharedPref_SomeClassName_SetKeepScreenOn(
    value: Boolean, commit: Boolean = false
): Boolean? = sharedPrefSet<Boolean>(
    privateFileName, 
    "keepScreenOn", 
    value, 
    false,
    Context.MODE_PRIVATE, 
    commit, 
    null
)
@Synchronized
fun Context.sharedPref_SomeClassName_GetKeepScreenOn(
    defValue: Boolean = false
): Boolean = sharedPrefGet<Boolean>(
    privateFileName, 
    "keepScreenOn", 
    defValue, 
    Context.MODE_PRIVATE,
    null
)

@JvmName("setMySetOfStrings")
@JvmOverloads
@Synchronized
fun Context.sharedPref_SomeClassName_SetMySetOfStrings(
    value: Set<String?>, commit: Boolean = false
): Boolean? = sharedPrefSet<Set<String?>>(
    privateFileName, 
    "mySetOfStrings", 
    value, 
    false, 
    Context.MODE_PRIVATE, 
    commit, 
    null
)
@JvmName("getMySetOfStrings")
@JvmOverloads
@Synchronized
fun Context.sharedPref_SomeClassName_GetMySetOfStrings(
    defValue: Set<String?> = setOf()
): Set<String?> = sharedPrefGet<Set<String?>>(
    privateFileName, 
    "mySetOfStrings", 
    defValue,
    Context.MODE_PRIVATE, 
    null
)
```

- Another Example

``` kotlin
// add import statements for custom types to use them,  
// add additional functions needed to used in your code.
@MASharedPrefFileConfigs(
    imports = [
        "mohamedalaa.mautils.sample.general_custom_classes.Person",
    ],
    addClearFun = true,
    addSharedPrefChangeListener = true,
)
// Custom type.
@MASharedPrefKeyValuePair(
    name = "personAutoConversion",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Person::class, true)
        ]
    )
)
class _SomeClassName

// which will generate the following code.

@Synchronized
fun Context.sharedPref_SomeClassName_clearAll(commit: Boolean = false): Boolean? =
    sharedPrefClearAll(privateFileName, Context.MODE_PRIVATE, commit)

fun Context.sharedPref_SomeClassName_registerSharedPrefChangeListener(
    listener: SharedPreferences.OnSharedPreferenceChangeListener
): Unit = sharedPref_SomeClassName_asSharedPreferences().registerOnSharedPreferenceChangeListener(listener)
fun Context.sharedPref_SomeClassName_unregisterSharedPrefChangeListener(
    listener: SharedPreferences.OnSharedPreferenceChangeListener
): Unit = sharedPref_SomeClassName_asSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener)

@Synchronized
fun Context.sharedPref_SomeClassName_SetPersonWithDefaultValue(
    value: Person, commit: Boolean = false
): Boolean? = sharedPrefSet<Person>(
    privateFileName, 
    "personWithDefaultValue", 
    value, 
    false, 
    Context.MODE_PRIVATE, 
    commit, 
    null
)
@Synchronized
fun Context.sharedPref_SomeClassName_GetPersonWithDefaultValue(
    defValue: Person = Person()
): Person = sharedPrefGet<Person>(
    privateFileName, 
    "personWithDefaultValue", 
    defValue,
    Context.MODE_PRIVATE, 
    null
)
```

#### Complex Use Cases ( shared_pref_annotation & shared_pref_processor ) [▴](#how-to-use--shared_pref_annotation--shared_pref_processor--)

- In case you don't know `SharedPreferences` doesn't save `null` for string value
it removes the key if it is a `null` value so to eliminate confusion and to benefit from the kotlin nullability features we by default have the setters accept non-null values and getters return non-null values but the annotation have parameters and conditions(in case type is null) to enable this behavior of remove the key if it is a nullable value and that is not like `SharedPreferences` for String & Set only but for any type.

``` kotlin
// in case of manual conversion
// we know there is auto conversion but what if want to have my own conversion
@MASharedPrefKeyValuePair(
    name = "personManualConversion",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Person::class, true)
        ]
    ),
    convertAnyToString = "toJsonOrNull()",
    convertStringToAny = "fromJsonOrNull()"
)
// what if my custom type has nested type params and since the auto conversion
// depend on `gson` module of this library a GsonConverter class will be needed
// Don't worry we got you covered just annotate that class with
// MASharedPrefGsonConverter
@MASharedPrefKeyValuePair(
    name = "nestedTypeParamWithGsonConverterConversion",
    defaultValue = "emptyList()",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            List::class, Pair::class, Pair::class, Int::class, Set::class, Float::class, String::class
        ]
    )
)
class _SomeClassName

// in .java file the annotated GsonConverter
@MASharedPrefGsonConverter
public class GsonConverterPairOfPairOfIntAndSetOfFloatAndString
    extends GsonConverter<List<Pair<Pair<Integer, Set<Float>>, String>>> {}

// both of the above classes will generate below code isa.

@Synchronized
fun Context.sharedPref_SomeClassName_SetPersonManualConversion(
    value: Person?, commit: Boolean = false
): Boolean? {
    val convertedValueAnyToString = value.run { toJsonOrNull() }
    return sharedPrefSet<String?>(
        privateFileName, 
        "personManualConversion",
        convertedValueAnyToString, 
        true, 
        Context.MODE_PRIVATE, 
        commit, 
        null
    )
}
@Synchronized
fun Context.sharedPref_SomeClassName_GetPersonManualConversion(
    defValue: Person? = null
): Person? {
    val convertedValueAnyToString = defValue.run { toJsonOrNull() }
    return sharedPrefGet<String?>(
        privateFileName, 
        "personManualConversion",
        convertedValueAnyToString, 
        Context.MODE_PRIVATE, 
        null
    ).run { 
        fromJsonOrNull() 
    }
}

@Synchronized
fun Context.sharedPref_SomeClassName_SetNestedTypeParamWithGsonConverterConversion(
    value: List<Pair<Pair<Int, Set<Float>>, String>>, commit: Boolean = false
): Boolean? = sharedPrefSet<List<Pair<Pair<Int, Set<Float>>, String>>>(
    privateFileName,
    "nestedTypeParamWithGsonConverterConversion", 
    value, 
    false, 
    Context.MODE_PRIVATE, 
    commit,
    GsonConverterPairOfPairOfIntAndSetOfFloatAndString()
)
@Synchronized
fun Context.sharedPref_SomeClassName_GetNestedTypeParamWithGsonConverterConversion(
    defValue: List<Pair<Pair<Int, Set<Float>>, String>> = emptyList()
): List<Pair<Pair<Int, Set<Float>>, String>> = sharedPrefGet<List<Pair<Pair<Int, Set<Float>>, String>>>(
    privateFileName,
    "nestedTypeParamWithGsonConverterConversion", 
    defValue, 
    Context.MODE_PRIVATE,
    GsonConverterPairOfPairOfIntAndSetOfFloatAndString()
)
```

#### All Cases ( shared_pref_annotation & shared_pref_processor ) [▴](#how-to-use--shared_pref_annotation--shared_pref_processor--)

``` kotlin

// Support java consumer code for functions created by fileConfigs annotation only
// also check the rest of the possible functions created by this annotation
@MASharedPrefFileConfigs(
    supportJavaConsumerCode = false,
    addFileNameFun = true,
    addSharedPrefInstanceFun = true,
    supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation = true
)
// Check manipulation with nullability isa.
@MASharedPrefKeyValuePair(
    name = "booleanWithNullableGetterOnly",
    defaultValue = "null", // since default for boolean is false
    // & I wanna check that it is not null anymore.
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Boolean::class
        ]
    ),
    supportGetterNullValue = true
    // no need to make type has nullable it will be done auto for you.
)
@MASharedPrefKeyValuePair(
    name = "booleanWithNullableSetterOnly",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Boolean::class
        ]
    ),
    supportSetterNullValue = true
)
@MASharedPrefKeyValuePair(
    name = "booleanWithNullableSetterAndGetter",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Boolean::class
        ]
    ),
    supportSetterAndGetterNullValues = true
)
class _SomeClassName

// Which will generate below code isa.

// from file configs annotation
object SharedPref_SomeClassName_NoContext {
    @JvmStatic
    fun fileName(): String = privateFileName
}

@JvmName("asSharedPreferences")
fun Context.sharedPref_SomeClassName_asSharedPreferences(): SharedPreferences =
        getSharedPreferences(privateFileName, Context.MODE_PRIVATE)
// from key/value pair annotation
@Synchronized
fun Context.sharedPref_SomeClassName_SetBooleanWithNullableGetterOnly(
    value: Boolean, commit: Boolean = false
): Boolean? = sharedPrefSet<Boolean>(
    privateFileName,
    "booleanWithNullableGetterOnly", 
    value, 
    false, 
    Context.MODE_PRIVATE, 
    commit, 
    null
)
@Synchronized
fun Context.sharedPref_SomeClassName_GetBooleanWithNullableGetterOnly(defValue: Boolean? = null):
    Boolean? = sharedPrefGet<Boolean?>(privateFileName, "booleanWithNullableGetterOnly",
    defValue, Context.MODE_PRIVATE, null)

@Synchronized
fun Context.sharedPref_SomeClassName_SetBooleanWithNullableSetterOnly(
    value: Boolean?, commit: Boolean = false
): Boolean? = sharedPrefSet<Boolean?>(
    privateFileName,
    "booleanWithNullableSetterOnly", 
    value, 
    true, 
    Context.MODE_PRIVATE, 
    commit, 
    null
)
@Synchronized
fun Context.sharedPref_SomeClassName_GetBooleanWithNullableSetterOnly(
    defValue: Boolean = false
): Boolean = sharedPrefGet<Boolean>(
    privateFileName, 
    "booleanWithNullableSetterOnly", 
    defValue,
    Context.MODE_PRIVATE, 
    null
)

@Synchronized
fun Context.sharedPref_SomeClassName_SetBooleanWithNullableSetterAndGetter(
    value: Boolean?, commit: Boolean = false
): Boolean? = sharedPrefSet<Boolean?>(
    privateFileName,
    "booleanWithNullableSetterAndGetter", 
    value, 
    true, 
    Context.MODE_PRIVATE, 
    commit, 
    null
)
@Synchronized
fun Context.sharedPref_SomeClassName_GetBooleanWithNullableSetterAndGetter(
    defValue: Boolean? = null
): Boolean? = sharedPrefGet<Boolean?>(
    privateFileName,
    "booleanWithNullableSetterAndGetter", 
    defValue, 
    Context.MODE_PRIVATE, 
    null
)
```

---

# [License](https://github.com/MohamedAlaaEldin636/MAUtils/blob/master/LICENSE.MD) [▴](#contents-)

## MAUtils is released under the [Apache 2.0 license](http://www.apache.org/licenses/).

```
Copyright (c) 2019 Mohamed Alaa

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
```

| Permissions         | Limitations           | Conditions   |
| ------------------- | --------------------- | ----------- |
| <img src="https://github.com/MohamedAlaaEldin636/VIP-Reminder-Capstone-Project/blob/master/forReadMeFiles/enable_use_icon.png" height="15" width="15"/> Commercial Use         | <img src="https://github.com/MohamedAlaaEldin636/VIP-Reminder-Capstone-Project/blob/master/forReadMeFiles/disable_use_icon.png" height="15" width="15"/> Trademark use | <img src="https://github.com/MohamedAlaaEldin636/VIP-Reminder-Capstone-Project/blob/master/forReadMeFiles/warning_icon.png" height="15" width="15"/> License and copyright notice |
| <img src="https://github.com/MohamedAlaaEldin636/VIP-Reminder-Capstone-Project/blob/master/forReadMeFiles/enable_use_icon.png" height="15" width="15"/> Modification           | <img src="https://github.com/MohamedAlaaEldin636/VIP-Reminder-Capstone-Project/blob/master/forReadMeFiles/disable_use_icon.png" height="15" width="15"/> Liability     |   <img src="https://github.com/MohamedAlaaEldin636/VIP-Reminder-Capstone-Project/blob/master/forReadMeFiles/warning_icon.png" height="15" width="15"/> State changes |
| <img src="https://github.com/MohamedAlaaEldin636/VIP-Reminder-Capstone-Project/blob/master/forReadMeFiles/enable_use_icon.png" height="15" width="15"/> Distribution           | <img src="https://github.com/MohamedAlaaEldin636/VIP-Reminder-Capstone-Project/blob/master/forReadMeFiles/disable_use_icon.png" height="15" width="15"/> Warranty      |    - |
| <img src="https://github.com/MohamedAlaaEldin636/VIP-Reminder-Capstone-Project/blob/master/forReadMeFiles/enable_use_icon.png" height="15" width="15"/> Patent use             | -         |   - |
| <img src="https://github.com/MohamedAlaaEldin636/VIP-Reminder-Capstone-Project/blob/master/forReadMeFiles/enable_use_icon.png" height="15" width="15"/> Private use            | -                 |  - |
