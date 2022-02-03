package com.crystal.locationsearchmapapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationLatLanEntity(
    val latitude: Float,
    val longitude: Float
):Parcelable
