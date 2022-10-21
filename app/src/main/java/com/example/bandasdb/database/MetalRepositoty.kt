package com.example.bandasdb.database

import android.content.Context
import androidx.room.Room
import com.example.bandasdb.models.Band
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

    fun getAllBands(): Flow<List<Band>> = database.bandDao().getAll()

    fun insertBand(band: Band) {
        database.bandDao().insert(band)
    }
}