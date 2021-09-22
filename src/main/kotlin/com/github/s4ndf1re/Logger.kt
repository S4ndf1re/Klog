package com.github.s4ndf1re

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileWriter

@Serializable
class Logger(
    override val description: String,
    override val logLevel: LogLevel
) : ILogger {

    override var mostCritical: LogLevel = LogLevel.SUCCESS
    private val children: ArrayList<ChildLogger> = arrayListOf()
    private val messages: ArrayList<Message> = arrayListOf()

    override fun createNode(description: String): ILogger {
        this.children.add(ChildLogger(description, this.logLevel))
        return this.children.last()
    }

    private fun addMessage(logLevel: LogLevel, msg: String, line: Int? = null, file: String? = null) {
        if (logLevel.priority >= this.logLevel.priority) {
            val config = MessageConfig(logLevel)
            config.message = msg
            config.line = line
            config.file = file
            this.messages.add(Message(config))
        }
    }

    // TODO (Jan): Implement MessageConfig class and use as instance for parameters
    override fun debug(debug: () -> String) {
        this.addMessage(LogLevel.DEBUG, debug())
    }

    override fun info(info: () -> String) {
        this.addMessage(LogLevel.INFO, info())
    }

    override fun warning(warning: () -> String) {
        this.addMessage(LogLevel.WARNING, warning())
    }

    override fun error(error: () -> String) {
        this.addMessage(LogLevel.ERROR, error())
    }

    override fun critical(critical: () -> String) {
        this.addMessage(LogLevel.CRITICAL, critical())
    }

    override fun evaluateMostCritical(): LogLevel {
        this.mostCritical = Util.defaultMaxLogLevel(this.children, this.messages)
        return this.mostCritical
    }

    fun save(path: String) {
        try {
            evaluateMostCritical()
            val serialized = Json {
                prettyPrint = true
                encodeDefaults = true
            }.encodeToString(this)
            FileWriter(path).use {
                it.write(serialized)
            }
        } catch (exc: Exception) {
            println(exc)
        }
    }
}