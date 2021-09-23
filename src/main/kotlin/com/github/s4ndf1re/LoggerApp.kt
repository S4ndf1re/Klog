package com.github.s4ndf1re

import javafx.stage.Stage
import tornadofx.App

class LoggerApp : App(LoggerView::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 700.0
        stage.height = 700.0
    }
}