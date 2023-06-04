package com.android.study.design_pattern.mvc_exam_2

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "models")
data class Model (
    val name: String,
    @PrimaryKey(autoGenerate = true) val id:Int = 0
) : Parcelable