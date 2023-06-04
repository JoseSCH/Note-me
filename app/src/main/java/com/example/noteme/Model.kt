package com.example.noteme


import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Model(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "nota") var nota: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "color") var color: Int,
)
