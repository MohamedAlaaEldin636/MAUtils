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

package mohamedalaa.mautils.sample;

import android.animation.Animator;
import android.animation.AnimatorSet;

import java.util.Calendar;
import java.util.List;

import kotlin.Pair;
import mohamedalaa.mautils.core_android.extensions.AnimatorSetUtils;
import mohamedalaa.mautils.core_android.extensions.ColorUtils;
import mohamedalaa.mautils.core_kotlin.extensions.CalendarUtils;
import mohamedalaa.mautils.core_kotlin.extensions.CollectionsUtils;
import mohamedalaa.mautils.core_kotlin.extensions.GeneralUtils;
import mohamedalaa.mautils.core_kotlin.extensions.IterableUtils;
import mohamedalaa.mautils.core_kotlin.extensions.ListUtils;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/19/2019.
 */
public class JavaQuickTrialIsa {

    private void f1() {
        Calendar calendar = CalendarUtils.getCalendar();

        String year = CalendarUtils.getCurrentYearAsString(calendar);
    }

    private void f2(List<String> list) {
        CollectionsUtils.performIfNotNullNorEmpty(list, strings -> {
            //

            return null;
        });
    }

    private void f3(List<Integer> list) {

        // Auto check addition
        ListUtils.addIfNotInside(list, 5);

        // Instead of
        if (!list.contains(5)) {
            list.add(5);
        }

        // In case you want to convert your list of [1, 2, 3]
        // into paired list of each item
        // so it becomes [(1, 2), (3, null)]
        List<Pair<Integer, Integer>> pairedList = IterableUtils.pairedIteration(list);


        int value = 9;

        // check if (int value) equals any of other int values
        boolean isMatched = GeneralUtils.equalAny(value, 1, 2, 3, 4);

        // Instead of
        isMatched = value == 1 || value == 2 || value == 3 || value == 4;
        switch (value) {
            case 1:
            case 2:
            case 3:
            case 4:
                isMatched = true;
                break;
            default:
                isMatched = false;
                break;
        }

    }

    private void f111(AnimatorSet animatorSet, final Animator a1) {
        AnimatorSetUtils.addListenerMA(animatorSet, maAnimatorAnimatorListener -> {
            maAnimatorAnimatorListener.onAnimationStart(maAnimatorAnimatorListener, animator -> {
                //

                return null;
            });

            maAnimatorAnimatorListener.onAnimationCancel(maAnimatorAnimatorListener, animator -> {
                // animator.someThing()

                return null;
            });

            return null;
        });

        //SpannableUtils.spanChars()
    }

    private void ff1(int color) {
        // Add alpha to color so result is same color but with 50% transparency
        color = ColorUtils.addColorAlpha(color, 0.5f);

    }

}
