package com.github.s4ndf1re

interface ILogger {
    val description: String
    val logLevel: LogLevel
    var mostCritical: LogLevel

    fun createNode(description: String): ILogger

    fun debug(debug: MessageConfig.() -> String)

    fun info(info: MessageConfig.() -> String)

    fun warning(warning: MessageConfig.() -> String)

    fun error(error: MessageConfig.() -> String)

    fun critical(critical: MessageConfig.() -> String)

    fun evaluateMostCritical(): LogLevel

    fun getChildren(): List<ILogger>

    fun getMessages(): List<Message>
}