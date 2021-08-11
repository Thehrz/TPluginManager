package io.github.thehrz.tpluginmanager.implementation.bukkit

import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import org.bukkit.plugin.Plugin


class BukkitPlugin(val plugin: Plugin) : ProxyPlugin {
    override val name: String
        get() = plugin.name

    override val isEnabled: Boolean
        get() = plugin.isEnabled

    override val version: String
        get() = plugin.description.version

    override val authors: List<String>
        get() = plugin.description.authors

    override val depend: List<String>
        get() = plugin.description.depend

    override val softDepend: List<String>
        get() = plugin.description.softDepend

    override val origin: Any
        get() = plugin
}