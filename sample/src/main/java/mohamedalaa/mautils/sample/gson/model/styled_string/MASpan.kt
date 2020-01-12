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

package mohamedalaa.mautils.sample.gson.model.styled_string

import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface

/**
 *  Can be used in 3 ways isa.
 * 1. press bold then type then press on bold to un-bold so B is highlighted this is a bar above keyboard isa,
 * in hz scroll if large isa.
 * 2. if bar not needed or even if exists have Insert option like in microsoft work in menu items of a toolbar isa.
 * 3. by selecting portion of text then either press bold from popup menu or in bar above keyboard isa.
 *
 * to-do in future isa -> see other spans as well isa, typeface scaleX and see all spans in android devs isa.
 */
@MASealedAbstractOrInterface
sealed class MASpan {

    object Bold : MASpan()
    object Italic : MASpan()
    object Underline : MASpan()

    object Strikethrough : MASpan()

    object BiggerSize : MASpan()

    data class Link(var linkUrl: String) : MASpan()

    /**
     * [backgroundColorInt] used only if [title] is not empty isa.
     */
    data class Location(
        var title: String,
        var latitude: String,
        var longitude: String,
        var backgroundColorInt: Int?
    ) : MASpan()

    data class Image(var location: String) : MASpan()

    /** For voice note, or ringtone or a song etc... isa. */
    data class Music(var location: String) : MASpan()

    /**
     * UI must not only be icon see ur kashkol must have bg color and text to easily read it
     * and searchable via find in file isa.
     * - [backgroundColorInt] if null use default isa.
     * - on insert or selection portion of text check if there is existent time isa.
     */
    data class DateAndTime(var date: String?, var time: String?, var backgroundColorInt: Int?)

    data class ForegroundColor(var colorInt: Int)
    data class BackgroundColor(var colorInt: Int)

}
