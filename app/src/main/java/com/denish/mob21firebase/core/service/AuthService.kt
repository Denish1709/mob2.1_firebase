package com.denish.mob21firebase.core.service

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.credentials.CredentialManager
import com.denish.mob21firebase.data.model.User
import com.denish.mob21firebase.data.repo.UserRepo
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthService(
    private val context: Context
) {
    private val auth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)
    suspend fun createUserWithEmailAndPass(email: String, pass: String): Boolean {
        val res = auth.createUserWithEmailAndPassword(
            email,pass
        ).await()

        return res.user != null
    }

    suspend fun loginWithEmailAndPass(email: String, pass: String): FirebaseUser? {
        val res = auth.signInWithEmailAndPassword(email, pass).await()
        return res.user
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

     fun logout() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getUId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun signInWithGoogle(credential: GoogleIdTokenCredential) {
        val firebaseCredential = GoogleAuthProvider.getCredential(credential.idToken, null)
        auth.signInWithCredential(firebaseCredential).await()
    }

    }