package com.github.s4ndf1re

import kotlinx.serialization.Serializable

@Serializable
class DummyLogger(override val description: String, override val logLevel: LogLevel) : ILogger {
    override var mostCritical: LogLevel = LogLevel.SUCCESS

    override fun createNode(description: String): ILogger {
        return DummyLogger(description, this.logLevel)
    }

    override fun debug(debug: MessageConfig.() -> String) {
    }

    override fun info(info: MessageConfig.() -> String) {
    }

    override fun warning(warning: MessageConfig.() -> String) {
    }

    override fun error(error: MessageConfig.() -> String) {
    }

    override fun critical(critical: MessageConfig.() -> String) {
    }

    override fun evaluateMostCritical(): LogLevel {
        return this.mostCritical
    }

    override fun getChildren(): List<ILogger> {
        return emptyList()
    }

    override fun getMessages(): List<Message> {
        return emptyList()
    }
}