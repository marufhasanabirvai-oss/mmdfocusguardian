package com.focusguardian.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocked_words")
data class BlockedWordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val word: String,
    val isExactMatch: Boolean = true, // true = whole word boundaries, false = partial substring
    val isEnabled: Boolean = true,
    val lockUntilTimestamp: Long = 0L,
    val createdAt: Long = System.currentTimeMillis()
)
