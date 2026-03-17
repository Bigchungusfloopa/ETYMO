package com.example.etymo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
import com.example.etymo.domain.SubscriptionTier

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val password: String = "password",
    val name: String = "Guest",
    val profileImageUri: String? = null,
    val currentTier: SubscriptionTier = SubscriptionTier.FREE,
    val heartsRemaining: Int = 5,
    val streakDays: Int = 12,
    val xp: Int = 4250,
    val wordsMastered: Int = 842,
    val lastHeartRefreshTime: Long = System.currentTimeMillis(),
    
    // Account Settings
    val email: String = "user@etymo.app",
    val phone: String = "+1 555-0198",
    val birthdate: String = "Aug 12, 1998",
    
    // App Preferences
    val isDarkMode: Boolean = false,
    val language: String = "English (US)",
    val areNotificationsEnabled: Boolean = true,
    val areSoundsEnabled: Boolean = true,
    val usageLimitMinutes: Float = 30f
)
