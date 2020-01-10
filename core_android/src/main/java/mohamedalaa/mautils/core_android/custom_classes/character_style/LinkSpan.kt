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

package mohamedalaa.mautils.core_android.custom_classes.character_style

import android.content.Intent
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import mohamedalaa.mautils.core_android.extensions.launchEMail
import mohamedalaa.mautils.core_android.extensions.launchLink

/**
 * - Note it has no changes in appearance at all so if you want to add underline or bold or change color
 * you need to add as well other spans isa.
 * - Span that is clickable and on click checks [link] and performs following actions isa,
 *      - if [Patterns.EMAIL_ADDRESS] uses [launchEMail] function isa.
 *      - else uses [launchLink] function isa.
 *
 * ### Properties
 * - [link]
 * - [useGooglePlayAsLauncher]
 * - [intentModificationsBlock]
 *
 * @property link link to launch on click Ex. https://www.google.com isa.
 * @property useGooglePlayAsLauncher in case your link directs to a link in Google Play and you want
 * the Google Play app to launch it instead of a browser then set this value to `true` isa.
 * @property intentModificationsBlock In case you like to modify the [Intent] that's used
 * for launching your [link] isa.
 *
 * @see DrawableSpan
 */
class LinkSpan(
    private val link: String,
    private val useGooglePlayAsLauncher: Boolean = false,
    private val intentModificationsBlock: (Intent.() -> Unit)? = null
) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {}

    override fun onClick(widget: View) {
        if (Patterns.EMAIL_ADDRESS.matcher(link).matches()) {
            widget.context?.launchEMail(
                listOf(link)
            )
        }else {
            widget.context?.launchLink(
                link,
                true,
                useGooglePlayAsLauncher,
                true,
                intentModificationsBlock
            )
        }
    }
}
