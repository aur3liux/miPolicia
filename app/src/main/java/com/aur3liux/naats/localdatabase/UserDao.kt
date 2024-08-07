package com.aur3liux.naats.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aur3liux.naats.Store

@Dao
interface UserDao {
    //Insertar un Promotor
    @Insert
    fun insertUser(user: UserData)

    //Leer datos de un promotor
    @Query("SELECT * FROM ${Store.DB.TB_USER} LIMIT 1")
    fun getUserData(): UserData

    //Devuelve el token Access de un usuario
    @Query("SELECT ${Store.User.TOKEN} FROM ${Store.DB.TB_USER} LIMIT 1")
    fun getTokenAccess(): String

    //Limpia la tabla de usuarios
    @Query("DELETE FROM ${Store.DB.TB_USER}")
    fun deleteAllUser()
}