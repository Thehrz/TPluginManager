package io.github.thehrz.tpluginmanager.api.plugin.impl

import io.github.thehrz.tpluginmanager.api.plugin.AbstractPluginManager
import io.github.thehrz.tpluginmanager.module.command.CommandHandler
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.command.SimpleCommandMap
import org.bukkit.plugin.*
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.NotNull
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.console
import taboolib.common.reflect.Ref
import taboolib.module.lang.sendLang
import java.io.File

object BukkitPluginManager : AbstractPluginManager() {
    private var lookupNames: MutableMap<String, Plugin>
    private val pluginsMap = pluginsList.associateBy { it.name }.toMutableMap()
    internal val pluginsList: MutableList<Plugin>
        get() {
            val pluginsField = Bukkit.getPluginManager()::class.java.getDeclaredField("plugins")
            pluginsField.isAccessible = true

            @Suppress("UNCHECKED_CAST")
            return pluginsField.get(Bukkit.getPluginManager()) as MutableList<Plugin>
        }

    init {
        @Suppress("UNCHECKED_CAST")
        lookupNames = Bukkit.getPluginManager()::class.java.getDeclaredField("lookupNames").let {
            it.isAccessible = true
            it.get(Bukkit.getPluginManager())
        } as MutableMap<String, Plugin>
    }

    override fun getPlugin(name: String): Plugin? = pluginsMap[name]

    override fun getPluginsListString(): List<String> {
        return Bukkit.getPluginManager().plugins.map { plugin: Plugin -> plugin.name }
    }

    private fun unregisterCommand(pluginManager: PluginManager, plugin: Plugin) {
        val commandMap = pluginManager::class.java.getDeclaredField("commandMap").let {
            it.isAccessible = true
            Ref.get<SimpleCommandMap>(pluginManager, it)
        }

        val knownCommands = SimpleCommandMap::class.java.getDeclaredField("knownCommands").let {
            it.isAccessible = true
            Ref.get<MutableMap<String, Command>>(commandMap, it)
        }

        val iterator = knownCommands!!.entries.iterator()

        while (iterator.hasNext()) {
            iterator.let { mutableIterator ->
                mutableIterator.next().value.let {
                    // JavaPlugin.getProvidingPlugin(it.javaClass) 可以获取这个类属于什么插件实例
                    // 但是还有一种注册命令的方法是直接操作commandMap 这种方法命令实例就不属于PluginCommand了 也就不会返回插件实例 会抛出异常
                    try {
                        if ((it is PluginCommand && it.plugin == plugin) || JavaPlugin.getProvidingPlugin(it.javaClass) == plugin) {
                            it.unregister(commandMap as @NotNull CommandMap)
                            mutableIterator.remove()
                        }
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    override fun enablePlugin(plugin: Plugin, sender: ProxyCommandSender): Boolean {
        // Bukkit 开启插件
        Bukkit.getPluginManager().enablePlugin(plugin)
        sender.sendLang("commands-enable-bukkitapi", plugin.name)

        sender.sendLang("commands-enable-finish", plugin.name)

        return true
    }

    override fun disablePlugin(plugin: Plugin, sender: ProxyCommandSender): Boolean {
        // Bukkit 关闭插件
        Bukkit.getPluginManager().disablePlugin(plugin)
        sender.sendLang("commands-disable-bukkitapi", plugin.name)

        // 注销命令
        unregisterCommand(Bukkit.getPluginManager(), plugin)
        sender.sendLang("commands-disable-command", plugin.name)

        sender.sendLang("commands-disable-finish", plugin.name)
        return true
    }

    override fun loadPlugin(pluginFile: File, sender: ProxyCommandSender): Boolean {
        val plugin: Plugin?
        try {
            plugin = Bukkit.getPluginManager().loadPlugin(pluginFile)
        } catch (e: InvalidDescriptionException) {
            e.printStackTrace()
            return false
        } catch (e: InvalidPluginException) {
            e.printStackTrace()
            return false
        } catch (e: UnknownDependencyException) {
            e.printStackTrace()
            return false
        }

        plugin?.let {
            it.onLoad()
            enablePlugin(it, sender)
        }

        return true
    }

    override fun unloadPlugin(plugin: Plugin, sender: ProxyCommandSender): Boolean {
        disablePlugin(plugin, console())
        sender.sendLang("commands-unload-disable", plugin.name)

        CommandHandler.enablePlugins.add(plugin.name)
        pluginsList.remove(plugin)
        lookupNames.remove(plugin.name)
        sender.sendLang("commands-unload-plugins-list", plugin.name)

        return true
    }

    override fun reloadPlugin(plugin: Plugin, sender: ProxyCommandSender): Boolean {
        disablePlugin(plugin, sender)
        enablePlugin(plugin, sender)

        return true
    }
}