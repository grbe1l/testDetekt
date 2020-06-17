package com.openreach.orion.customDetekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.psiUtil.getCallNameExpression

class RemovePrints(config: Config = Config.empty) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "File contains print or println",
        Debt.FIVE_MINS
    )

    override fun visitCallExpression(callNameExpression: KtCallExpression) {
        when (callNameExpression.getCallNameExpression()?.text) {

            "println" -> report(
                CodeSmell(
                    issue, Entity.from(callNameExpression),
                    "Outputting println found."
                )
            )
            "print" -> report(
                CodeSmell(
                    issue, Entity.from(callNameExpression),
                    "Outputting print found."
                )
            )
            else -> super.visitCallExpression(callNameExpression)
        }
    }
}
