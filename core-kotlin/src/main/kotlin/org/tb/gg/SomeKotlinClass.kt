package org.tb.gg

import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService

class SomeKotlinClass {
    @Inject
    lateinit var environmentService: EnvironmentService

    fun test() {
        println(environmentService.environment)
    }
}

