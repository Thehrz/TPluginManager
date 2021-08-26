package io.github.thehrz.tpluginmanager.implementation.bungeecore

import io.github.thehrz.tpluginmanager.api.adaptPlugin
import io.github.thehrz.tpluginmanager.api.adaptPluginOrNull
import io.github.thehrz.tpluginmanager.api.manager.IPluginManager
import io.github.thehrz.tpluginmanager.api.manager.Result
import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.plugin.PluginManager
import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformImplementation
import taboolib.common.platform.ProxyCommandSender
import taboolib.platform.BungeePlugin
import java.io.File

/**
 * BungeeCord平台插件管理器实现
 */
@PlatformImplementation(Platform.BUNGEE)
class BungeeCordPluginManager : IPluginManager {
    override fun getPluginManager(): PluginManager =
        BungeePlugin.getInstance().proxy.pluginManager

    override fun getPlugin(name: String): ProxyPlugin? =
        adaptPluginOrNull(getPluginManager().getPlugin(name))

    override fun getProxyPluginsList(): List<ProxyPlugin> =
        getPluginManager().plugins.map { adaptPlugin(it) }

    override fun getPluginsListString(): List<String> =
        getPluginManager().plugins.map { it.description.name }

    override fun enablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Result {
        proxyPlugin.cast<Plugin>().onEnable()
        return Result.SUCCESS
    }

    override fun disablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Result {
        TODO("Not yet implemented")
    }

    override fun loadPlugin(pluginFile: File, sender: ProxyCommandSender): Result {
        TODO("Not yet implemented")
    }

    override fun loadPlugin(name: String, sender: ProxyCommandSender): Result {
        TODO("Not yet implemented")
    }

    override fun unloadPlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Result {
        TODO("Not yet implemented")
    }

    override fun reloadPlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Result {
        TODO("Not yet implemented")
    }
}