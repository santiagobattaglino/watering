package com.santiago.battaglino.esp32.domain.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "connection", indices = [Index(value = ["userId"], unique = true)])
data class Connection(
    @PrimaryKey(autoGenerate = true) var id: Long = 0, val userId: String
)

enum class Status(val displayMsg: String) {
    Connected("Ready!"), Connecting("Connecting"), Disconnected("Disconnected"), Streaming("Recording"), StreamingStop(
        "Recording Stopped"
    ),
    CompressingVideo("Compressing Video"), VideoProcessed("Video Processed"), Copying("Receiving Files"), Error(
        "Error"
    )
}