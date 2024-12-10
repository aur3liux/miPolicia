package com.aur3liux.mipolicia.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TokenPushDao {
    //Insertar un token
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(sesion: TokenPushData)

    //Lee el valor del token push notification
    @Query("SELECT ${Store.TOKEN_PUSH_NOTIFICATION.TOKEN} FROM ${Store.DB.TB_TOKEN_PUSH} LIMIT 1")
    fun getTokenPushNotif(): String


    //Limpia la tabla de sesion
    @Query("DELETE FROM ${Store.DB.TB_TOKEN_PUSH}")
    fun deleteToken()
}