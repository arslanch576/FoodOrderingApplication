package com.coderobust.foodorderingapplication.model.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    public fun getCurrentUser():FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    public suspend fun signIn(email:String,password:String):Result<FirebaseUser>{
        try {
            val result=FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).await()
            return Result.success(result.user!!)
        }catch (e:Exception){
            return Result.failure(e)
        }

    }

    public suspend fun resetPassword(email:String):Result<Boolean>{
        try {
            val result=FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
            return Result.success(true)
        }catch (e:Exception){
            return Result.failure(e)
        }

    }

    public suspend fun signUp(email:String,password:String,name:String):Result<FirebaseUser>{
        try {
            val result=FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).await()
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            result.user?.updateProfile(profileUpdates)?.await()
            return Result.success(result.user!!)
        }catch (e:Exception){
            return Result.failure(e)
        }

    }

}