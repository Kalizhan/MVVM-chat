package com.example.chatapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapplication.modules.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatViewModel(receiverUid: String): ViewModel() {

    var messageLiveData = MutableLiveData<ArrayList<Message>>()
    var messageList = arrayListOf<Message>()
    private var mDbRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("chats")
    var receiveRoom: String? = null
    var senderRoom: String? = null
    val senderUid = FirebaseAuth.getInstance().currentUser?.uid

    init {
        senderRoom = receiverUid + senderUid
        receiveRoom = senderUid + receiverUid
    }

    fun getMessages(){
        mDbRef.child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                        messageLiveData.value = messageList
                        Log.i("BBB", messageLiveData.value.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    fun sendMessages(message: String){
        val messageObject = Message(message, senderUid)

        mDbRef.child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
            mDbRef.child(receiveRoom!!).child("messages").push().setValue(messageObject)
        }
    }

}