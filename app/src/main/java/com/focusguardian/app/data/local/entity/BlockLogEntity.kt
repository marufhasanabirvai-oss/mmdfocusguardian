package com.focusguardian.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "block_logs")
data class BlockLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val triggerType: String, // "website", "word", "image", "adult", "vpn_tor", "uninstall_attempt"
    val details: String,
    val penaltyDurationSeconds: Int,
    val timestamp: Long = System.currentTimeMillis()
)
