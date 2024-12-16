package com.coderobust.foodorderingapplication.model.repositories

import com.coderobust.foodorderingapplication.model.utils.CloudinaryUploadHelper
import javax.inject.Inject

class StorageRepository @Inject constructor() {
    fun uploadFile(filePath:String,onComplete: (Boolean,String?) -> Unit){
        CloudinaryUploadHelper().uploadFile(filePath,onComplete)
    }
}