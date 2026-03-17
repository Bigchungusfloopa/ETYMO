package com.example.etymo.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.etymo.data.AppDatabase
import com.example.etymo.data.UserEntity
import com.example.etymo.domain.SubscriptionTier
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val prefs: SharedPreferences = application.getSharedPreferences("etymo_prefs", Context.MODE_PRIVATE)

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()
    
    private val _isInitializing = MutableStateFlow(true)
    val isInitializing: StateFlow<Boolean> = _isInitializing.asStateFlow()

    private var observeJob: Job? = null

    init {
        val loggedInId = prefs.getString("user_id", null)
        if (loggedInId != null) {
            startObservingUser(loggedInId)
        } else {
            _isInitializing.value = false
        }
    }

    private fun startObservingUser(userId: String) {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            userDao.getUserById(userId).collect { user ->
                _currentUser.value = user
                _isInitializing.value = false
            }
        }
    }

    fun login(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUserByEmailAndPassword(email, pass)
            if (user != null) {
                prefs.edit().putString("user_id", user.id).apply()
                startObservingUser(user.id)
                onResult(true, "Success")
            } else {
                onResult(false, "Invalid credentials")
            }
        }
    }

    fun signup(name: String, email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val existing = userDao.getUserByEmail(email)
            if (existing != null) {
                onResult(false, "Email already exists")
                return@launch
            }
            val newUser = UserEntity(name = name, email = email, password = pass)
            userDao.insertUser(newUser)
            prefs.edit().putString("user_id", newUser.id).apply()
            startObservingUser(newUser.id)
            onResult(true, "Success")
        }
    }

    fun logout() {
        observeJob?.cancel()
        prefs.edit().remove("user_id").apply()
        _currentUser.value = null
    }
    fun updateSubscriptionTier(tier: SubscriptionTier) {
        viewModelScope.launch {
            val user = _currentUser.value
            if (user != null) {
                userDao.updateSubscriptionTier(tier.name, user.id)
            }
        }
    }

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            userDao.updateUser(user)
        }
    }
}
