package com.openreach.orion.customDetekt.validation

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.ConfigValidator
import io.gitlab.arturbosch.detekt.api.Notification

class ConfigValidator : ConfigValidator {

    override fun validate(config: Config): Collection<Notification> {
        val result = mutableListOf<Notification>()
        runCatching {
            config.subConfig("removePrints").valueOrNull<Boolean>("active")
        }.onFailure {
            result.add(SampleMessage("'active' property must be of type boolean."))
        }
        return result
    }
}

class SampleMessage(
    override val message: String,
    override val level: Notification.Level = Notification.Level.Error
) : Notification
