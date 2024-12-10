package com.aur3liux.mipolicia.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserData::class,
        SesionData::class,
        TokenPushData::class,
        LocationData::class,
        PredenunciaTmpData::class,
        MyPredenunciaData::class,
        AvisosData::class],
    version = 1,
    exportSchema = true
)

abstract class AppDb: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun sesionDao(): SesionDao
    abstract fun tokenPushDao(): TokenPushDao
    abstract fun locationDao(): LocationDao
    abstract fun predenunciaTmpDao(): PredenunciaTmpDao
    abstract fun myPredenunciaDao(): PredenunciaDao
    abstract fun avisosDao(): AvisosDao
}