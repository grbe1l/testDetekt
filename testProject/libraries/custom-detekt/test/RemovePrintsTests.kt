package com.openreach.orion.customDetekt.extensions.removePrintsTests

import com.openreach.orion.customDetekt.rules.RemovePrints

import io.gitlab.arturbosch.detekt.test.compileAndLint
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe

class RemovePrintsTests : FeatureSpec({

    feature("Test that detekt rules work correctly") {

        scenario("Should be able to detect a print") {

            val printCode = """
                        fun test(){ 
                         print("TestPrint")
                        }
                        """

            val findings = RemovePrints().compileAndLint(printCode)
            val message = findings[0].messageOrDescription()

            findings.size.shouldBe(1)
            message.shouldBe("Outputting print found.")
        }

        scenario("Should be able to detect a println") {

            val printlnCode = """
                        fun test(){
                         println("TestPrint") 
                        }
                        """

            val findings = RemovePrints().compileAndLint(printlnCode)
            val message = findings[0].messageOrDescription()

            message.shouldBe("Outputting println found.")
        }

        scenario("Should not find a print") {

            val printlnCode = """
                                fun test(){
                                 var string: String = "TEST"
                                }
                                """

            val findings = RemovePrints().compileAndLint(printlnCode)
            findings.size.shouldBe(0)
        }
    }
})
