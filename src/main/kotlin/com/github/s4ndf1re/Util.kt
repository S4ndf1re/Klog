package com.github.s4ndf1re

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
                if (mostCritical.priority < msg.level.priority) {
                    mostCritical = msg.level
                }
            }

            return mostCritical
        }

        fun fromException(exception: Exception): String {
            return "${exception.message} | ${exception.stackTrace[0]}"
        }

    }
}