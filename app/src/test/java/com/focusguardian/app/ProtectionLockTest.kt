package com.focusguardian.app

import com.focusguardian.app.domain.matching.ProtectionLockManager
import org.junit.Assert.*
import org.junit.Test

class ProtectionLockTest {

    @Test
    fun testActiveLockPreventsDisable() {
        val now = 1_000_000L
        val sevenDaysMs = 7 * 86_400_000L
        val lockUntil = now + sevenDaysMs

        // During the 7 days, lock must be active
        assertTrue(ProtectionLockManager.isLockActive(lockUntil, currentEpochMs = now + 1000))
        assertTrue(ProtectionLockManager.isLockActive(lockUntil, currentEpochMs = now + (3 * 86_400_000L)))

        // After lock expires, isLockActive must return false
        assertFalse(ProtectionLockManager.isLockActive(lockUntil, currentEpochMs = now + sevenDaysMs + 1000))
    }

    @Test
    fun testLockPeriodCalculation() {
        val now = 1_000_000L
        val lock1Day = ProtectionLockManager.calculateLockUntilTimestamp(now, 1)
        assertEquals(now + 86_400_000L, lock1Day)

        val lock30Days = ProtectionLockManager.calculateLockUntilTimestamp(now, 30)
        assertEquals(now + (30 * 86_400_000L), lock30Days)
    }
}
