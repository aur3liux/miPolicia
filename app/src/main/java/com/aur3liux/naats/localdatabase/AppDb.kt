package com.aur3liux.naats.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserData::class,
    SesionData::class],
    version = 1,
    exportSchema = true
)

abstract class AppDb: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun sesionDao(): SesionDao
}