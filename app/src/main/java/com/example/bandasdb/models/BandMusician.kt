package com.example.bandasdb.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Anotações para o banco de dados:
 *
 * @Entity(tableName="Nome da Tabela") -> Diz o nome da tabela para a entidade
 *
 * foreignKeys -> Associa a coluna de outra entidade com a chave estrangeira desta.
 *
 * @PrimaryKey -> Diz que a variável anotaada é uma chave primária
 *
 * @ColumnInfo(name = "Nome da Coluna") -> Diz o nome da coluna na tabela
 *
 *
 */

@Entity(
    tableName = "BandMusician",
    foreignKeys = [
        ForeignKey(
            entity = Band::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("bandId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Musician::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("musicianId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BandMusician(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "bandId")
    val bandId: Long = 0L,

    @ColumnInfo(name = "musicianId")
    val musicianId: Long = 0L
)