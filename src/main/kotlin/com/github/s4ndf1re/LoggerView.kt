package com.github.s4ndf1re

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableView
import tornadofx.View
import tornadofx.column
import tornadofx.populate
import tornadofx.vbox

class LoggerView : View() {
    private var logger: Logger? = null

    override val root = vbox { }

    init {
        logger = Logger.load("log.json")
        logger?.evaluateMostCritical()

        val treeTableView = this.createTreeTableView()
        root.add(treeTableView)
    }

    private fun createTreeTableView(): TreeTableView<IntermediateMessage> {
        var message = IntermediateMessage(LogLevel.SUCCESS, "", "", "", "", emptyList())
        if (logger != null) {
            message = Util.parseLoggerToIntermediateMessage(logger as ILogger)
        }

        return TreeTableView<IntermediateMessage>().apply {
            column("Status", IntermediateMessage::status)
            column("Message", IntermediateMessage::message)
            column("Timestamp", IntermediateMessage::timestamp)
            column("Line", IntermediateMessage::line)
            column("File", IntermediateMessage::file)

            // Create the root item that holds all top level employees
            root = TreeItem(message)

            // Always return employees under the current person
            populate { it.value.children }

            // Expand the two first levels
            root.isExpanded = true
            columnResizePolicy = TreeTableView.CONSTRAINED_RESIZE_POLICY
        }
    }
}