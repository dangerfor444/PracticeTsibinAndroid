package com.example.practicetsibin.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicetsibin.data.profile.Profile
import com.example.practicetsibin.di.DIContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    init {
        DIContainer.observeProfileUseCase().onEach { _profile.value = it }.launchIn(viewModelScope)
    }
}

class EditProfileViewModel : ViewModel() {

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    private val _avatarUri = MutableStateFlow("")
    val avatarUri: StateFlow<String> = _avatarUri.asStateFlow()

    fun setFullName(value: String) {
        _fullName.value = value
    }

    fun setAvatar(uri: String) {
        _avatarUri.value = uri
    }

    fun loadCurrent(profile: Profile) {
        _fullName.value = profile.fullName
        _avatarUri.value = profile.avatarUri
    }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {
            DIContainer.updateProfileUseCase(
                Profile(fullName = _fullName.value, avatarUri = _avatarUri.value)
            )
            onDone()
        }
    }
}
