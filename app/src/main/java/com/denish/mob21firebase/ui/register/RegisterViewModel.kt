package com.denish.mob21firebase.ui.register

import androidx.lifecycle.viewModelScope
import com.denish.mob21firebase.core.service.AuthService
import com.denish.mob21firebase.core.utils.ValidationUtil
import com.denish.mob21firebase.data.model.User
import com.denish.mob21firebase.data.model.Field
import com.denish.mob21firebase.data.repo.UserRepo
import com.denish.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {

    val success: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        pass: String,
        confirmPassword: String
    ) {
        val error = ValidationUtil.validate(
            Field(firstName, "[a-zA-z ]{2,20}", "Enter a valid name"),
            Field(lastName, "[a-zA-z ]{2,20}", "Enter a valid name"),
            Field(email, "[a-zA-Z0-9 ]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+", "Enter a valid email"),
            Field(pass, "[a-zA-Z0-9#$%]{3,20}", "Enter a valid password")
        )

        if (error == null) {
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler {
                    validate(firstName, lastName ,email, pass, confirmPassword)
                    authService.createUserWithEmailAndPass(email, pass)
                }?.let {
                    userRepo.createUser(
                        User(firstName, lastName, email)
                    )
                    success.emit(Unit)
                }
            }
        } else {
            viewModelScope.launch {
                _error.emit(error)
            }
        }
    }


    private fun validate(firstName: String, lastName: String ,email: String, pass: String, confirmPassword: String) {
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()|| pass.isEmpty()|| confirmPassword.isEmpty() || confirmPassword != pass) {
            throw Exception("Please check the entered credential")
        }
    }
}