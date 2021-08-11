package io.github.thehrz.tpluginmanager.implementation.nukkit

import cn.nukkit.plugin.Plugin
import cn.nukkit.plugin.PluginManager
import io.github.thehrz.tpluginmanager.api.adaptPlugin
import io.github.thehrz.tpluginmanager.api.adaptPluginNullable
import io.github.thehrz.tpluginmanager.api.manager.IPluginManager
import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformImplementation
import taboolib.common.platform.ProxyCommandSender
import taboolib.module.lang.sendLang
import taboolib.platform.NukkitPlugin
import java.io.File

@PlatformImplementation(Platform.NUKKIT)
class NukkitPluginManager : IPluginManager {
    override fun getPluginManager(): PluginManager =
        NukkitPlugin.getInstance().server.pluginManager

    override fun getPlugin(name: String): ProxyPlugin? =
        adaptPluginNullable(getPluginManager().getPlugin(name))

    override fun getProxyPluginsList(): List<ProxyPlugin> =
        getPluginManager().plugins.map { adaptPlugin(it.value) }

    override fun getPluginsListString(): List<String> =
        getPluginManager().plugins.map { it.key }

    override fun enablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean {
        // Nukkit-API 开启插件
        getPluginManager().enablePlugin(proxyPlugin.cast())
        sender.sendLang("commands-enable-api", proxyPlugin.name, "Nukkit")

        sender.sendLang("commands-enable-finish", proxyPlugin.name)

        return true
    }

    override fun disablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean {
        val plugin = proxyPlugin.cast<Plugin>()
        // Nukkit-API 关闭插件
        getPluginManager().disablePlugin(plugin)
        sender.sendLang("commands-disable-api", plugin.name, "Nukkit")

//        sender.sendLang("commands-disable-command", plugin.name)

        sender.sendLang("commands-disable-finish", plugin.name)
        return true
    }

    override fun loadPlugin(pluginFile: File, sender: ProxyCommandSender): Boolean {
//        getPluginManager().loadPlugin(pluginFile)
        return true
    }

    override fun loadPlugin(name: String, sender: ProxyCommandSender): Boolean {
        return true
    }

    override fun unloadPlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean {

        return true
    }

    override fun reloadPlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean {

        return true
    }
}