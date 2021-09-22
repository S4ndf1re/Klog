package com.github.s4ndf1re

import kotlinx.serialization.Serializable

@Serializable
class MessageConfig(
    val logLevel: LogLevel,
) {
    var message: String = ""
    var line: Int? = null
    var file: String? = null
    var withLineAndFile: Boolean = false
}