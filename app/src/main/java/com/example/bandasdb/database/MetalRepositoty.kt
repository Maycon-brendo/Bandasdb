package com.example.bandasdb.database

import android.content.Context
import androidx.room.Room
import com.example.bandasdb.models.Band
import com.example.bandasdb.models.BandMusician
import com.example.bandasdb.models.Musician
import kotlinx.coroutines.flow.Flow

private const val DATABASE_NAME = "metal-db"

class MetalRepositoty private constructor(context: Context) {

    private val database: MetalDataBase = Room
        .databaseBuilder(
            context.applicationContext,
            MetalDataBase::class.java,
            DATABASE_NAME
        )
        .build()

    companion object {
        private var INSTANCE: MetalRepositoty? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MetalRepositoty(context)
            }
        }

        fun get(): MetalRepositoty {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // DAO Band ///////////////////////////////////////////////////////////////////////////////////


    fun getAllBands(): Flow<List<Band>> = database.bandDao().getAll()

    fun insertBand(band: Band) {
        database.bandDao().insert(band)
    }

    fun getBandById(bandId: Long): Band {
        return database.bandDao().getById(bandId)
    }

    fun updateBand(band: Band){
        database.bandDao().update(band)
    }

    fun deleteBand(band: Band){
        database.bandDao().delete(band)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // DAO Musician ///////////////////////////////////////////////////////////////////////////////////


    fun getAllMusicians(): Flow<List<Musician>> = database.musicianDao().getAll()

    fun insertMusician(musician: Musician) {
        database.musicianDao().insert(musician)
    }

    fun getMusicianById(musicianId: Long): Musician {
        return database.musicianDao().getById(musicianId)
    }

    fun updateMusician(musician: Musician){
        database.musicianDao().update(musician)
    }

    fun deleteMusician(musician: Musician){
        database.musicianDao().delete(musician)
    }

    fun getMusicianByName(input: String): Flow<List<Musician>> = database
        .musicianDao()
        .getListByName(input)

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // DAO BandMusician ////////////////////////////////////////////////////////////////////////////
    fun getAllBandsMusician(): Flow<List<BandMusician>> = database.bandMusicianDao().getAll()

    fun insertBandMusician(bandMusician: BandMusician) {
        database.bandMusicianDao().insert(bandMusician)
    }

    fun getBandMusicianById(bandMusicianId: Long): BandMusician {
        return database.bandMusicianDao().getById(bandMusicianId)
    }

    fun updateBandMusician(bandMusician: BandMusician){
        database.bandMusicianDao().update(bandMusician)
    }

    fun deleteBandMusician(bandMusician: BandMusician){
        database.bandMusicianDao().delete(bandMusician)
    }

    fun getMusiciansFromBand(bandId: Long): Flow<List<Musician>> = database
        .bandMusicianDao().getMusiciansFromBand(bandId)

    fun getBandMusicianByBandIdAndMusicianId(bandId: Long, musicianId: Long): BandMusician = database
        .bandMusicianDao().getByBandIdAndMusicianId(bandId, musicianId)
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
}