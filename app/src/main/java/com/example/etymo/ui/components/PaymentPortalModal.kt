package com.example.etymo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.etymo.domain.SubscriptionTier
import com.example.etymo.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class PaymentMethod(val title: String, val icon: ImageVector) {
    UPI("UPI", Icons.Default.QrCode),
    CARD("Credit/Debit Card", Icons.Default.CreditCard),
    NET_BANKING("Net Banking", Icons.Default.AccountBalance)
}

@Composable
fun PaymentPortalModal(
    showDialog: Boolean,
    plan: SubscriptionTier,
    onDismiss: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    if (showDialog) {
        var selectedMethod by remember { mutableStateOf(PaymentMethod.UPI) }
        var isProcessing by remember { mutableStateOf(false) }
        var cardNumber by remember { mutableStateOf("") }
        var expiryDate by remember { mutableStateOf("") }
        var cvvCode by remember { mutableStateOf("") }
        var upiId by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberScrollState()
        
        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            if (bitmap != null) {
                upiId = "scannedcode@upi"
            }
        }

        val isPaymentValid = when (selectedMethod) {
            PaymentMethod.UPI -> upiId.isNotBlank() && upiId.contains("@")
            PaymentMethod.CARD -> cardNumber.isNotBlank() && cardNumber.length >= 16 && expiryDate.length == 5 && cvvCode.length >= 3
            PaymentMethod.NET_BANKING -> true
        }

        Dialog(
            onDismissRequest = { if (!isProcessing) onDismiss() },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = !isProcessing,
                dismissOnClickOutside = !isProcessing
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                GlassCard(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f),
                    blurRadius = 40.dp,
                    cornerRadius = 32.dp,
                    backgroundAlpha = 0.95f,
                    borderAlpha = 0.4f
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                            .padding(24.dp)
                    ) {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Secure Checkout",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = EtymoDark
                            )
                            if (!isProcessing) {
                                IconButton(
                                    onClick = onDismiss,
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(EtymoOffWhite)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = EtymoDark,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Amount section
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = plan.title,
                                fontSize = 16.sp,
                                color = EtymoDarkCard,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "₹${plan.priceMonthly}",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = EtymoDark
                            )
                            Text(
                                text = "Billed securely every month",
                                fontSize = 12.sp,
                                color = EtymoDarkCard
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "Select Payment Method",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = EtymoDark,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        // Payment Methods
                        PaymentMethod.values().forEach { method ->
                            PaymentMethodItem(
                                method = method,
                                isSelected = selectedMethod == method,
                                onClick = { 
                                    if (!isProcessing) {
                                        selectedMethod = method
                                        cardNumber = ""
                                        upiId = ""
                                    }
                                }
                            )
                        }

                        // Payment Details Inputs
                        AnimatedVisibility(visible = selectedMethod == PaymentMethod.UPI) {
                            OutlinedTextField(
                                value = upiId,
                                onValueChange = { upiId = it },
                                label = { Text("Enter UPI ID (e.g., name@bank)") },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.QrCodeScanner,
                                        contentDescription = "Scan QR",
                                        tint = EtymoPurple,
                                        modifier = Modifier.clickable {
                                            cameraLauncher.launch(null)
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = EtymoYellow,
                                    unfocusedBorderColor = EtymoOffWhite
                                ),
                                enabled = !isProcessing
                            )
                        }

                        AnimatedVisibility(visible = selectedMethod == PaymentMethod.CARD) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = cardNumber,
                                    onValueChange = { 
                                        if (it.length <= 16 && it.all { char -> char.isDigit() }) cardNumber = it 
                                    },
                                    label = { Text("Enter 16-Digit Card Number") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = EtymoYellow,
                                        unfocusedBorderColor = EtymoOffWhite
                                    ),
                                    enabled = !isProcessing
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    OutlinedTextField(
                                        value = expiryDate,
                                        onValueChange = { 
                                            if (it.length <= 5) expiryDate = it 
                                        },
                                        label = { Text("MM/YY") },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = EtymoYellow,
                                            unfocusedBorderColor = EtymoOffWhite
                                        ),
                                        enabled = !isProcessing
                                    )
                                    OutlinedTextField(
                                        value = cvvCode,
                                        onValueChange = { 
                                            if (it.length <= 4 && it.all { char -> char.isDigit() }) cvvCode = it 
                                        },
                                        label = { Text("CVV") },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = EtymoYellow,
                                            unfocusedBorderColor = EtymoOffWhite
                                        ),
                                        enabled = !isProcessing
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Pay Button
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(28.dp))
                                .background(
                                    if (isProcessing || !isPaymentValid) EtymoDarkCard.copy(alpha = 0.5f)
                                    else EtymoDark
                                )
                                .clickable(enabled = !isProcessing && isPaymentValid) {
                                    isProcessing = true
                                    coroutineScope.launch {
                                        delay(1500) // Simulate network delay
                                        isProcessing = false
                                        onPaymentSuccess()
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isProcessing) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = EtymoWhite,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "Pay ₹${plan.priceMonthly}",
                                    color = EtymoWhite,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentMethodItem(
    method: PaymentMethod,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) EtymoYellow else EtymoOffWhite
    val backgroundColor = if (isSelected) EtymoYellow.copy(alpha = 0.1f) else EtymoWhite

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = method.icon,
            contentDescription = method.title,
            tint = if (isSelected) EtymoDark else EtymoDarkCard,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = method.title,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = EtymoDark,
            modifier = Modifier.weight(1f)
        )
        // Custom Radio Button
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (isSelected) EtymoYellow else EtymoOffWhite,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(EtymoYellow)
                )
            }
        }
    }
}
