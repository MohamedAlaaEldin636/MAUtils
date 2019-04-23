# MAUtils
[![](https://jitpack.io/v/MohamedAlaaEldin636/MAUtils.svg)](https://jitpack.io/#MohamedAlaaEldin636/MAUtils)
[![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)


# Library is In Progress (alpha release)

This library is about general utilities, everytime I run into
a problem that can be a common use case I will add it in this library
so no need in future to re-invent the wheel OR re-build known boiler-plate code

---

## Initial Setup

### Jitpack Environment

in your Gradle **Top-level build file** add below code

``` groovy
allprojects {
    repositories {
    	// ...
    	maven { url 'https://jitpack.io' }
    }
}
```

### Library Version

Ensure having mautils_version defined in your Gradle **Top-level build file** as below

``` groovy
buildscript {
    ext.mautils_version = '1.0.0-alpha.6'
}    
```

_**OR**_ in your gradle **app module** as below

``` groovy
dependencies {
    def mautils_version = '1.0.0-alpha.6'
}
```

---

## Notes

1- Notes for java devs any reified functions
and any functions annotated with @JvmSynthetic
is only for kotlin developers, and java developers won't be able to use it isa.

2- modules are mostly named according to dependencies used in it isa.

3- several modules are used intentionly for 2 reasons
    A- To reduce size as much as possiblt for developers who doesn't need all of the features 
    B- so you will not add unneeded features in your app
    
4- If you try to add the whole project by jitpack feature
an intentional error will happen since that feature will as well add
sample module to your project which in no way is needed
use all module instead to get all modules except sample in this library isa.

---

# All Library Modules (Setup/Highlights)

## Core Kotlin

### Setup

``` groovy
implementation 'com.github.MohamedAlaaEldin636.MAUtils:core_kotlin:$mautils_version'
```

### Dependencies

``` groovy
implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
```

### Highlights

<i><strong>Kotlin Code</strong></i>

```kotlin

/* Generic extension fun */

// eliminate several curly braces if possible isa.
// Ex. -> if Need To Return Unit Due To Interface Signature given 
// nullable int as param and wants to interact with not null
private fun fun1(int: Int?): Unit = int.performIfNotNull { 
    // Your code here with not null receiver
}

// Instead of -> 2 curly braces
private fun fun2(int: Int?): Unit {
    int?.apply { 
        // Your code here with not null receiver
    }
}
// Instead of -> Error not Unit return type (below code makes error)
private fun fun3(int: Int?): Unit = int?.apply { 
    // Your code here with not null receiver
}

/* Iterable extension fun for more consice coding */

// find first instance of given type and cast it as well
val string1: String = list.firstIsInstance<String>()

// Instead of
val string2: String = list.first { it is String } as String

/* Calendar quick access */

// top-level calenar property for quick access to it
val text: String = calendar.currentYearAsString // 2019

// Instead of
val text = Calendar.getInstance().get(Calendar.YEAR).toString() // 2019

// And alot of other utilities for other classes exist as well check docs
```

<details>
<summary><i><strong>Java Code</strong></i></summary>
<p>

``` java
// Auto check addition
ListUtils.addIfNotInside(list, 5);

// Instead of
if (!list.contains(5)) {
    list.add(5);
}

// Check if (int value) equals any of other int values
boolean isMatched = GenericUtils.equalAny(value, 1, 2, 3, 4);

// Instead of
boolean isMatched = value == 1 || value == 2 || value == 3 || value == 4;

// In case you want to convert your list of [1, 2, 3]
// into paired list of each item
// so it becomes [(1, 2), (3, null)]
List<Pair<Integer, Integer>> pairedList = IterableUtils.pairedIteration(list);

// And alot of other utilities for other classes exist as well check docs
```

</p>
</details>

### For more info see [docs](TODO)

---

## Core Android

### Setup

``` groovy
android {
    dataBinding {
        enabled = true
    }
}

dependencies {
    implementation 'com.github.MohamedAlaaEldin636.MAUtils:core_android:$mautils_version'
}
```

### Dependencies

``` groovy
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"

api 'androidx.core:core-ktx:1.0.1'
```

### Highlights

<i><strong>Kotlin Code</strong></i>

``` kotlin
// Add all variables to Bundle immediately instead of creating keys for it.
// add any objects that is supported by Bundle
override fun onSaveInstanceState(outState: Bundle?) {
    outState?.apply { 
        addValues(
            intVal,
            stringVal,
            doubleVal,
            floatArray
        )
    }
    
    super.onSaveInstanceState(outState)
}

// But when you retrieve it only limitation is 
// it MUST be retrieved in same order
override fun onCreate(savedInstanceState: Bundle?) {
    // ...
    savedInstanceState?.apply {
        val getterBundle = getKGetterBundle()
        
        intVal = getterBundle.get()
        stringVal = getterBundle.getOrNull() ?: "fallback"
        doubleVal = getterBundle.get()
        floatArray = getterBundle.get()
    }
}

// Hide keyboard
context.hideKeyboardFrom(editText/*, false optional clearFocus param*/)

// Force Show keyboard
context.showKeyboardFor(editText/*, false optional requestFocus param*/)

// Add alpha to color so result is same color but with 50% transparency
color = color.addColorAlpha(0.5f)

// More Consice implementation for Non-SAM interfaces
// This feature is added to some interfaces
// and in future is planned to try to support all
// possible interfaces.
animatorSet.addListenerMA {
    onAnimationStart {
        // Code for on anim start
    } onAnimationEnd {
        // Code for on anim end
    } onAnimationCancel {
        // Code for on anim cancel
    }
    // Don't need -> onAnimationRepeat { /* Do nothing */ }
} 

// Quick inflation of a layout.xml
val rootViewOfLayout = context.inflateLayout(R.layout.layout/*, parent optional viewGroup, false optional attachToParent*/)

// And alot of other utilities for other classes exist as well check docs
```

<details>
<summary><i><strong>Java Code</strong></i></summary>
<p>

``` java
// Add all variables to Bundle immediately instead of creating keys for it.
// add any objects that is supported by Bundle
@Override
public void onSaveInstanceState(Bundle outState) {
    BundleUtils.addValues(outState,
        intVal,
        stringVal,
        doubleVal,
        floatArray
    );

    super.onSaveInstanceState(outState);
}

// But when you retrieve it only limitation is
// it MUST be retrieved in same order
@Override
public void onCreate(Bundle savedInstanceState) {
    // ...
    JGetterBundle getterBundle = BundleUtils.getJGetterBundle(savedInstanceState);
    
    intVal = getterBundle.get();
    stringVal = getterBundle.getOrNull();
    doubleVal = getterBundle.get();
    floatArray = getterBundle.get();
}


// Hide keyboard
SoftKeyboardUtils.hideKeyboardFrom(context, editText/*, false optional clearFocus param*/);

// Force Show keyboard
SoftKeyboardUtils.showKeyboardFor(context, editText/*, false optional requestFocus param*/);
    
// Add alpha to color so result is same color but with 50% transparency
color = ColorUtils.addColorAlpha(color, 0.5f);

// Quick inflation of a layout.xml
View rootViewOfLayout = ContextUtils.inflateLayout(R.layout.layout/*, parent optional viewGroup, false optional attachToParent*/);

// And alot of other utilities for other classes exist as well check docs
```

</p>
</details>

### For more info see [docs](TODO)

---

## Gson

### Setup

``` groovy
// ==> If you want sealed class, abstract class, interface serialization feature

apply plugin: 'kotlin-kapt'
// ...
dependencies {
    implementation 'com.github.MohamedAlaaEldin636.MAUtils:gson:$mautils_version'
    kapt 'com.github.MohamedAlaaEldin636.MAUtils:gson_processor:$mautils_version'
}

// -------- OR if you don't need the feature --------

dependencies {
    implementation 'com.github.MohamedAlaaEldin636.MAUtils:gson:$mautils_version'
}
```

### Dependencies

``` groovy
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"

implementation 'com.google.code.gson:gson:2.8.5'
api project (':gson_annotation')
```

### Highlights

<i><strong>Kotlin Code</strong></i>

``` kotlin
// Simple extension fun for toJson() & fromJson() and it's customizable
// note even if anyObject is has type parameter no need for you
// to do any additional work
val string = anyObject.toJson() // has optional gson param
val reclaimAnyObject = string.fromJson()

// Sealed class & abstract class & interface safe conversion feature
// in case your class has a sealed class as a parameter
// or even directly just serialize and deserialize sealed class
class Base(val iAmSealedClass: SomeSealedClass)
// it can be safely serialized and deserialized as long as you
// annotate that sealed class like below
@MASealedAbstractOrInterface
sealed class SomeSealedClass
// and surely the same for an interface and abstract classes

// However there is 1 use case limitation and it has solution
// but will need some boiler plate code
// not so much actually it is a line or 2
// And it is ==> in case of nested type parameters
// Ex. Object<TypeParam1, TypeParam2<IAMNestedTypeParam>>
// if you do not have any variance annotated as (in) OR (out)
// then no matter how deep is your nesting it will be converted
// successfully but if you do have any nested varience type parameter
// then you MUST use GsonConverter in a java class (must be java code)
// Gson Converter is insipired from TypeToken in gson library
// Ex. on the case of GsonConverter
val any = CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>()
val json = any.toJson()
// since pair has it's type parameter non-invariant (annotated with out)
// and it is nested (List<...>) then GsonConverter is needed
// in java file
public class GsonCustomWithTypeParam2 extends GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>> {}
// then in kotlin to retrieve it
val value = GsonCustomWithTypeParam2().fromJson(json) 

// buildBundleGson && addValuesGson
// it's like buildBundle in core_android module but better since
// it supports values that can be converted via .toJson()
// so it supports custom object
```

### For more info see [docs](TODO)

---

## Lifecycle Extensions

### Setup

``` groovy
implementation 'com.github.MohamedAlaaEldin636.MAUtils:lifecycle_extensions:$mautils_version'
```

### Dependencies

``` groovy
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"

api "androidx.lifecycle:lifecycle-extensions:2.0.0"
```

### Highlights

<i><strong>Kotlin Code</strong></i>

``` kotlin
/* View Model ( more concise initialization ) */

// Inside your activity Use below code
val yourViewModel = getViewModel<YourViewModel>()

// Instead of
val yourViewModel = ViewModelProviders.of(this).get(YourViewModel::class.java)

/* Mutable Live Data ( more concise initialization ) */

// If you need to initialize MutableLiveData use
val quickMutableLiveData = QuickMutableLiveData("Initial Value")

// Instead of below code
val mutableLiveData = MutableLiveData<String>().apply { value = "Initial Value" }
// OR below code
val mutableLiveData = MutableLiveData<String>()
init {
    mutableLiveData.value = "Initial Value"
}
```

<details>
<summary><i><strong>Java Code</strong></i></summary>
<p>

``` java
// If you need to initialize MutableLiveData use
QuickMutableLiveData<String> quickMutableLiveData = new QuickMutableLiveData<>("Initial Value");

// Instead of below code
class YourClass {
    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    public YourClass() {
        mutableLiveData.setValue("Initial Value");
    }
}
```

</p>
</details>

### For more info see [docs](TODO)

---

# [License]

### MAUtils is released under the [Apache 2.0 license](http://www.apache.org/licenses/).

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
