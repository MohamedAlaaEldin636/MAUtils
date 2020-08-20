# MAUtils
[![](https://jitpack.io/v/MohamedAlaaEldin636/MAUtils.svg)](https://jitpack.io/#MohamedAlaaEldin636/MAUtils)
[![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release) [**Wiki Home Page**](https://github.com/MohamedAlaaEldin636/MAUtils/wiki)


# Library is in Progress (Alpha Development Stage)

- There are some modules which are stable (via stable releases of this library) so they have been tested and won't change but those are the ones only mentioned in README.md or [wiki](https://github.com/MohamedAlaaEldin636/MAUtils/wiki) otherwise the rest are experimental (alpha release), so don't get confused when you see releases without the extension `-alpha.#` this yes means stable but for some modules not the whole library.  
- for a temp. in production use, Ex. Google Play publish you can use the stable modules but sometimes this might mean implementing other non-stable modules, So **Make That as a last resort** until the whole library is stable.

# Contents [▴](#mautils)

- [What is MAUtils](#what-is-mautils-)
- [How to use MAUtils](#how-to-use-mautils-)
- [Features of Currently stable modules](#features-of-currently-stable-modules-)
    - [gson & gson_annotation & gson_processor modules](#gson--gson_annotation--gson_processor-modules-)
        - [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/gson-&-gson_annotation-&-gson_processor-modules)
    - [shared_pref_core module](#shared_pref_core-module-)
        - [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/shared_pref_core-module)
    - [shared_pref_annotation & shared_pref_processor modules](#shared_pref_annotation--shared_pref_processor-modules-)
        - [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/shared_pref_annotation-&-shared_pref_processor-modules)
    - [core_android module](#core_android-module-)
        - [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/core_android-module)
    - [core_kotlin module](#core_kotlin-module-)
        - [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/core_kotlin-module) 
- [License](#license-)

# What is MAUtils [▴](#contents-)

- Multi-module Android Library to facilitate common tasks.
- Each module is built on top of an/other library/s to add new features to it and/or facilitates the usage of its current features.
- Most of the modules will be very useful for almost any Android Application, Since the goal of this Library is to save the developer time by using less boilerplate code for common tasks.

# How to use MAUtils [▴](#contents-)

- Check out [**wiki home page**](https://github.com/MohamedAlaaEldin636/MAUtils/wiki) for how to Download each module along with their features and a lot of examples in java/kotlin code
for how to use the features in each module.

# Features of Currently stable modules [▴](#contents-)

- [gson & gson_annotation & gson_processor modules](#gson--gson_annotation--gson_processor-modules-)
    - [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/gson-&-gson_annotation-&-gson_processor-modules)
- [shared_pref_core module](#shared_pref_core-module-)
    - [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/shared_pref_core-module)
- [shared_pref_annotation & shared_pref_processor modules](#shared_pref_annotation--shared_pref_processor-modules-)
    - [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/shared_pref_annotation-&-shared_pref_processor-modules)
- [core_android module](#core_android-module-)
    - [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/core_android-module)
- [core_kotlin module](#core_kotlin-module-)
    - [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/core_kotlin-module) 

## gson & gson_annotation & gson_processor modules [▴](#features-of-currently-stable-modules-)

- [Full Details](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/gson-&-gson_annotation-&-gson_processor-modules)

### Features [▴](#gson--gson_annotation--gson_processor-modules-)

1. support conversion to/from JSON just by using extension functions of `Any?.toJson` & `String?.fromJson`.
2. support conversion of **sealed classes** / **abstract classes** / **interfaces**
this is only where the annotation is needed and only for root type, Not needed for nested ones.
3. (kotlin) object keyword(class) conversion maintains the same instance.
4. support conversion for the above cases even if they are inside another class as a property.
5. Supports conversions of types with type params even in case of nested type params no matter how deep nesting is, Without the use of `TypeToken` for kotlin consumer code 
(for java consumer code `GsonConverter` is needed in case of any type params like `TypeToken`) isa.
6. building on top of [`core_android module`](core_android-module) for `Bundle` using `buildBundleGson(vararg values: Any?)`
to save any custom object on a bundle, And surely that is, in addition, to put & get values from bundle easily without the need of explicitly mentioning the key
which is already the feature of `buildBundle` in [`core_android module`](core_android-module). 

## shared_pref_core module [▴](#features-of-currently-stable-modules-)

- [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/shared_pref_core-module)

## Features [▴](#shared_pref_core-module-)

1. for quicker coding it would be better instead of putString putInt to have one function name here is why `Context.sharedPrefSet` exists use it for all types, Also quicker than getting the `SharedPreferences` instance then edit then commit or apply changes all of that in one function with default parameter for common use cases so actually you only provide 3 things fileName, key and value. <br/>
`/*for Java*/ SharedPrefUtils.set`

2. Supports **Any Type** thanks to the other module in this library which is [gson module](gson-&-gson_annotation-&-gson_processor-modules) auto conversion will be done for you so you can save any custom object to `SharedPreference` easily without making the conversion to/from String yourself.
Remember that's only in case the custom type is suitable for `SharedPreferences` otherwise for non-small sizes consider other options such as databases, files or cloud.

3. additional functions are added for quick access instead of getting instance to `SharedPreferences` then performing edits for Ex. clearing all keys, removing specific key in the file or checking if a key exists you just provide the file's name and we do the rest but also all other parameters are optional for customizability so No trade-offs for the concise code.

## shared_pref_annotation & shared_pref_processor modules [▴](#features-of-currently-stable-modules-)

- [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/shared_pref_annotation-&-shared_pref_processor-modules)

## Features [▴](#shared_pref_annotation--shared_pref_processor-modules-)

1. eliminates the need of a lot of constants in your codebase to define your `SharedPreferences` files names and their keys now by an annotation specific functions made according to that specific key so more concise code in generating and calling the `SharedPreferences` key/value pairs.

2. Since it's built on top of [shared_pref_core module](shared_pref_core-module) it supports **Any Type** not just the types supported by `SharedPreferences` thanks to [gson module](gson-&-gson_annotation-&-gson_processor-modules) of this library isa.

3. Supports additional functions not just setter/getter for key/value pairs for ex. to have an onChange listener for `SharedPreferences` you would need the file name and get an instance then register now a function will be created for you only if you require it in the annotation `MASharedPrefFileConfigs` to register/unregister and a lot of other functions as well to have full control to do whatever suits your use case.

4. supports default value in annotation so when you call `Context.sharedPref${FileName}_GetName()` function to get a value of a key `name` for example there will be only optional arguments no required arguments needed for more concise and better and faster coding for you, also if you don't specify a default value then it will be auto-created for you which is gonna be `0` for numbers, empty string for `String`, `false` for `Boolean` and `null` for custom types, **Also** defaultValue accepts kotlin code expressions so you can make the default value emptyList() for example if you want isa.

## core_android module [▴](#features-of-currently-stable-modules-)

- [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/core_android-module)

## Features [▴](#core_android-module-)

1. Custom classes -> Spans, Dialogs & Application for common use cases.
2. Binding Adapter functions for better attributes in layout xml.
3. extension functions for core android to facilitate common usages.

## core_kotlin module [▴](#features-of-currently-stable-modules-)

- [`Full Details`](https://github.com/MohamedAlaaEldin636/MAUtils/wiki/core_kotlin-module)

## Features [▴](#core_kotlin-module-)

1. You can think of this module as kotlin-stdlib it has extension functions to facilitate common usages in kotlin programming language.

---
# K/kdls(ss)s/s

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
| :heavy_check_mark: Commercial Use | :x: Trademark use | :information_source: License and copyright notice |
| :heavy_check_mark: Modification | :x: Liability | :information_source: State changes |
| :heavy_check_mark: Distribution | :x: Warranty | - |
| :heavy_check_mark: Patent use | - | - |
| :heavy_check_mark: Private use | - | - |
