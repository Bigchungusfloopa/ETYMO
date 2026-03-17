package com.example.etymo.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.ui.theme.*
import com.example.etymo.viewmodels.UserViewModel

@Composable
fun AuthScreen(
    viewModel: UserViewModel,
    initialIsSignUp: Boolean,
    onBack: () -> Unit
) {
    var isSignUp by remember { mutableStateOf(initialIsSignUp) }
    
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EtymoOffWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Top Bar
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, 
                    contentDescription = "Back",
                    tint = EtymoDark
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = if (isSignUp) "Create Account" else "Welcome Back",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = EtymoDark
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = if (isSignUp) "Sign up to start learning organically." else "Log in to pick up where you left off.",
                fontSize = 16.sp,
                color = EtymoDarkCard
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Inputs
            AnimatedVisibility(
                visible = isSignUp,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = EtymoPurple) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EtymoPurple,
                        unfocusedBorderColor = EtymoWhite,
                        focusedContainerColor = EtymoWhite,
                        unfocusedContainerColor = EtymoWhite,
                        focusedTextColor = EtymoDark,
                        unfocusedTextColor = EtymoDark
                    )
                )
            }
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = EtymoPurple) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EtymoPurple,
                    unfocusedBorderColor = EtymoWhite,
                    focusedContainerColor = EtymoWhite,
                    unfocusedContainerColor = EtymoWhite,
                    focusedTextColor = EtymoDark,
                    unfocusedTextColor = EtymoDark
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = EtymoPurple) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EtymoPurple,
                    unfocusedBorderColor = EtymoWhite,
                    focusedContainerColor = EtymoWhite,
                    unfocusedContainerColor = EtymoWhite,
                    focusedTextColor = EtymoDark,
                    unfocusedTextColor = EtymoDark
                )
            )
            
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Action Button
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank() || (isSignUp && name.isBlank())) {
                        errorMessage = "Please fill out all fields."
                        return@Button
                    }
                    
                    isLoading = true
                    errorMessage = null
                    
                    if (isSignUp) {
                        viewModel.signup(name, email, password) { success, msg ->
                            isLoading = false
                            if (!success) errorMessage = msg
                        }
                    } else {
                        viewModel.login(email, password) { success, msg ->
                            isLoading = false
                            if (!success) errorMessage = msg
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(32.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = EtymoPurple),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = EtymoWhite, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = if (isSignUp) "Sign Up" else "Log In",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = EtymoWhite
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isSignUp) "Already have an account? " else "Don't have an account? ",
                    color = EtymoDarkCard
                )
                Text(
                    text = if (isSignUp) "Log in" else "Sign up",
                    color = EtymoPurple,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { 
                        isSignUp = !isSignUp 
                        errorMessage = null
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
