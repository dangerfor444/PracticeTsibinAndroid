package com.example.practicetsibin.ui.screens

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.practicetsibin.feature.profile.EditProfileViewModel
import com.example.practicetsibin.feature.profile.ProfileViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileScreen(
    onEdit: () -> Unit
) {
    val vm: ProfileViewModel = viewModel()
    val profile by vm.profile.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            val avatarUri = profile.avatarUri
            if (avatarUri.isNotBlank()) {
                AsyncImage(
                    model = avatarUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = if (profile.fullName.isNotBlank()) profile.fullName else "Имя не указано")
        }
    }
}

@Composable
fun EditProfileScreen(
    onDone: () -> Unit,
    onBack: () -> Unit
) {
    val vm: EditProfileViewModel = viewModel()

    val profileVm: ProfileViewModel = viewModel()
    val current by profileVm.profile.collectAsState()
    LaunchedEffect(current) {
        vm.loadCurrent(current)
    }

    val fullName by vm.fullName.collectAsState()
    val avatarUri by vm.avatarUri.collectAsState()

    val context = LocalContext.current

    var showPickerDialog by remember { mutableStateOf(false) }
    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let { vm.setAvatar(it.toString()) }
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            pendingCameraUri?.let { vm.setAvatar(it.toString()) }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { grantedMap ->
        val allGranted = grantedMap.values.all { it }
        if (allGranted) {
            val photoUri = createImageUri(context)
            pendingCameraUri = photoUri
            takePictureLauncher.launch(photoUri)
        }
    }

    fun onPickFromGallery() {
        showPickerDialog = false
        pickImageLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    fun onPickFromCamera() {
        showPickerDialog = false
        val permissions = mutableListOf(android.Manifest.permission.CAMERA)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(android.Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        permissionLauncher.launch(permissions.toTypedArray())
    }

    Scaffold(
        topBar = {},
        bottomBar = {}
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (avatarUri.isNotBlank()) {
                AsyncImage(
                    model = avatarUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .clickable { showPickerDialog = true },
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .clickable { showPickerDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = vm::setFullName,
                label = { Text("Name") },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { vm.save(onDone) }) {
                Text("Готово")
            }
        }
    }

    if (showPickerDialog) {
        AlertDialog(
            onDismissRequest = { showPickerDialog = false },
            confirmButton = {
                Button(onClick = { onPickFromGallery() }) { Text("Галерея") }
            },
            dismissButton = {
                Button(onClick = { onPickFromCamera() }) { Text("Камера") }
            },
            title = { Text("Выбрать аватар") },
            text = { Text("Выберите источник изображения") }
        )
    }
}

private fun createImageUri(context: android.content.Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imagesDir = File(context.cacheDir, "images").apply { mkdirs() }
    val file = File(imagesDir, "IMG_${'$'}timeStamp.jpg")
    return FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
}
