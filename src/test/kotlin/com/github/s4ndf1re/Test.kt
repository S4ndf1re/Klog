package com.github.s4ndf1re

import org.junit.Test

class Test {

    @Test
    fun test1() {
        val logger = Logger("Toplevel", LogLevel.DEBUG)
        val child1 = logger.createNode("FirstChildL1")
        val child2 = logger.createNode("SecondChildL1")
        val child3 = child1.createNode("FirstChildL2")

        child1.debug { "Debug" }
        child2.info { "Info" }
        logger.evaluateMostCritical()
        assert(logger.mostCritical == LogLevel.SUCCESS)

        child3.error { "Error" }
        logger.evaluateMostCritical()
        assert(logger.mostCritical == LogLevel.ERROR)

        logger.save("log.json")
    }

}