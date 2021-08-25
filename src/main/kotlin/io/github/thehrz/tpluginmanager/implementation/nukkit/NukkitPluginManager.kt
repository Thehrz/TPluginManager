package io.github.thehrz.tpluginmanager.implementation.nukkit

import cn.nukkit.plugin.Plugin
import cn.nukkit.plugin.PluginManager
import io.github.thehrz.tpluginmanager.api.adaptPlugin
import io.github.thehrz.tpluginmanager.api.adaptPluginOrNull
import io.github.thehrz.tpluginmanager.api.manager.IPluginManager
import io.github.thehrz.tpluginmanager.api.manager.Result
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
        adaptPluginOrNull(getPluginManager().getPlugin(name))

    override fun getProxyPluginsList(): List<ProxyPlugin> =
        getPluginManager().plugins.map { adaptPlugin(it.value) }

    override fun getPluginsListString(): List<String> =
        getPluginManager().plugins.map { it.key }

    override fun enablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Result {
        // Nukkit-API 开启插件
        getPluginManager().enablePlugin(proxyPlugin.cast())
        sender.sendLang("commands-enable-api", proxyPlugin.name, "Nukkit")

        sender.sendLang("commands-enable-finish", proxyPlugin.name)

        return Result.SUCCESS
    }

    override fun disablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Result {
        val plugin = proxyPlugin.cast<Plugin>()
        // Nukkit-API 关闭插件
        getPluginManager().disablePlugin(plugin)
        sender.sendLang("commands-disable-api", plugin.name, "Nukkit")

//        sender.sendLang("commands-disable-command", plugin.name)

        sender.sendLang("commands-disable-finish", plugin.name)
        return Result.SUCCESS
    }

    override fun loadPlugin(pluginFile: File, sender: ProxyCommandSender): Result {
//        getPluginManager().loadPlugin(pluginFile)
        return Result.SUCCESS
    }

    override fun loadPlugin(name: String, sender: ProxyCommandSender): Result {
        return Result.SUCCESS
    }

    override fun unloadPlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Result {

        return Result.SUCCESS
    }

    override fun reloadPlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Result {

        return Result.SUCCESS
    }
}