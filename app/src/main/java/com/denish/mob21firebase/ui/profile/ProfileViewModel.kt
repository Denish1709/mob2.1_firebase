package com.denish.mob21firebase.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.denish.mob21firebase.data.model.User
import com.denish.mob21firebase.data.repo.UserRepo
import com.denish.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepo
): BaseViewModel() {

    val user = MutableLiveData<User>()

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            errorHandler {
                userRepo.getUser()
            }?.let {
                user.value = it
                Log.d("debugging", it.toString())
            }
        }
    }
    fun updateProfile(imageName: String) {
        viewModelScope.launch {
            errorHandler {
                user.value?.let {
                    userRepo.updateUser(it.copy(profilePic = imageName))
                }
            }
        }
    }
}