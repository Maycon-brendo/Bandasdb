package com.example.bandasdb.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Band")
data class Band(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long = 0L,

    @ColumnInfo(name = "name")
    val name:String = "",

    @ColumnInfo(name = "genre")
    val genre:String = "",

    @ColumnInfo(name = "formation")
    val formation: String = "",
)