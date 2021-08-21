package io.github.thehrz.tpluginmanager.implementation.bungeecore

import io.github.thehrz.tpluginmanager.api.adaptPlugin
import io.github.thehrz.tpluginmanager.api.adaptPluginNullable
import io.github.thehrz.tpluginmanager.api.manager.IPluginManager
import io.github.thehrz.tpluginmanager.api.manager.Result
import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import net.md_5.bungee.api.plugin.PluginManager
import taboolib.common.platform.ProxyCommandSender
import taboolib.platform.BungeePlugin
import java.io.File

class BungeeCordPluginManager : IPluginManager {
    override fun getPluginManager(): PluginManager =
        BungeePlugin.getInstance().proxy.pluginManager


    override fun getPlugin(name: String): ProxyPlugin? =
        adaptPluginNullable(getPluginManager().getPlugin(name))


    override fun getProxyPluginsList(): List<ProxyPlugin> =
        getPluginManager().plugins.map { adaptPlugin(it) }


    override fun getPluginsListString(): List<String> =
        getPluginManager().plugins.map { it.description.name }


    override fun enablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Result {
        TODO("Not yet implemented")
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