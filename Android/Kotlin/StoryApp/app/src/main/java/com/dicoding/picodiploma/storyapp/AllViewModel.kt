package com.dicoding.picodiploma.storyapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AllViewModel (private val preference: UserPreference): ViewModel(){
    fun getUser(): LiveData<UserData>{
        return preference.getUser().asLiveData()
    }
    fun saveUser(userData: UserData){
        viewModelScope.launch {
            preference.saveUser(userData)
        }

    }
    fun logout() {
        viewModelScope.launch {
            preference.logout()
        }
    }
}