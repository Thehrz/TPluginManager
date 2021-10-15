package io.github.thehrz.tpluginmanager.implementation.bukkit

import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import org.bukkit.plugin.Plugin

/**
 * Bukkit平台插件类
 */
class BukkitPlugin(override val origin: Plugin) : ProxyPlugin {
    override val name: String
        get() = origin.name

    override val isEnabled: Boolean
        get() = origin.isEnabled

    override val version: String
        get() = origin.description.version

    override val authors: List<String>
        get() = origin.description.authors

    override val depend: List<String>
        get() = origin.description.depend

    override val softDepend: List<String>
        get() = origin.description.softDepend
}