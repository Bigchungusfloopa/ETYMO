package com.example.etymo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.ui.components.AccountSettingsModal
import com.example.etymo.ui.components.BadgesSection
import com.example.etymo.ui.components.ClayCard
import com.example.etymo.ui.components.GlassCard
import com.example.etymo.ui.components.CreditCard
import com.example.etymo.ui.components.ProfilePhotoSection
import com.example.etymo.ui.components.AppSettingsModal
import com.example.etymo.ui.components.StreakCard
import com.example.etymo.ui.components.WordsMasteredCard
import com.example.etymo.ui.theme.*

@Composable
fun ProfileScreen() {
    var showAppSettings by remember { mutableStateOf(false) }
    var showAccountSettings by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EtymoOffWhite)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(top = 48.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    text = "Dashboard",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = EtymoDark
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Stats row (Streak & XP)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StreakCard(
                        modifier = Modifier.weight(1f),
                        streakDays = 12
                    )
                    CreditCard(
                        modifier = Modifier.weight(1f),
                        xp = 4250
                    )
                }
            }

            // Words Mastered
            item {
                WordsMasteredCard(
                    modifier = Modifier.fillMaxWidth(),
                    words = 842,
                    isLarge = true
                )
            }

            // Unlocked Badges
            item {
                BadgesSection(modifier = Modifier.fillMaxWidth())
            }

            // Subscription & Settings
            item {
                Text(
                    text = "Account",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = EtymoDark,
                    modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
                )
                
                ProfilePhotoSection(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp))
                
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    blurRadius = 30.dp,
                    cornerRadius = 24.dp,
                    backgroundAlpha = 0.5f,
                    borderAlpha = 0.3f
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        SettingsRowItem(
                            icon = Icons.Rounded.WorkspacePremium,
                            title = "Subscription Info",
                            subtitle = "Pro Plan (Active)",
                            tint = EtymoPurple
                        )
                        Divider(
                            color = EtymoOffWhite,
                            thickness = 2.dp,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                        )
                        Box(modifier = Modifier.clickable { showAccountSettings = true }) {
                            SettingsRowItem(
                                icon = Icons.Default.ManageAccounts,
                                title = "Account Settings",
                                subtitle = "Personal Info, Email, Birthdate",
                                tint = EtymoDark
                            )
                        }
                        Divider(
                            color = EtymoOffWhite,
                            thickness = 2.dp,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                        )
                        Box(modifier = Modifier.clickable { showAppSettings = true }) {
                            SettingsRowItem(
                                icon = Icons.Default.Settings,
                                title = "App Settings",
                                subtitle = "Notifications, Language, Theme",
                                tint = EtymoDark
                            )
                        }
                    }
                }
            }
        }
        
        AppSettingsModal(
            showDialog = showAppSettings,
            onDismiss = { showAppSettings = false }
        )
        
        AccountSettingsModal(
            showDialog = showAccountSettings,
            onDismiss = { showAccountSettings = false }
        )
    }
}




@Composable
fun SettingsRowItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    tint: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(EtymoOffWhite),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = EtymoDark
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = EtymoDarkCard
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Go",
            tint = EtymoDarkCard
        )
    }
}
