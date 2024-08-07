package com.aur3liux.naats.localdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aur3liux.naats.Store

@Entity(tableName = Store.DB.TB_USER)
data class UserData(
    @PrimaryKey @ColumnInfo(name = Store.User.USER) val userName: String,
    @ColumnInfo(name = Store.User.TOKEN) val tokenAccess: String
)

@Entity(tableName = Store.DB.TB_SESION)
data class SesionData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = Store.SESION.STATUS) val status: Boolean
)
