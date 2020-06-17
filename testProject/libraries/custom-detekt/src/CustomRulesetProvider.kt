package com.openreach.orion.customDetekt

import com.openreach.orion.customDetekt.rules.RemovePrints
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class CustomRulesetProvider : RuleSetProvider {
    override val ruleSetId: String = "customDetekt"

    override fun instance(config: Config): RuleSet = RuleSet(
        ruleSetId,
        listOf(
            RemovePrints(config)
        )
    )
}
