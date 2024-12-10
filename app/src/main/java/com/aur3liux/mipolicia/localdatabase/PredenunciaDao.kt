package com.aur3liux.naats.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PredenunciaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPredenuncia(predenuncia: MyPredenunciaData)

    @Update
    fun updatePredenuncia(predenuncia: MyPredenunciaData)

    @Query("SELECT * FROM ${Store.DB.TB_MYPREDENUNCIAS} where ${Store.PREDENUNCIA.folio}=:folio")
    fun getPredenunciaData(folio: String): MyPredenunciaData

    @Query("SELECT * FROM ${Store.DB.TB_MYPREDENUNCIAS}")
    fun getPredenunciaList(): List<MyPredenunciaData>

    //Limpia la tabla de preguntas
    @Query("DELETE FROM ${Store.DB.TB_MYPREDENUNCIAS}")
    fun clearPredenuncias()
}