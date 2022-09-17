package com.udacity.asteroidradar.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "asteroids_table")
@Parcelize
@JsonClass(generateAdapter = true)
data class Asteroid constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "codename")
    val codename: String?,
    @ColumnInfo(name = "closeApproachDate")
    val closeApproachDate: String?,
    @ColumnInfo(name = "absoluteMagnitude")
    val absoluteMagnitude: Double?,
    @ColumnInfo(name = "estimatedDiameter")
    val estimatedDiameter: Double?,
    @ColumnInfo(name = "relativeVelocity")
    val relativeVelocity: Double?,
    @ColumnInfo(name = "distanceFromEarth")
    val distanceFromEarth: Double?,
    @ColumnInfo(name = "isPotentiallyHazardous")
    val isPotentiallyHazardous: Boolean?
) : Parcelable