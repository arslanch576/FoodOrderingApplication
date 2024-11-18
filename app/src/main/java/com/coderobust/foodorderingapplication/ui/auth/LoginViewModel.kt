package com.coderobust.foodorderingapplication.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {
    val authRepository=AuthRepository()

    val currentUser=MutableStateFlow<FirebaseUser?>(null)
    val failureMessage=MutableStateFlow<String?>(null)

    init {
        currentUser.value=authRepository.getCurrentUser()
    }

    fun login(emil:String,password:String){
        viewModelScope.launch {
            val result=authRepository.signIn(emil,password)
            if (result.isSuccess)
                currentUser.value=result.getOrNull()
            else
                failureMessage.value=result.exceptionOrNull()?.localizedMessage
        }
    }
}