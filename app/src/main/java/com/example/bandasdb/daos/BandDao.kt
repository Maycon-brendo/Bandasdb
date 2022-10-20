package com.example.bandasdb.daos

import androidx.room.*
import com.example.bandasdb.models.Band
import kotlinx.coroutines.flow.Flow

/**
 * Anotações para o banco de dados:
 *
 * @Dao -> Data Access Object: objeto para acessar dados da tabela
 *
 * @Insert -> Implementa inserção no banco de dados
 *
 * @Update -> Implementa atualização no banco de dados
 *
 * @Delete -> Implementa a remoção no banco de dados
 *
 * @Query -> Faz uma pesquisa como descrita com SQL
 *
 * SQL Tutorial:
 * https://www.w3schools.com/sql/default.asp
 *
 */

@Dao
interface BandDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(band: Band)

    @Update
    fun update(band:Band)

    @Delete
    fun delete(band:Band)

    @Query("SELECT * FROM Band")
    fun getAll() : Flow<List<Band>>

    @Query("SELECT * FROM Band Where id = :id")
    fun getById(id: Long) : Band
}