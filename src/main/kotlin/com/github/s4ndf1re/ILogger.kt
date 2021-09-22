package com.github.s4ndf1re

interface ILogger {
    val description: String
    val logLevel: LogLevel
    var mostCritical: LogLevel

    fun createNode(description: String): ILogger

    fun debug(debug: () -> String)

    fun info(info: () -> String)

    fun warning(warning: () -> String)

    fun error(error: () -> String)

    fun critical(critical: () -> String)

    fun evaluateMostCritical(): LogLevel
}