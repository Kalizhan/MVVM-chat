package com.example.chatapplication.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.chatapplication.activities.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val context = getApplication<Application>().applicationContext

    private fun saveUser() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun login(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Fill the poles", Toast.LENGTH_SHORT).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    init {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            saveUser()
        }
    }

    override fun onCleared() {
        Log.i("AAA", "VM cleared")
        super.onCleared()
    }
}