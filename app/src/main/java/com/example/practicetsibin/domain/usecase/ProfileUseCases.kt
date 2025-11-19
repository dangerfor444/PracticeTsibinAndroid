package com.example.practicetsibin.domain.usecase

import com.example.practicetsibin.data.profile.Profile
import com.example.practicetsibin.data.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ObserveProfileUseCase(private val repository: ProfileRepository) {
    operator fun invoke(): Flow<Profile> = repository.profile
}

class UpdateProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(profile: Profile) {
        repository.updateProfile(profile)
    }
}
