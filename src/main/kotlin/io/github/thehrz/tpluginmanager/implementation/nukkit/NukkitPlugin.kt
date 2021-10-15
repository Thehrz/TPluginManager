package io.github.thehrz.tpluginmanager.implementation.nukkit

import cn.nukkit.plugin.Plugin
import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin

/**
 * Nukkit平台插件类
 */
class NukkitPlugin(override val origin: Plugin) : ProxyPlugin {
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