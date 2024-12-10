package com.aur3liux.naats.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateLocation(locationData: LocationData)

    @Query("SELECT * FROM ${Store.DB.TB_LOCATIONS}")
    fun getLocationData(): LocationData

    //Limpia la tabla de preguntas
    @Query("DELETE FROM ${Store.DB.TB_LOCATIONS}")
    fun clearlocations()
}