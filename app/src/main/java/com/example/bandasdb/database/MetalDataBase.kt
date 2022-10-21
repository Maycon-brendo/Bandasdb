package com.example.bandasdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bandasdb.daos.BandDao
import com.example.bandasdb.daos.BandMusicianDao
import com.example.bandasdb.daos.MusicianDao
import com.example.bandasdb.models.Band
import com.example.bandasdb.models.BandMusician
import com.example.bandasdb.models.Musician

@Database(
    entities = [Band::class, Musician::class, BandMusician::class],
    version = 1,
    exportSchema = false
)
abstract class MetalDataBase : RoomDatabase() {

    abstract fun bandDao(): BandDao
    abstract fun musicianDao(): MusicianDao
    abstract fun bandMusicianDao(): BandMusicianDao
}