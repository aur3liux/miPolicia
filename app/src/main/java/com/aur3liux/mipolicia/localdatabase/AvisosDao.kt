package com.aur3liux.naats.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AvisosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAviso(aviso: AvisosData)

    @Query("SELECT * FROM ${Store.DB.TB_AVISOS} where ${Store.AVISOS.ref}=:ref")
    fun getAvisoData(ref: Int): AvisosData

    @Query("SELECT * FROM ${Store.DB.TB_AVISOS}")
    fun getAvisosList(): List<AvisosData>

    //Limpia la tabla de preguntas
    @Query("DELETE FROM ${Store.DB.TB_AVISOS}")
    fun clearAvisos()
}