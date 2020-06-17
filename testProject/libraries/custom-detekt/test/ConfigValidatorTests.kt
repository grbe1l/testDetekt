package com.openreach.orion.customDetekt.extensions.removePrintsTests

import com.openreach.orion.customDetekt.validation.ConfigValidator
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe

class ConfigValidatorTests : FeatureSpec({

    feature("Test that correct config exists") {

        scenario("should be correct config") {
            val config = io.gitlab.arturbosch.detekt.test.yamlConfig("positiveConfig.yml")
            val findings = ConfigValidator().validate(config = config)
            findings.size.shouldBe(0)
        }

        scenario("should be incorrect") {
            val config = io.gitlab.arturbosch.detekt.test.yamlConfig("negativeConfig.yml")
            val findings = ConfigValidator().validate(config = config)

            findings.size.shouldBe(1)
            findings.first().message.shouldBe("'active' property must be of type boolean.")
            findings.first().isError.shouldBe(true)
        }
    }
})
