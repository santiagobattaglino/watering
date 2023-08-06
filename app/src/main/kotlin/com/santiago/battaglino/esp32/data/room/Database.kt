package com.santiago.battaglino.esp32.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.santiago.battaglino.esp32.data.room.dao.*
import com.santiago.battaglino.esp32.domain.entity.*
import com.santiago.battaglino.esp32.util.Constants

@Database(
    entities = [Connection::class], version = Constants.DB_VERSION
)

@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun connectionDao(): ConnectionDao
}