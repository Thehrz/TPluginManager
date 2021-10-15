package io.github.thehrz.tpluginmanager.implementation.bungeecore

import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import net.md_5.bungee.api.plugin.Plugin

/**
 * BungeeCord平台插件类
 */
class BungeeCordPlugin(override val origin: Plugin) : ProxyPlugin {
    override val name: String
        get() = origin.description.name

    override val isEnabled: Boolean
        get() = true

    override val version: String
        get() = origin.description.version

    override val authors: List<String>
        get() = listOf(origin.description.author)

    override val depend: List<String>
        get() = origin.description.depends.toList()

    override val softDepend: List<String>
        get() = origin.description.softDepends.toList()
}