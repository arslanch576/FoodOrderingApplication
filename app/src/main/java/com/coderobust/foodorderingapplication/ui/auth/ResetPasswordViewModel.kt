package com.coderobust.foodorderingapplication.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ResetPasswordViewModel(val authRepository: AuthRepository) :ViewModel(){

    val isEmailSent= MutableStateFlow<Boolean?>(null)
    val failureMessage= MutableStateFlow<String?>(null)


    fun resetPassword(email:String){
        if(!email.trim().contains("@")){
            failureMessage.value="Invalid Email"
            return
        }
        viewModelScope.launch {
            val result=authRepository.resetPassword(email)
            if (result.isSuccess)
                isEmailSent.value=result.getOrNull()
            else
                failureMessage.value=result.exceptionOrNull()?.localizedMessage
        }
    }
}