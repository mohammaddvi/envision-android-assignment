package com.envision.assignment.roomtest

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Document::class],
    version = 1
)
abstract class EnvisionDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao

    companion object {
        private val INSTANCE: EnvisionDatabase? = null
    }
}