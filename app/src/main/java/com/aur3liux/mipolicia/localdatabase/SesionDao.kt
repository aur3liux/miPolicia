package com.aur3liux.naats.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SesionDao {
    //Insertar una sesion
    @Insert
    fun insertSesion(sesion: SesionData)

    //Lee el estado de la sesion
    @Query("SELECT ${Store.SESION.STATUS} FROM ${Store.DB.TB_SESION} LIMIT 1")
    fun getSessionStatus(): Int

    @Update
    fun upDateSession(sesion: SesionData)

    //Limpia la tabla de sesion
    @Query("DELETE FROM ${Store.DB.TB_SESION}")
    fun deleteSesion()
}