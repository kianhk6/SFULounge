package com.example.sfulounge.data

import android.net.Uri
import android.util.Log
import com.example.sfulounge.R
import com.example.sfulounge.data.model.Message
import com.example.sfulounge.data.model.User
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

class MessageRepository {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    private var registration: ListenerRegistration? = null

    interface MessagesListener {
        fun onNewMessage(message: Message)
    }

    fun getCurrentUserUid(): String {
        val user = auth.currentUser ?: throw IllegalStateException("User cannot be null")
        return user.uid
    }

    fun getUsers(
        userIds: List<String>,
        onComplete: (List<User>) -> Unit
    ) {
        DatabaseHelper.getUsers(db, userIds, onComplete)
    }

    fun sendMessage(
        chatRoomId: String,
        message: Message,
        images: List<Uri> = ArrayList(),
        onSuccess: () -> Unit,
        onError: (Result.Error) -> Unit
    ) {
        val ref = db.collection("chat_rooms")
            .document(chatRoomId)
            .collection("messages")

        ref.add(message)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                val messageId = task.result.id

                // update the chatroom to show most recent message
                message.messageId = messageId
                addMessageToChatRoom(chatRoomId, message)

                ref.document(messageId)
                    .update(
                        mapOf(
                            "messageId" to messageId
                        )
                    )
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // upload any images
                    for (img in images) {
                        uploadPhoto(chatRoomId, message.messageId, img)
                    }
                    onSuccess()
                } else {
                    onError(Result.Error(R.string.error_message_message_send))
                }
            }
    }

    fun updateMemberLastMessageSeenTime(chatRoomId: String, memberId: String) {
        db.collection("chat_rooms")
                .document(chatRoomId)
                .update("memberInfo.${memberId}.lastMessageSeenTime", Timestamp.now())
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.e("error", "updateMemberLastMessageSeenTime: ${task.exception}")
                    }
                }
    }

    private fun addMessageToChatRoom(
        chatRoomId: String,
        message: Message
    ) {
        db.collection("chat_rooms")
            .document(chatRoomId)
            .update(
                mapOf(
                    "lastMessageSentTime" to message.timeCreated,
                    "mostRecentMessage" to message
                )
            )
    }

    fun registerMessagesListener(chatRoomId: String, listener: MessagesListener) {
        registration = db.collection("chat_rooms")
            .document(chatRoomId)
            .collection("messages")
            .orderBy("timeCreated", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.e("error", "message listener failed: ${e.message}")
                    return@addSnapshotListener
                }
                if (value != null) {
                    if (value.documents.isNotEmpty()) {
                        val message = value.documents.first().toObject(Message::class.java)!!
                        listener.onNewMessage(message)
                    }
                }
            }
    }

    fun unregisterMessagesListener() {
        registration?.remove()
    }

    private fun uploadPhoto(
        chatRoomId: String,
        messageId: String,
        photoUri: Uri
    ) {
        val ref = storage.reference
        val photoUid = UUID.randomUUID().toString()

        // first get the firebase url then upload to the firebase storage
        val node = ref.child(
            "chat_rooms/${chatRoomId}/messages/${messageId}/photos/${photoUid}.jpg"
        )
        node.putFile(photoUri)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                node.downloadUrl
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val url = task.result.toString()
                    addPhotoUrlToMessage(chatRoomId, messageId, url)
                } else {
                    Log.e("error", "${task.exception}")
                }
            }
    }

    private fun addPhotoUrlToMessage(chatRoomId: String, messageId: String, url: String) {
        db.collection("chat_rooms")
            .document(chatRoomId)
            .collection("messages")
            .document(messageId)
            .update(
                mapOf(
                    "images" to FieldValue.arrayUnion(url)
                )
            )
    }
}