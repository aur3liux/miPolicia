package com.aur3liux.mipolicia.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    //Insertar un usuario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserData)

    @Update
    fun updateUser(user: UserData)

    //Leer datos de un usuario
    @Query("SELECT * FROM ${Store.DB.TB_USER} LIMIT 1")
    fun getUserData(): UserData

    //Devuelve el token Access de un usuario
    @Query("SELECT ${Store.User.TOKEN} FROM ${Store.DB.TB_USER} LIMIT 1")
    fun getTokenAccess(): String

    //Limpia la tabla de usuarios
    @Query("DELETE FROM ${Store.DB.TB_USER}")
    fun deleteAllUser()
}