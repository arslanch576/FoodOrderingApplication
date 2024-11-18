package com.coderobust.foodorderingapplication.model.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {

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

}