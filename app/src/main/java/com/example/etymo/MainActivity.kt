package com.example.etymo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Park
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.screens.EtymoScreen
import com.example.etymo.screens.LearnScreen
import com.example.etymo.screens.ProfileScreen
import com.example.etymo.screens.ScriptScreen
import com.example.etymo.ui.components.ClayCard
import com.example.etymo.ui.theme.*

enum class EtymoTab(
    val label: String,
    val icon: ImageVector,
) {
    LEARN("Learn", Icons.Default.Explore),
    SCRIPT("Script", Icons.Default.AutoStories),
    ETYMO("Etymo", Icons.Default.Park),
    PROFILE("Profile", Icons.Default.AccountCircle),
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ETYMOTheme {
                EtymoApp()
            }
        }
    }
}

@Composable
fun EtymoApp() {
    var currentTab by rememberSaveable { mutableStateOf(EtymoTab.LEARN) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Screen content
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (currentTab) {
                EtymoTab.LEARN -> LearnScreen()
                EtymoTab.SCRIPT -> ScriptScreen()
                EtymoTab.ETYMO -> EtymoScreen()
                EtymoTab.PROFILE -> ProfileScreen()
            }
        }

        // Claymorphism bottom nav bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = EtymoWhite,
                cornerRadius = 32.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EtymoTab.entries.forEach { tab ->
                        val isSelected = tab == currentTab
                        NavItem(
                            tab = tab,
                            isSelected = isSelected,
                            onClick = { currentTab = tab }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavItem(tab: EtymoTab, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .then(
                if (isSelected) Modifier.background(EtymoYellow) else Modifier
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = tab.label,
                    tint = EtymoDark,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = tab.label,
                    color = EtymoDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        } else {
            Icon(
                imageVector = tab.icon,
                contentDescription = tab.label,
                tint = EtymoDarkCard.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}