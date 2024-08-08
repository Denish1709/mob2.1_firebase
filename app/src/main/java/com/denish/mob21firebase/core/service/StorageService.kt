package com.denish.mob21firebase.core.service

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.net.URI

class StorageService(
    private val authService: AuthService
) {

    private val storageRef = FirebaseStorage.getInstance().getReference("images/")

    fun createImageName(): String? {
        val uid = authService.getUId() ?: return null
        return uid.take(5) + System.nanoTime()
    }

    fun uploadImage(uri: Uri, name:String?, callBack: (String?) -> Unit) {
        val imageName = name ?: createImageName()
        if (imageName != null) {
            storageRef.child(imageName).putFile(uri)
                .addOnSuccessListener {
                    callBack(imageName)
                }
                .addOnFailureListener {
                    callBack(null)
                }
        }
    }

    fun getImageUri(imageName: String, callBack: (Uri?) -> Unit) {
        storageRef.child(imageName).downloadUrl.addOnSuccessListener {
            callBack(it)
        }
    }
}