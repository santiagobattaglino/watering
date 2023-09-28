package com.santiago.battaglino.esp32.domain.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.santiago.battaglino.esp32.domain.model.config.ConfigResponse

@Entity(tableName = "connection") // , indices = [Index(value = ["userId"], unique = true)]
data class Connection(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val userId: Long = 0,
    val batteryLevel: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    @Embedded(prefix = "config")
    val config: ConfigResponse = ConfigResponse()
)

enum class Status(val displayMsg: String) {
    Connected("Ready!"), Connecting("Connecting"), Disconnected("Disconnected"), Streaming("Recording"), StreamingStop(
        "Recording Stopped"
    ),
    CompressingVideo("Compressing Video"), VideoProcessed("Video Processed"), Copying("Receiving Files"), Error(
        "Error"
    )
}