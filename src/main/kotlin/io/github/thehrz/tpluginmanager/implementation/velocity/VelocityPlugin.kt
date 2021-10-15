package io.github.thehrz.tpluginmanager.implementation.velocity

import com.velocitypowered.api.plugin.Plugin
import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin

class VelocityPlugin(override val origin: Plugin) : ProxyPlugin {
    override val name: String
        get() = origin.name

    override val isEnabled: Boolean
        get() = true

    override val version: String
        get() = origin.version

    override val authors: List<String>
        get() = origin.authors.toList()

    override val depend: List<String>
        get() = origin.dependencies.map { it.id }

    override val softDepend: List<String>
        get() = listOf()
}