package com.example.chatapplication.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.activities.LoginActivity
import com.example.chatapplication.adapters.UserAdapter
import com.example.chatapplication.modules.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    var lst= MutableLiveData<ArrayList<User>>()
    var userList = arrayListOf<User>()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mDbRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("user")


    fun obsData(){
        Log.i("BBB", "This fun is ok")
        mDbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if (mAuth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                        lst.value = userList
                        Log.i("BBB2", lst.value.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        Log.i("BBB1", lst.value.toString())
    }

    fun signOut(){
        mAuth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

}