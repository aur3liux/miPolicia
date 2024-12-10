package com.aur3liux.naats.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PredenunciaTmpDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPredenunciaTmp(predenunciaTmp: PredenunciaTmpData)

    @Update
    fun updatePredenunciaTmp(predenunciaTmp: PredenunciaTmpData)

    @Query("SELECT * FROM ${Store.DB.TB_PREDENUNCIA_TMP}")
    fun getPredenunciaData(): PredenunciaTmpData

    //Limpia la tabla de preguntas
    @Query("DELETE FROM ${Store.DB.TB_PREDENUNCIA_TMP}")
    fun clearPredenunciasTmp()
}