package com.example.chatapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.modules.User
import com.example.chatapplication.adapters.UserAdapter
import com.example.chatapplication.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var mainvm: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userRecyclerView = findViewById(R.id.userRecyclerView)
        mainvm = ViewModelProvider(this)[MainViewModel::class.java]
        Log.i("BBB", "MainActivity Log")

        mainvm.obsData()
        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun observeData() {
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        mainvm.lst.observe(this, Observer {
            Log.i("BBB", it.toString())
            userRecyclerView.adapter = UserAdapter(mainvm, this, mainvm.userList)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            mainvm.signOut()
            return true
        }
        return true
    }
}