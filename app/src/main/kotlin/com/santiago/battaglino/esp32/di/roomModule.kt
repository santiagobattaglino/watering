package com.santiago.battaglino.esp32.di

import androidx.room.Room
import com.santiago.battaglino.esp32.data.room.Database
import com.santiago.battaglino.esp32.util.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {

    single {
        Room.databaseBuilder(androidApplication(), Database::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration().build()
    }

    single { get<Database>().connectionDao() }
}