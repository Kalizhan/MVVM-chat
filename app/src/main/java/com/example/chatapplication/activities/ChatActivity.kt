package com.example.chatapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.modules.Message
import com.example.chatapplication.adapters.MessageAdapter
import com.example.chatapplication.R
import com.example.chatapplication.viewmodel.ChatViewModel
import com.example.chatapplication.viewmodel.ChatViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var messageRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageRecyclerView = findViewById(R.id.chatRecyclerView)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        supportActionBar?.title = name

        chatViewModel = ViewModelProvider(this, ChatViewModelFactory("$receiverUid"))[ChatViewModel::class.java]

        chatViewModel.getMessages()
        getFullMessages()

        sentButton.setOnClickListener{
            val message = messageBox.text.toString()

            chatViewModel.sendMessages(message)

            messageBox.setText("")
        }
    }

    private fun getFullMessages(){
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatViewModel.messageLiveData.observe(this, {
            Log.i("BBB", it.toString())
            chatRecyclerView.adapter = MessageAdapter(this, it)
        })

    }
}