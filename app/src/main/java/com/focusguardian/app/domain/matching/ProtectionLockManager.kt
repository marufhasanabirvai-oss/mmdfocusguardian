package com.focusguardian.app.domain.matching

object ProtectionLockManager {

    /**
     * Calculates target epoch timestamp for standard lock options:
     * 1 day, 2 days, 3 days, 7 days, 14 days, 21 days, 30 days
     */
    fun calculateLockUntilTimestamp(currentEpochMs: Long, days: Int): Long {
        require(days > 0) { "Days must be positive" }
        val millisInDay = 86_400_000L
        return currentEpochMs + (days * millisInDay)
    }

    /**
     * Returns true if lock is currently in effect and switch CANNOT be turned OFF.
     */
    fun isLockActive(lockUntilTimestamp: Long, currentEpochMs: Long = System.currentTimeMillis()): Boolean {
        return lockUntilTimestamp > currentEpochMs
    }

    /**
     * Human readable remaining lock time
     */
    fun formatRemainingLockTime(lockUntilTimestamp: Long, currentEpochMs: Long = System.currentTimeMillis()): String {
        val diffMs = lockUntilTimestamp - currentEpochMs
        if (diffMs <= 0) return "Expired"

        val totalSeconds = diffMs / 1000
        val days = totalSeconds / 86400
        val hours = (totalSeconds % 86400) / 3600
        val minutes = (totalSeconds % 3600) / 60

        return when {
            days > 0 -> "$days d, $hours hrs remaining"
            hours > 0 -> "$hours hrs, $minutes min remaining"
            else -> "$minutes min remaining"
        }
    }
}
