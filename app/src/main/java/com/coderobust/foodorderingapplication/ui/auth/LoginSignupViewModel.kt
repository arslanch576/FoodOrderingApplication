package com.coderobust.foodorderingapplication.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignupViewModel @Inject constructor(val authRepository:AuthRepository):ViewModel() {


    val currentUser=MutableStateFlow<FirebaseUser?>(null)
    val failureMessage=MutableStateFlow<String?>(null)

    init {
        currentUser.value=authRepository.getCurrentUser()
    }

    fun login(email:String,password:String){
        if(!email.trim().contains("@")){
            failureMessage.value="Invalid Email"
            return
        }

        if(password.trim().length<6){
            failureMessage.value="Invalid Password"
            return
        }
        viewModelScope.launch {
            val result=authRepository.signIn(email,password)
            if (result.isSuccess)
                currentUser.value=result.getOrNull()
            else
                failureMessage.value=result.exceptionOrNull()?.localizedMessage
        }
    }

    fun signUp(email:String,password:String,confirmPassword:String,name:String){
        if(name.isEmpty()){
            failureMessage.value="Enter name"
            return
        }
        if(!email.trim().contains("@")){
            failureMessage.value="Invalid Email"
            return
        }
        if(password.trim().length<6){
            failureMessage.value="Invalid Password"
            return
        }
        if(!password.equals(confirmPassword)){
            failureMessage.value="Confirm password does not match with password"
            return
        }
        viewModelScope.launch {
            val result=authRepository.signUp(email,password,name)
            if (result.isSuccess)
                currentUser.value=result.getOrNull()
            else
                failureMessage.value=result.exceptionOrNull()?.localizedMessage
        }
    }
}