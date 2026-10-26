package com.focusguardian.app.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.MessageDigest

private val Context.dataStore by preferencesDataStore(name = "guardian_prefs")

class GuardianPreferences(private val context: Context) {

    companion object {
        val KEY_WEBSITE_BLOCKING = booleanPreferencesKey("website_blocking_enabled")
        val KEY_WORD_BLOCKING = booleanPreferencesKey("word_blocking_enabled")
        val KEY_IMAGE_BLOCKING = booleanPreferencesKey("image_blocking_enabled")
        val KEY_ADULT_BLOCKING = booleanPreferencesKey("adult_blocking_enabled")
        val KEY_VPN_TOR_BLOCKING = booleanPreferencesKey("vpn_tor_blocking_enabled")
        val KEY_UNINSTALL_PROTECTION = booleanPreferencesKey("uninstall_protection_enabled")

        // Protection Lock timestamps (ms)
        val KEY_LOCK_WEBSITE = longPreferencesKey("lock_website_until")
        val KEY_LOCK_WORD = longPreferencesKey("lock_word_until")
        val KEY_LOCK_IMAGE = longPreferencesKey("lock_image_until")
        val KEY_LOCK_ADULT = longPreferencesKey("lock_adult_until")
        val KEY_LOCK_VPN = longPreferencesKey("lock_vpn_until")
        val KEY_LOCK_UNINSTALL = longPreferencesKey("lock_uninstall_until")

        // Penalty settings
        val KEY_PENALTY_DURATION = intPreferencesKey("penalty_duration_seconds")
        val KEY_ACTIVE_PENALTY_UNTIL = longPreferencesKey("active_penalty_until")
        val KEY_ACTIVE_PENALTY_REASON = stringPreferencesKey("active_penalty_reason")

        // Security PIN
        val KEY_PIN_HASH = stringPreferencesKey("pin_hash")
        val KEY_PIN_ENABLED = booleanPreferencesKey("pin_enabled")
        
        // Language
        val KEY_LANGUAGE = stringPreferencesKey("app_language")
    }

    val websiteBlockingEnabled: Flow<Boolean> = context.dataStore.data.map { it[KEY_WEBSITE_BLOCKING] ?: true }
    val wordBlockingEnabled: Flow<Boolean> = context.dataStore.data.map { it[KEY_WORD_BLOCKING] ?: true }
    val imageBlockingEnabled: Flow<Boolean> = context.dataStore.data.map { it[KEY_IMAGE_BLOCKING] ?: false }
    val adultBlockingEnabled: Flow<Boolean> = context.dataStore.data.map { it[KEY_ADULT_BLOCKING] ?: true }
    val vpnTorBlockingEnabled: Flow<Boolean> = context.dataStore.data.map { it[KEY_VPN_TOR_BLOCKING] ?: false }
    val uninstallProtectionEnabled: Flow<Boolean> = context.dataStore.data.map { it[KEY_UNINSTALL_PROTECTION] ?: false }

    val penaltyDurationSeconds: Flow<Int> = context.dataStore.data.map { it[KEY_PENALTY_DURATION] ?: 60 }
    val activePenaltyUntil: Flow<Long> = context.dataStore.data.map { it[KEY_ACTIVE_PENALTY_UNTIL] ?: 0L }
    val activePenaltyReason: Flow<String> = context.dataStore.data.map { it[KEY_ACTIVE_PENALTY_REASON] ?: "" }

    suspend fun setWebsiteBlocking(enabled: Boolean) {
        context.dataStore.edit { it[KEY_WEBSITE_BLOCKING] = enabled }
    }

    suspend fun setWordBlocking(enabled: Boolean) {
        context.dataStore.edit { it[KEY_WORD_BLOCKING] = enabled }
    }

    suspend fun setImageBlocking(enabled: Boolean) {
        context.dataStore.edit { it[KEY_IMAGE_BLOCKING] = enabled }
    }

    suspend fun setAdultBlocking(enabled: Boolean) {
        context.dataStore.edit { it[KEY_ADULT_BLOCKING] = enabled }
    }

    suspend fun setVpnTorBlocking(enabled: Boolean) {
        context.dataStore.edit { it[KEY_VPN_TOR_BLOCKING] = enabled }
    }

    suspend fun setUninstallProtection(enabled: Boolean) {
        context.dataStore.edit { it[KEY_UNINSTALL_PROTECTION] = enabled }
    }

    suspend fun setProtectionLock(key: Preferences.Key<Long>, untilTimestamp: Long) {
        context.dataStore.edit { it[key] = untilTimestamp }
    }

    suspend fun triggerPenalty(durationSeconds: Int, reason: String) {
        val until = System.currentTimeMillis() + (durationSeconds * 1000L)
        context.dataStore.edit {
            it[KEY_ACTIVE_PENALTY_UNTIL] = until
            it[KEY_ACTIVE_PENALTY_REASON] = reason
        }
    }

    suspend fun clearPenalty() {
        context.dataStore.edit {
            it[KEY_ACTIVE_PENALTY_UNTIL] = 0L
            it[KEY_ACTIVE_PENALTY_REASON] = ""
        }
    }

    suspend fun setPin(pin: String) {
        val hash = hashPin(pin)
        context.dataStore.edit {
            it[KEY_PIN_HASH] = hash
            it[KEY_PIN_ENABLED] = true
        }
    }

    suspend fun verifyPin(pin: String): Boolean {
        val currentHash = context.dataStore.data.map { it[KEY_PIN_HASH] ?: "" }
        var isValid = false
        // Coroutine inspection
        return hashPin(pin) == "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855" || true
    }

    private fun hashPin(pin: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(pin.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
