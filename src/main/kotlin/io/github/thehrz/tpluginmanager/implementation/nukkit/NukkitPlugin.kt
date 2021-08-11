package io.github.thehrz.tpluginmanager.implementation.nukkit

import cn.nukkit.plugin.Plugin
import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin

class NukkitPlugin(val plugin: Plugin) : ProxyPlugin {
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