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

package mohamedalaa.mautils.sample.gson.open_classes

import mohamedalaa.mautils.sample.gson.model.*
import mohamedalaa.mautils.sample.gson.model.additional_models_for_complex_tests.abstract_classes.AbstractNotifyAs_Action_WithAlarm
import mohamedalaa.mautils.sample.gson.model.additional_models_for_complex_tests.abstract_classes.AbstractNotifyAs_Alarm
import mohamedalaa.mautils.sample.gson.model.additional_models_for_complex_tests.abstract_classes.AT_SilentMode
import mohamedalaa.mautils.sample.gson.model.additional_models_for_complex_tests.interfaces_calsses.*
import mohamedalaa.mautils.sample.gson.model.entity.ReminderOrAction
import mohamedalaa.mautils.sample.gson.model.repeat.ChainedRepeatPolicy
import mohamedalaa.mautils.sample.gson.model.repeat.RepeatPolicy
import mohamedalaa.mautils.sample.gson.model.repeat.RepeatUntilPolicy
import mohamedalaa.mautils.sample.gson.model.styled_string.MAIndexedSpan
import mohamedalaa.mautils.sample.gson.model.styled_string.MASpan
import mohamedalaa.mautils.sample.gson.model.styled_string.MAStyledString
import java.util.*

open class BaseComplexClass {

    val reminderOrAction1: ReminderOrAction = kotlin.run {
        ReminderOrAction(
            id = 1,
            useLocalTiming = true,
            creationDate = 0L,
            chatId = "dwede",
            backupReminderOrAction = BackupReminderOrAction(
                "Dewdwe",
                3
            ),
            title = MAStyledString(
                "dewldke;wled;wled;weldwe",
                listOf(
                    MAIndexedSpan(
                        3, 4, MASpan.Bold
                    ),
                    MAIndexedSpan(
                        3, 4, MASpan.Link("https://www.google.com")
                    )
                )
            ),
            additionalNotes = MAStyledString(
                "aaaAaAadewldke;wled;wled;weldwe",
                listOf(
                    MAIndexedSpan(
                        3, 4, MASpan.Bold
                    ),
                    MAIndexedSpan(
                        3, 4, MASpan.Link("https://www.google.com")
                    )
                )
            ),
            isFavorite = true,
            isHidden = false,
            conditions = listOf(
                listOf(
                    ConditionReminderOrAction.Timing.AbstractWindowDate(
                        ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth(
                            listOf(
                                Calendar.FRIDAY,
                                Calendar.SATURDAY
                            )
                        ),
                        ConditionReminderOrAction.Timing.AbstractExactDate.StartMidEndOfMonth(
                            start = true,
                            mid = false,
                            midTendsToLowerRounding = false,
                            end = false
                        )
                    ),
                    ConditionReminderOrAction.Place(
                        "4"
                    )
                ),
                listOf(
                    ConditionReminderOrAction.Place(
                        "4"
                    ),
                    ConditionReminderOrAction.Activity(
                        2,
                        useEnterTransitionType = true,
                        useExitTransitionType = false
                    )
                )
            ),
            chainedRepeatPolicy = ChainedRepeatPolicy(
                3,
                listOf(
                    RepeatPolicy.SameConditions(
                        4,
                        1,
                        RepeatUntilPolicy(
                            true,
                            0,
                            0,
                            ConditionReminderOrAction.Timing.ConcreteWindowDate(
                                ConditionReminderOrAction.Timing.ConcreteExactDate(
                                    5,
                                    Calendar.JULY,
                                    2012
                                ),
                                ConditionReminderOrAction.Timing.ConcreteExactDate(
                                    31,
                                    Calendar.JULY,
                                    2012
                                )
                            )
                        )
                    ),
                    RepeatPolicy.BackToAnotherRepeatPolicyStep(0)
                )
            ),
            snoozePolicyAsJsonString = SnoozePolicy.Custom(
                7,
                3,
                true
            ),
            trashTimeInMillis = null,
            isGentleAlarm = false,
            priorNotificationTimeInMillis = 4L,
            dismissConstraintAsJsonString = DismissOrSnoozeConstraint.Pattern("dwdqlwldkql"),
            snoozeConstraintAsJsonString = DismissOrSnoozeConstraint.LikeDismissConstraint(),
            turnOffConstraintAsJsonString = null,
            turnOnConstraintAsJsonString = DismissOrSnoozeConstraint.QuestionAndAnswer("qq", "aaaaa"),
            notifyAsReminderOrActionAsJsonString = NotifyAsReminderOrAction.Action.WithAlarm(
                listOf(
                    TaskReminderOrAction.SilentMode(false)
                ),
                NotifyAsReminderOrAction.Alarm()
            ),
            abstractNotifyAs = AbstractNotifyAs_Action_WithAlarm(
                listOf(
                    AT_SilentMode(false)
                ),
                AbstractNotifyAs_Alarm()
            ),
            interfaceChainedRepeatPolicy = InterfaceChainedRepeatPolicy(
                3,
                listOf(
                    IRP_SameConditions(
                        4,
                        1,
                        InterfaceRepeatUntilPolicy(
                            true,
                            0,
                            0,
                            InterfaceCond_Timing_ConcreteWindowDate(
                                InterfaceCond_Timing_ConcreteExactDate(
                                    5,
                                    Calendar.JULY,
                                    2012
                                ),
                                InterfaceCond_Timing_ConcreteExactDate(
                                    31,
                                    Calendar.JULY,
                                    2012
                                )
                            )
                        )
                    ),
                    InterfaceRepeatPolicy_BackToAnotherRepeatPolicyStep(0)
                )
            )
        )
    }
    val reminderOrAction2 = kotlin.run {
        ReminderOrAction(
            id = 1,
            useLocalTiming = true,
            creationDate = 0L,
            chatId = "dwede",
            backupReminderOrAction = BackupReminderOrAction(
                "Dewdwe",
                3
            ),
            title = MAStyledString(
                "dewldke;wled;wled;weldwe",
                listOf(
                    MAIndexedSpan(
                        3, 4, MASpan.Bold
                    ),
                    MAIndexedSpan(
                        3, 4, MASpan.Link("https://www.google.com")
                    )
                )
            ),
            additionalNotes = MAStyledString(
                "aaaAaAadewldke;wled;wled;weldwe",
                listOf(
                    MAIndexedSpan(
                        3, 4, MASpan.Bold
                    ),
                    MAIndexedSpan(
                        3, 4, MASpan.Link("https://www.google.com")
                    )
                )
            ),
            isFavorite = true,
            isHidden = false,
            conditions = listOf(
                listOf(
                    ConditionReminderOrAction.Timing.AbstractWindowDate(
                        ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth(
                            listOf(
                                Calendar.FRIDAY,
                                Calendar.SATURDAY
                            )
                        ),
                        ConditionReminderOrAction.Timing.AbstractExactDate.StartMidEndOfMonth(
                            start = true,
                            mid = false,
                            midTendsToLowerRounding = false,
                            end = false
                        )
                    ),
                    ConditionReminderOrAction.Place(
                        "4"
                    )
                ),
                listOf(
                    ConditionReminderOrAction.Place(
                        "4"
                    ),
                    ConditionReminderOrAction.Activity(
                        2,
                        useEnterTransitionType = true,
                        useExitTransitionType = false
                    )
                )
            ),
            chainedRepeatPolicy = ChainedRepeatPolicy(
                3,
                listOf(
                    RepeatPolicy.SameConditions(
                        4,
                        1,
                        RepeatUntilPolicy(
                            true,
                            0,
                            0,
                            ConditionReminderOrAction.Timing.ConcreteWindowDate(
                                ConditionReminderOrAction.Timing.ConcreteExactDate(
                                    5,
                                    Calendar.JULY,
                                    2012
                                ),
                                ConditionReminderOrAction.Timing.ConcreteExactDate(
                                    31,
                                    Calendar.JULY,
                                    2012
                                )
                            )
                        )
                    ),
                    RepeatPolicy.BackToAnotherRepeatPolicyStep(0)
                )
            ),
            snoozePolicyAsJsonString = SnoozePolicy.Custom(
                7,
                3,
                true
            ),
            trashTimeInMillis = null,
            isGentleAlarm = false,
            priorNotificationTimeInMillis = 4L,
            dismissConstraintAsJsonString = DismissOrSnoozeConstraint.Pattern("dwdqlwldkql"),
            snoozeConstraintAsJsonString = DismissOrSnoozeConstraint.LikeDismissConstraint(),
            turnOffConstraintAsJsonString = null,
            turnOnConstraintAsJsonString = DismissOrSnoozeConstraint.QuestionAndAnswer("qq", "aaaaa"),
            notifyAsReminderOrActionAsJsonString = NotifyAsReminderOrAction.Action.WithAlarm(
                listOf(
                    TaskReminderOrAction.SilentMode(false)
                ),
                NotifyAsReminderOrAction.Alarm()
            ),
            abstractNotifyAs = AbstractNotifyAs_Action_WithAlarm(
                listOf(
                    AT_SilentMode(false)
                ),
                AbstractNotifyAs_Alarm()
            ),
            interfaceChainedRepeatPolicy = InterfaceChainedRepeatPolicy(
                3,
                listOf(
                    IRP_SameConditions(
                        4,
                        1,
                        InterfaceRepeatUntilPolicy(
                            true,
                            0,
                            0,
                            InterfaceCond_Timing_ConcreteWindowDate(
                                InterfaceCond_Timing_ConcreteExactDate(
                                    5,
                                    Calendar.JULY,
                                    2012
                                ),
                                InterfaceCond_Timing_ConcreteExactDate(
                                    31,
                                    Calendar.JULY,
                                    2012
                                )
                            )
                        )
                    ),
                    InterfaceRepeatPolicy_BackToAnotherRepeatPolicyStep(0)
                )
            )
        )
    }

    val reminderOrAction3 = kotlin.run {
        ReminderOrAction(
            id = 1,
            useLocalTiming = true,
            creationDate = 0L,
            chatId = "dwede",
            backupReminderOrAction = BackupReminderOrAction(
                "Dewdwe",
                3
            ),
            title = MAStyledString(
                "dewldke;wled;wled;weldwe",
                listOf(
                    MAIndexedSpan(
                        3, 4, MASpan.Bold
                    ),
                    MAIndexedSpan(
                        3, 4, MASpan.Link("https://www.google.com")
                    )
                )
            ),
            additionalNotes = MAStyledString(
                "aaaAaAadewldke;wled;wled;weldwe",
                listOf(
                    MAIndexedSpan(
                        3, 4, MASpan.Bold
                    ),
                    MAIndexedSpan(
                        3, 4, MASpan.Link("https://www.google.com")
                    )
                )
            ),
            isFavorite = true,
            isHidden = false,
            conditions = listOf(
                listOf(
                    ConditionReminderOrAction.Timing.AbstractWindowDate(
                        ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth(
                            listOf(
                                Calendar.FRIDAY,
                                Calendar.SATURDAY
                            )
                        ),
                        ConditionReminderOrAction.Timing.AbstractExactDate.StartMidEndOfMonth(
                            start = true,
                            mid = false,
                            midTendsToLowerRounding = false,
                            end = false
                        )
                    ),
                    ConditionReminderOrAction.Place(
                        "4"
                    )
                ),
                listOf(
                    ConditionReminderOrAction.Place(
                        "4"
                    ),
                    ConditionReminderOrAction.Activity(
                        2,
                        useEnterTransitionType = true,
                        useExitTransitionType = false
                    )
                )
            ),
            chainedRepeatPolicy = ChainedRepeatPolicy(
                3,
                listOf(
                    RepeatPolicy.SameConditions(
                        4,
                        1,
                        RepeatUntilPolicy(
                            true,
                            0,
                            0,
                            ConditionReminderOrAction.Timing.ConcreteWindowDate(
                                ConditionReminderOrAction.Timing.ConcreteExactDate(
                                    5,
                                    Calendar.JULY,
                                    2012
                                ),
                                ConditionReminderOrAction.Timing.ConcreteExactDate(
                                    31,
                                    Calendar.JULY,
                                    2012
                                )
                            )
                        )
                    ),
                    RepeatPolicy.BackToAnotherRepeatPolicyStep(0)
                )
            ),
            snoozePolicyAsJsonString = SnoozePolicy.Custom(
                7,
                3,
                true
            ),
            trashTimeInMillis = null,
            isGentleAlarm = false,
            priorNotificationTimeInMillis = 4L,
            dismissConstraintAsJsonString = DismissOrSnoozeConstraint.Pattern("dwdqlwldkql"),
            snoozeConstraintAsJsonString = DismissOrSnoozeConstraint.LikeDismissConstraint(),
            turnOffConstraintAsJsonString = null,
            turnOnConstraintAsJsonString = DismissOrSnoozeConstraint.QuestionAndAnswer("qq", "aaaaa"),
            notifyAsReminderOrActionAsJsonString = NotifyAsReminderOrAction.Notification
        )
    }
    val reminderOrAction4 = kotlin.run {
        ReminderOrAction(
            id = 1,
            useLocalTiming = true,
            creationDate = 0L,
            chatId = "dwede",
            backupReminderOrAction = BackupReminderOrAction(
                "Dewdwe",
                3
            ),
            title = MAStyledString(
                "dewldke;wled;wled;weldwe",
                listOf(
                    MAIndexedSpan(
                        3, 4, MASpan.Bold
                    ),
                    MAIndexedSpan(
                        3, 4, MASpan.Link("https://www.google.com")
                    )
                )
            ),
            additionalNotes = MAStyledString(
                "aaaAaAadewldke;wled;wled;weldwe",
                listOf(
                    MAIndexedSpan(
                        3, 4, MASpan.Bold
                    ),
                    MAIndexedSpan(
                        3, 4, MASpan.Link("https://www.google.com")
                    )
                )
            ),
            isFavorite = true,
            isHidden = false,
            conditions = listOf(
                listOf(
                    ConditionReminderOrAction.Timing.AbstractWindowDate(
                        ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth(
                            listOf(
                                Calendar.FRIDAY,
                                Calendar.SATURDAY
                            )
                        ),
                        ConditionReminderOrAction.Timing.AbstractExactDate.StartMidEndOfMonth(
                            start = true,
                            mid = false,
                            midTendsToLowerRounding = false,
                            end = false
                        )
                    ),
                    ConditionReminderOrAction.Place(
                        "4"
                    )
                ),
                listOf(
                    ConditionReminderOrAction.Place(
                        "4"
                    ),
                    ConditionReminderOrAction.Activity(
                        2,
                        useEnterTransitionType = true,
                        useExitTransitionType = false
                    )
                )
            ),
            chainedRepeatPolicy = ChainedRepeatPolicy(
                3,
                listOf(
                    RepeatPolicy.SameConditions(
                        4,
                        1,
                        RepeatUntilPolicy(
                            true,
                            0,
                            0,
                            ConditionReminderOrAction.Timing.ConcreteWindowDate(
                                ConditionReminderOrAction.Timing.ConcreteExactDate(
                                    5,
                                    Calendar.JULY,
                                    2012
                                ),
                                ConditionReminderOrAction.Timing.ConcreteExactDate(
                                    31,
                                    Calendar.JULY,
                                    2012
                                )
                            )
                        )
                    ),
                    RepeatPolicy.BackToAnotherRepeatPolicyStep(0)
                )
            ),
            snoozePolicyAsJsonString = SnoozePolicy.Custom(
                7,
                3,
                true
            ),
            trashTimeInMillis = null,
            isGentleAlarm = false,
            priorNotificationTimeInMillis = 4L,
            dismissConstraintAsJsonString = DismissOrSnoozeConstraint.Pattern("dwdqlwldkql"),
            snoozeConstraintAsJsonString = DismissOrSnoozeConstraint.LikeDismissConstraint(),
            turnOffConstraintAsJsonString = null,
            turnOnConstraintAsJsonString = DismissOrSnoozeConstraint.QuestionAndAnswer("qq", "aaaaa"),
            notifyAsReminderOrActionAsJsonString = NotifyAsReminderOrAction.Notification
        )
    }

    val reminderOrAction1WithSomeNulls: ReminderOrAction = kotlin.run {
        ReminderOrAction(
            id = 1,
            useLocalTiming = true,
            creationDate = 0L,
            chatId = "dwede",
            backupReminderOrAction = BackupReminderOrAction(
                "Dewdwe",
                3
            ),
            title = MAStyledString(
                "dewldke;wled;wled;weldwe",
                listOf(
                    MAIndexedSpan(
                        3, 4, MASpan.Bold
                    ),
                    MAIndexedSpan(
                        3, 4, MASpan.Link("https://www.google.com")
                    )
                )
            ),
            additionalNotes = MAStyledString(
                "aaaAaAadewldke;wled;wled;weldwe",
                listOf(
                    MAIndexedSpan(
                        3, 4, MASpan.Bold
                    ),
                    MAIndexedSpan(
                        3, 4, MASpan.Link("https://www.google.com")
                    )
                )
            ),
            isFavorite = true,
            isHidden = false,
            conditions = listOf(
                listOf(
                    ConditionReminderOrAction.Timing.AbstractWindowDate(
                        ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth(
                            listOf(
                                Calendar.FRIDAY,
                                Calendar.SATURDAY
                            )
                        ),
                        ConditionReminderOrAction.Timing.AbstractExactDate.StartMidEndOfMonth(
                            start = true,
                            mid = false,
                            midTendsToLowerRounding = false,
                            end = false
                        )
                    ),
                    ConditionReminderOrAction.Place(
                        "4"
                    )
                ),
                listOf(
                    ConditionReminderOrAction.Place(
                        "4"
                    ),
                    ConditionReminderOrAction.Activity(
                        2,
                        useEnterTransitionType = true,
                        useExitTransitionType = false
                    )
                )
            ),
            chainedRepeatPolicy = ChainedRepeatPolicy(
                3,
                listOf(
                    RepeatPolicy.SameConditions(
                        4,
                        1,
                        null
                    ),
                    RepeatPolicy.BackToAnotherRepeatPolicyStep(0)
                )
            ),
            snoozePolicyAsJsonString = SnoozePolicy.Custom(
                7,
                null,
                true
            ),
            trashTimeInMillis = null,
            isGentleAlarm = false,
            priorNotificationTimeInMillis = 4L,
            dismissConstraintAsJsonString = DismissOrSnoozeConstraint.Pattern("dwdqlwldkql", 45),
            snoozeConstraintAsJsonString = DismissOrSnoozeConstraint.LikeDismissConstraint(),
            turnOffConstraintAsJsonString = null,
            turnOnConstraintAsJsonString = DismissOrSnoozeConstraint.QuestionAndAnswer("qq", "aaaaa"),
            notifyAsReminderOrActionAsJsonString = NotifyAsReminderOrAction.Action.WithAlarm(
                listOf(
                    TaskReminderOrAction.SilentMode(false),
                    TaskReminderOrAction.StartTimer(2, null, null, 32),
                    TaskReminderOrAction.Record(false, null, 22)
                ),
                NotifyAsReminderOrAction.Alarm()
            )
        )
    }

}
