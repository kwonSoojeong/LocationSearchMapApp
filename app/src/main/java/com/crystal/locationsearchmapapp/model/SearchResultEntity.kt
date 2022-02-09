package com.crystal.locationsearchmapapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResultEntity(
    val FullAddress:String,
    val BuildingName:String,
    val locationLatLan: LocationLatLanEntity
) : Parcelable
