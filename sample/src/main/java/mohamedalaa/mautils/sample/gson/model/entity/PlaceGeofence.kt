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

package mohamedalaa.mautils.sample.gson.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @property id is same id used for registering a [Geofence] by [Geofence.Builder.setRequestId] isa.
 *
 * @property transitionTypes bitwise-OR of [Geofence.GEOFENCE_TRANSITION_ENTER],
 * [Geofence.GEOFENCE_TRANSITION_DWELL] and [Geofence.GEOFENCE_TRANSITION_EXIT]
 *
 * @property loiteringForDwellTransitionInMs time delay used for [Geofence.GEOFENCE_TRANSITION_DWELL]
 * to be invoked if still in place after [Geofence.GEOFENCE_TRANSITION_ENTER] occurred isa.
 */
@Entity(
    tableName = "placeGeofence"
)
data class PlaceGeofence(

    @PrimaryKey(autoGenerate = false)
    var id: String,

    var latitude: Double,
    var longitude: Double,
    var radiusInMeters: Float,

    var transitionTypes: Int,

    var loiteringForDwellTransitionInMs: Long

)
