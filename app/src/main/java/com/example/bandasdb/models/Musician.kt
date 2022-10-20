package com.example.bandasdb.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Musician")
data class Musician (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long = 0L,

    @ColumnInfo(name = "name")
    val name:String = "",

    @ColumnInfo(name = "gender")
    val gender:String = "",

    @ColumnInfo(name = "age")
    val age:Long = 0L,
)