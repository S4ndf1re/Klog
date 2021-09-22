package com.github.s4ndf1re

import kotlinx.serialization.Serializable

@Serializable
class DummyLogger(override val description: String, override val logLevel: LogLevel) : ILogger {
    override var mostCritical: LogLevel = LogLevel.SUCCESS

    override fun createNode(description: String): ILogger {
        return DummyLogger(description, this.logLevel)
    }

    override fun debug(debug: () -> String) {
    }

    override fun info(info: () -> String) {
    }

    override fun warning(warning: () -> String) {
    }

    override fun error(error: () -> String) {
    }

    override fun critical(critical: () -> String) {
    }

    override fun evaluateMostCritical(): LogLevel {
        return this.mostCritical
    }
}