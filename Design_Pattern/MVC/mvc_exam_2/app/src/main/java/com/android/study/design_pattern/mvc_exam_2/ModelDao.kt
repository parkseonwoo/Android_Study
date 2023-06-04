package com.android.study.design_pattern.mvc_exam_2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface ModelDao {

    @Delete
    fun delete(models: Model)

    @Insert
    fun insert(models: Model)

}