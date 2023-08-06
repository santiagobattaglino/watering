package com.santiago.battaglino.esp32.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.santiago.battaglino.esp32.domain.entity.Connection
import kotlinx.coroutines.flow.Flow

@Dao
interface ConnectionDao : BaseDao<Connection> {
    @Query("DELETE FROM connection")
    suspend fun nukeTable()

    @Query("SELECT * FROM connection")
    fun getConnections(): Flow<List<Connection>>

    @Query("SELECT * FROM connection WHERE userId = :userId")
    suspend fun getConnection(userId: String): Connection?

    @Query("DELETE FROM connection where userId = :userId")
    fun removeConnection(userId: String): Int
}