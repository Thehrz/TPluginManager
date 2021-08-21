package io.github.thehrz.tpluginmanager.implementation.bungeecore

import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import net.md_5.bungee.api.plugin.Plugin

class BungeeCordPlugin(val plugin: Plugin) : ProxyPlugin {
    override val name: String
        get() = plugin.description.name

    override val isEnabled: Boolean
        get() = true

    override val version: String
        get() = plugin.description.version

    override val authors: List<String>
        get() = listOf(plugin.description.author)

    override val depend: List<String>
        get() = plugin.description.depends.toList()

    override val softDepend: List<String>
        get() = plugin.description.softDepends.toList()

    override val origin: Any
        get() = plugin
}