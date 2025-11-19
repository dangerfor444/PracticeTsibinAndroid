package com.example.practicetsibin.data.profile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.profileDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_profile")

class ProfileRepository(private val context: Context) {

    private val fullNameKey = stringPreferencesKey("full_name")
    private val avatarUriKey = stringPreferencesKey("avatar_uri")

    val profile: Flow<Profile> = context.profileDataStore.data.map { prefs ->
        Profile(
            fullName = prefs[fullNameKey] ?: "",
            avatarUri = prefs[avatarUriKey] ?: ""
        )
    }

    suspend fun updateProfile(profile: Profile) {
        context.profileDataStore.edit { prefs ->
            prefs[fullNameKey] = profile.fullName
            prefs[avatarUriKey] = profile.avatarUri
        }
    }

    suspend fun clearProfile() {
        context.profileDataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
