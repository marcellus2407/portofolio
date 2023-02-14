package com.dicoding.picodiploma.storyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory (private val preference: UserPreference) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        return when {
            modelClass.isAssignableFrom(AllViewModel::class.java) -> {
                AllViewModel(preference) as T
            }
            else -> throw IllegalArgumentException("ViewModel class: " + modelClass.name)
        }
    }
}
