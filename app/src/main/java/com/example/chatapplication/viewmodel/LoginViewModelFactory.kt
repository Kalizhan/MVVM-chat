package com.example.chatapplication.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory(application: Application): ViewModelProvider.Factory {

    val app = application

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(app) as T
    }
}