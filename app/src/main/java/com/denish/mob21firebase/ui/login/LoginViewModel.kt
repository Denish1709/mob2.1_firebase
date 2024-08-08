package com.denish.mob21firebase.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.denish.mob21firebase.core.service.AuthService
import com.denish.mob21firebase.data.model.User
import com.denish.mob21firebase.data.repo.TodoRepoFirestore
import com.denish.mob21firebase.data.repo.UserRepo
import com.denish.mob21firebase.ui.base.BaseViewModel
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {
    val success: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler{
                validate(email, pass)
                authService.loginWithEmailAndPass(email, pass)
            }?.let {
                success.emit(Unit)
            }
        }
    }

    fun login(credential: GoogleIdTokenCredential) {
        viewModelScope.launch {
            authService.signInWithGoogle(credential)
            userRepo.createUser(
                User(credential.displayName ?: "", "",  credential.id)
            )
            success.emit(Unit)
        }
    }


    private fun validate(email: String, pass: String) {
        if (email.isEmpty() || pass.isEmpty()) {
            throw Exception("Email or password cannot be empty")
        }
    }


}