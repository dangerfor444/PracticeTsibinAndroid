package com.example.practicetsibin.profile.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicetsibin.profile.data.Profile
import com.example.practicetsibin.profile.domain.ObserveProfileUseCase
import com.example.practicetsibin.profile.domain.UpdateProfileUseCase
import com.example.practicetsibin.profile.notification.AlarmScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val observeProfileUseCase: ObserveProfileUseCase
) : ViewModel() {

    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    init {
        observeProfileUseCase().onEach { _profile.value = it }.launchIn(viewModelScope)
    }
}

class EditProfileViewModel(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val reminderReceiverClass: Class<*>
) : ViewModel() {

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    private val _avatarUri = MutableStateFlow("")
    val avatarUri: StateFlow<String> = _avatarUri.asStateFlow()

    private val _reminderTime = MutableStateFlow("")
    val reminderTime: StateFlow<String> = _reminderTime.asStateFlow()

    private val _timeError = MutableStateFlow<String?>(null)
    val timeError: StateFlow<String?> = _timeError.asStateFlow()

    fun setFullName(value: String) {
        _fullName.value = value
    }

    fun setAvatar(uri: String) {
        _avatarUri.value = uri
    }

    fun setReminderTime(value: String) {
        _reminderTime.value = value
        validateTime(value)
    }

    private fun validateTime(time: String) {
        _timeError.value = when {
            time.isEmpty() -> null
            !time.matches(Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) -> "Неверный формат времени (HH:mm)"
            else -> null
        }
    }

    fun loadCurrent(profile: Profile) {
        _fullName.value = profile.fullName
        _avatarUri.value = profile.avatarUri
        _reminderTime.value = profile.reminderTime
    }

    fun save(context: Context, onDone: () -> Unit) {
        validateTime(_reminderTime.value)
        if (_timeError.value != null && _reminderTime.value.isNotEmpty()) {
            return
        }
        viewModelScope.launch {
            val profile = Profile(
                fullName = _fullName.value,
                avatarUri = _avatarUri.value,
                reminderTime = _reminderTime.value
            )
            updateProfileUseCase(profile)

            if (_reminderTime.value.isNotEmpty()) {
                val timeParts = _reminderTime.value.split(":")
                val hour = timeParts[0].toInt()
                val minute = timeParts[1].toInt()
                AlarmScheduler.scheduleReminder(
                    context,
                    hour,
                    minute,
                    _fullName.value,
                    reminderReceiverClass
                )
            }

            onDone()
        }
    }
}

