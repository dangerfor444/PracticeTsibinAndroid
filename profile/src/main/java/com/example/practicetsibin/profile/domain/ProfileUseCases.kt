package com.example.practicetsibin.profile.domain

import com.example.practicetsibin.profile.data.Profile
import com.example.practicetsibin.profile.data.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ObserveProfileUseCase(private val repository: ProfileRepository) {
    operator fun invoke(): Flow<Profile> = repository.profile
}

class UpdateProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(profile: Profile) {
        repository.updateProfile(profile)
    }
}

