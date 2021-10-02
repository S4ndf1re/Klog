package com.github.s4ndf1re

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import tornadofx.launch
import java.io.FileReader
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

    private fun addMessage(logLevel: LogLevel, functor: MessageConfig.() -> String) {
        if (logLevel.priority >= this.logLevel.priority) {
            val config = MessageConfig(logLevel)
            config.message = config.functor()
            if (config.withLineAndFile) {
                val (line, file) = Util.getLineAndFile(2)
                config.file = file
                config.line = line
            }
            this.messages.add(Message(config))
        }
    }

    // TODO (Jan): Implement MessageConfig class and use as instance for parameters
    override fun debug(debug: MessageConfig.() -> String) {
        this.addMessage(LogLevel.DEBUG, debug)
    }

    override fun info(info: MessageConfig.() -> String) {
        this.addMessage(LogLevel.INFO, info)
    }

    override fun warning(warning: MessageConfig.() -> String) {
        this.addMessage(LogLevel.WARNING, warning)
    }

    override fun error(error: MessageConfig.() -> String) {
        this.addMessage(LogLevel.ERROR, error)
    }

    override fun critical(critical: MessageConfig.() -> String) {
        this.addMessage(LogLevel.CRITICAL, critical)
    }

    override fun evaluateMostCritical(): LogLevel {
        this.mostCritical = Util.defaultMaxLogLevel(this.children, this.messages)
        return this.mostCritical
    }

    override fun getChildren(): List<ILogger> {
        return this.children
    }

    override fun getMessages(): List<Message> {
        return this.messages
    }

    fun save(path: String) {
        kotlin.runCatching {
            evaluateMostCritical()
            val serialized = Json {
                prettyPrint = true
                encodeDefaults = true
            }.encodeToString(this)
            FileWriter(path).use {
                it.write(serialized)
            }
        }.onFailure { println(it) }
    }

    fun show() {
        this.save("log.json")
        launch<LoggerApp>()
    }

    companion object Loader {
        fun load(path: String): Logger? {
            return kotlin.runCatching {
                FileReader(path).use {
                    Json {
                        prettyPrint = true
                        encodeDefaults = true
                    }.decodeFromString<Logger>(it.readText())
                }
            }.getOrNull()
        }
    }
}