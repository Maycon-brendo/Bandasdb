package com.example.bandasdb.daos

import com.example.bandasdb.models.BandMusician
import androidx.room.*
import com.example.bandasdb.models.Band
import com.example.bandasdb.models.Musician


@Dao
interface BandMusicianDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(BandMusician: BandMusician)


    @Update
    fun update(BandMusician: BandMusician)


    @Delete
    fun delete(BandMusician: BandMusician)

    // return all  Band Musicians
    @Query("SELECT * FROM BandMusician")
    fun getAll(): List<BandMusician>

    // Retorna item da tabela onde o id é o requisitado
    @Query("SELECT * FROM BandMusician Where id = :id")
    fun getById(id: Long): BandMusician

    // Retorna todos os ids de  musicos da banda:
    @Query("SELECT musicianId FROM BandMusician WHERE id = :bandId  ")
    fun getMusicianIdsFromBand(bandId: Long): List<Long>

    // Retorna todos os ids de  bandas do musico:
    @Query("SELECT bandId FROM BandMusician WHERE id = :musicianId  ")
    fun getBandIdsFromMusician(musicianId: Long): List<Long>

    // Retorna musicos de banda
    @Query("SELECT * FROM Musician WHERE EXISTS (SELECT * FROM BandMusician WHERE BandMusician.musicianId = Musician.id AND BandMusician.bandId = :bandId )")
    fun getAMusiciansFromBand(bandId: Long): List<Musician>

    // retorna bandas do musico
    @Query("SELECT * FROM Band WHERE EXISTS (SELECT * FROM BandMusician WHERE BandMusician.bandId = Band.id AND BandMusician.musicianId = :musicianId )")
    fun getBandsFromMusician(musicianId: Long): List<Band>
}