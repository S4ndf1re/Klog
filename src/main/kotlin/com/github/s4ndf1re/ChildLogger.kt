package com.github.s4ndf1re

import kotlinx.serialization.Serializable

@Serializable
class ChildLogger(
    override val description: String,
    override val logLevel: LogLevel,
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
            this.messages.add(Message(logLevel, msg, line, file))
        }
    }

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

}