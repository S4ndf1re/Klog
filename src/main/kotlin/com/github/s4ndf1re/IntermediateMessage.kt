package com.github.s4ndf1re

data class IntermediateMessage(
    val status: LogLevel,
    val message: String,
    val timestamp: String,
    val line: String,
    val file: String,
    val children: List<IntermediateMessage> = emptyList()
)
