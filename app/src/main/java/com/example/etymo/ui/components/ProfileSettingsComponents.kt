package com.example.etymo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.etymo.ui.theme.*

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun ProfilePhotoSection(
    modifier: Modifier = Modifier, 
    user: com.example.etymo.data.UserEntity,
    onUserUpdate: (com.example.etymo.data.UserEntity) -> Unit,
    onLogout: () -> Unit = {}
) {
    var showEditMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            showEditMenu = false
            if (uri != null) {
                try {
                    context.contentResolver.takePersistableUriPermission(
                        uri, 
                        android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    onUserUpdate(user.copy(profileImageUri = uri.toString()))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            if (user.profileImageUri != null) {
                AsyncImage(
                    model = user.profileImageUri,
                    contentDescription = "Profile Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Photo",
                    tint = EtymoWhite,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.width(20.dp))
        
        Column {
            Text(
                text = user.name.ifBlank { "User" },
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = EtymoDark
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { showEditMenu = true },
                    colors = ButtonDefaults.buttonColors(containerColor = EtymoPurple.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(50.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = null,
                            tint = EtymoPurple,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Edit Photo",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = EtymoPurple
                        )
                    }
                }
                
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(50.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Logout",
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Logout",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
    
    if (showEditMenu) {
        Dialog(
            onDismissRequest = { showEditMenu = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(24.dp).clickable { showEditMenu = false },
                contentAlignment = Alignment.Center
            ) {
                ClayCard(
                    modifier = Modifier.fillMaxWidth().clickable(enabled = false) {},
                    backgroundColor = EtymoWhite,
                    cornerRadius = 24.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = "Edit Profile Photo",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = EtymoDark,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        EditOptionRow(
                            icon = Icons.Default.PhotoLibrary, 
                            text = "Choose from Gallery", 
                            color = EtymoPurple,
                            onClick = { 
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        EditOptionRow(
                            icon = Icons.Default.Delete, 
                            text = "Remove Photo", 
                            color = Color.Red.copy(alpha = 0.8f),
                            onClick = {
                                onUserUpdate(user.copy(profileImageUri = null))
                                showEditMenu = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EditOptionRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, color: Color, onClick: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = EtymoDark)
    }
}

@Composable
fun AppSettingsModal(
    showDialog: Boolean,
    user: com.example.etymo.data.UserEntity,
    onUserUpdate: (com.example.etymo.data.UserEntity) -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            var visible by remember { mutableStateOf(false) }
            
            // State for settings initialized from UserEntity
            var isDarkMode by remember { mutableStateOf(user.isDarkMode) }
            var areNotificationsEnabled by remember { mutableStateOf(user.areNotificationsEnabled) }
            var areSoundsEnabled by remember { mutableStateOf(user.areSoundsEnabled) }
            var usageLimit by remember { mutableStateOf(user.usageLimitMinutes) }

            LaunchedEffect(Unit) {
                visible = true
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(300)) + scaleIn(initialScale = 0.8f, animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(200)) + scaleOut(targetScale = 0.8f, animationSpec = tween(200))
                ) {
                    ClayCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.85f),
                        backgroundColor = EtymoWhite,
                        cornerRadius = 32.dp
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Header
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = null,
                                        tint = EtymoDark,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Text(
                                        text = "App Settings",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EtymoDark
                                    )
                                }
                                IconButton(onClick = onDismiss) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = EtymoDark)
                                }
                            }
                            
                            HorizontalDivider(color = EtymoOffWhite, thickness = 2.dp)
                            
                            // Scrollable Settings Content
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 24.dp, vertical = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                // Appearance Section
                                SettingsSection(title = "Appearance") {
                                    SettingToggleRow(
                                        icon = Icons.Default.DarkMode,
                                        text = "Dark Mode",
                                        isChecked = isDarkMode,
                                        onCheckedChange = { 
                                            isDarkMode = it 
                                            onUserUpdate(user.copy(isDarkMode = it))
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    SettingNavigationRow(
                                        icon = Icons.Default.Language,
                                        text = "Language",
                                        value = user.language
                                    )
                                }
                                
                                // Preferences Section
                                SettingsSection(title = "Preferences") {
                                    SettingToggleRow(
                                        icon = Icons.Default.NotificationsActive,
                                        text = "Push Notifications",
                                        isChecked = areNotificationsEnabled,
                                        onCheckedChange = { 
                                            areNotificationsEnabled = it 
                                            onUserUpdate(user.copy(areNotificationsEnabled = it))
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    SettingToggleRow(
                                        icon = Icons.Default.VolumeUp,
                                        text = "App Sounds",
                                        isChecked = areSoundsEnabled,
                                        onCheckedChange = { 
                                            areSoundsEnabled = it 
                                            onUserUpdate(user.copy(areSoundsEnabled = it))
                                        }
                                    )
                                }
                                
                                // Usage Limits
                                SettingsSection(title = "Daily Usage Limit") {
                                    Text(
                                        text = "${usageLimit.toInt()} minutes",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EtymoPurple
                                    )
                                    Slider(
                                        value = usageLimit,
                                        onValueChange = { 
                                            usageLimit = it 
                                        },
                                        onValueChangeFinished = {
                                            onUserUpdate(user.copy(usageLimitMinutes = usageLimit))
                                        },
                                        valueRange = 10f..120f,
                                        steps = 10,
                                        colors = SliderDefaults.colors(
                                            thumbColor = EtymoPurple,
                                            activeTrackColor = EtymoPurple,
                                            inactiveTrackColor = EtymoOffWhite
                                        )
                                    )
                                    Text(
                                        text = "You will be reminded to take a break after this limit.",
                                        fontSize = 12.sp,
                                        color = EtymoDarkCard.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = EtymoPurple,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        content()
    }
}

@Composable
private fun SettingToggleRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = EtymoDark, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = EtymoDark)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = EtymoWhite,
                checkedTrackColor = EtymoGreen,
                uncheckedThumbColor = EtymoWhite,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}

@Composable
private fun SettingNavigationRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = EtymoDark, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = EtymoDark)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = value, fontSize = 14.sp, color = EtymoDarkCard.copy(alpha = 0.6f))
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = EtymoDarkCard.copy(alpha = 0.6f))
        }
    }
}

@Composable
fun AccountSettingsModal(
    showDialog: Boolean,
    user: com.example.etymo.data.UserEntity,
    onUserUpdate: (com.example.etymo.data.UserEntity) -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            var visible by remember { mutableStateOf(false) }
            
            // Edit state defaults mapped to UserEntity
            var birthdate by remember { mutableStateOf(user.birthdate) }
            var email by remember { mutableStateOf(user.email) }
            var phone by remember { mutableStateOf(user.phone) }

            LaunchedEffect(Unit) {
                visible = true
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(300)) + scaleIn(initialScale = 0.8f, animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(200)) + scaleOut(targetScale = 0.8f, animationSpec = tween(200))
                ) {
                    ClayCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        backgroundColor = EtymoWhite,
                        cornerRadius = 32.dp
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Header
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ManageAccounts,
                                        contentDescription = null,
                                        tint = EtymoPurple,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Text(
                                        text = "Account Settings",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EtymoDark
                                    )
                                }
                                IconButton(onClick = onDismiss) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = EtymoDark)
                                }
                            }
                            
                            HorizontalDivider(color = EtymoOffWhite, thickness = 2.dp)
                            
                            // Scrollable Settings Content
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 24.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                AccountEditField(
                                    label = "Email Address",
                                    value = email,
                                    onValueChange = { email = it },
                                    icon = Icons.Default.Email
                                )
                                
                                AccountEditField(
                                    label = "Phone Number",
                                    value = phone,
                                    onValueChange = { phone = it },
                                    icon = Icons.Default.Phone
                                )
                                
                                AccountEditField(
                                    label = "Birthdate",
                                    value = birthdate,
                                    onValueChange = { birthdate = it },
                                    icon = Icons.Default.Cake
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Button(
                                    onClick = {
                                        onUserUpdate(user.copy(
                                            email = email,
                                            phone = phone,
                                            birthdate = birthdate
                                        ))
                                        onDismiss()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = EtymoPurple),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.fillMaxWidth().height(52.dp)
                                ) {
                                    Text(text = "Save Changes", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AccountEditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = EtymoDarkCard.copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(imageVector = icon, contentDescription = null, tint = EtymoPurple)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EtymoPurple,
                unfocusedBorderColor = EtymoOffWhite,
                focusedContainerColor = EtymoOffWhite.copy(alpha = 0.5f),
                unfocusedContainerColor = EtymoOffWhite.copy(alpha = 0.5f),
                focusedTextColor = EtymoDark,
                unfocusedTextColor = EtymoDark
            ),
            singleLine = true
        )
    }
}
