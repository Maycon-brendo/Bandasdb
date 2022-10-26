package com.example.bandasdb.daos

import androidx.room.*
import com.example.bandasdb.models.Musician
import kotlinx.coroutines.flow.Flow


@Dao
interface MusicianDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(musician: Musician)

    @Update
    fun update(musician: Musician)

    @Delete
    fun delete(musician: Musician)

    @Query("SELECT * FROM Musician")
    fun getAll() : Flow<List<Musician>>

    @Query("SELECT * FROM Musician Where id = :id")
    fun getById(id: Long): Musician

    @Query("SELECT * FROM Musician WHERE name like :input")
    fun getListByName(input: String): Flow<List<Musician>>

}