package com.example.sfulounge.ui

import android.graphics.Typeface
import android.widget.TextView
import com.example.sfulounge.data.model.ChatRoom
import com.example.sfulounge.data.model.Message
import com.example.sfulounge.data.model.User
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

object MessageFormatter {
    fun displayMessage(textView: TextView, message: Message?, isMessageSeen: Boolean) {
        textView.setTypeface(null, if (isMessageSeen) Typeface.NORMAL else Typeface.BOLD)
        textView.text = if (message == null) {
            "Start chatting!"
        } else {
            message.text ?: "upload"
        }
    }

    fun formatMessageTime(time: Timestamp): String {
        val formatter = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault(Locale.Category.FORMAT)
        )
        return formatter.format(time.toDate())
    }

    fun formatTime(chatRoom: ChatRoom): String {
        val lastMessageSentTime = chatRoom.lastMessageSentTime
        val formatter = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault(Locale.Category.FORMAT)
        )
        return if (lastMessageSentTime == null) {
            formatter.format(chatRoom.timeCreated.toDate())
        } else {
            formatter.format(lastMessageSentTime.toDate())
        }
    }

    fun formatNames(members: List<User>, currentUserId: String): String {
        val users = members.filter { x -> x.userId != currentUserId }
        if (users.isEmpty()) {
            return "Me"
        } else if (users.size <= 2) {
            return users.joinToString(" and ") { x ->
                x.firstName ?: "null"
            }
        }
        val first = users.first()
        return "${first.userId} and ${users.size - 1} others"
    }

    fun formatMessageSummary(message: Message, sender: User?): String {
        val contents = if (message.text != null) {
            "${message.text}"
        } else if (message.images.isNotEmpty()) {
            "Sent an image"
        } else if (message.voiceMemos.isNotEmpty()) {
            "Sent a voice memo"
        } else if (message.videos.isNotEmpty()) {
            "Sent a video"
        } else if (message.files.isNotEmpty()) {
            "Sent a file"
        } else {
            ""
        }
        return if (sender == null) {
            contents
        } else {
            return "${sender.firstName}: $contents"
        }
    }
}