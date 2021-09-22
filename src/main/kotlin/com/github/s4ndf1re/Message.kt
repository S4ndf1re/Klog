package com.github.s4ndf1re

import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Serializable
class Message(val messageConfig: MessageConfig) {
    private val timestamp: String

    init {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        timestamp = dateFormat.format(Date())
    }

    override fun toString(): String {
        val line: String = messageConfig.line?.toString() ?: ""
        val file: String = messageConfig.file ?: ""
        return "${messageConfig.logLevel}/$timestamp $line $file |=> ${messageConfig.message}"
    }

}