package com.santiago.battaglino.esp32.di

import com.santiago.battaglino.esp32.data.repository.connection.ConnectionRepository
import com.santiago.battaglino.esp32.data.repository.connection.ConnectionRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoriesModule = module {
    single<ConnectionRepository> {
        ConnectionRepositoryImpl(get(named("RetrofitRest")), get())
    }
}
