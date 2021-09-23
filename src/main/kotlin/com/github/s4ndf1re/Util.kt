package com.github.s4ndf1re

const val THREAD_STACKTRACE_OFFSET = 2

class Util {
    companion object Static {

        fun defaultMaxLogLevel(children: List<ILogger>, messages: List<Message>): LogLevel {
            var mostCritical = LogLevel.SUCCESS

            for (child in children) {
                val level = child.evaluateMostCritical()
                if (mostCritical.priority < level.priority) {
                    mostCritical = level
                }
            }

            for (msg in messages) {
                if (mostCritical.priority < msg.messageConfig.logLevel.priority) {
                    mostCritical = msg.messageConfig.logLevel
                }
            }

            return mostCritical
        }


        private fun addStackLevelOffset(hierarchyLevel: Int): Int {
            return hierarchyLevel + THREAD_STACKTRACE_OFFSET
        }


        fun getLineAndFile(hierarchyLevel: Int): Pair<Int, String?> {
            val stackTrace = Thread.currentThread().stackTrace
            val line = stackTrace[addStackLevelOffset(hierarchyLevel)].lineNumber
            val fileName = stackTrace[addStackLevelOffset(hierarchyLevel)].fileName
            return Pair(line, fileName)
        }

        private fun parseMessageToIntermediateMessage(message: Message): IntermediateMessage {
            val config = message.messageConfig
            return IntermediateMessage(
                config.logLevel,
                config.message,
                message.timestamp,
                config.line?.toString() ?: "",
                config.file.orEmpty(),
                emptyList()
            )
        }

        fun parseLoggerToIntermediateMessage(logger: ILogger): IntermediateMessage {
            val messageChildren: MutableList<IntermediateMessage> = mutableListOf()
            val children = logger.getChildren()
            for (child in children) {
                messageChildren.add(parseLoggerToIntermediateMessage(child))
            }

            val messages = logger.getMessages()
            for (msg in messages) {
                messageChildren.add(parseMessageToIntermediateMessage(msg))
            }

            return IntermediateMessage(logger.mostCritical, logger.description, "", "", "", messageChildren)
        }

    }
}