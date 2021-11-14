package io.github.thehrz.tpluginmanager.implementation.sponge

import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import org.spongepowered.plugin.PluginContainer

class SpongePlugin(override val origin: PluginContainer) : ProxyPlugin {
    override val name: String
        get() = origin.metadata().name().get()

    override val isEnabled: Boolean
        get() = true

    override val version: String
        get() = origin.metadata().version().toString()

    override val authors: List<String>
        get() = origin.metadata().contributors().map { it.name() }

    override val depend: List<String>
        get() = origin.metadata().dependencies().map { it.id() }

    override val softDepend: List<String>
        get() = listOf()
}