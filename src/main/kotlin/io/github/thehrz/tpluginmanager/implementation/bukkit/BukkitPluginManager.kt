package io.github.thehrz.tpluginmanager.implementation.bukkit

import io.github.thehrz.tpluginmanager.api.adaptPlugin
import io.github.thehrz.tpluginmanager.api.adaptPluginNullable
import io.github.thehrz.tpluginmanager.api.manager.IPluginManager
import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.command.SimpleCommandMap
import org.bukkit.plugin.*
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.NotNull
import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformImplementation
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.console
import taboolib.common.reflect.Ref
import taboolib.module.lang.sendLang
import taboolib.platform.BukkitPlugin
import java.io.File

@PlatformImplementation(Platform.BUKKIT)
class BukkitPluginManager : IPluginManager {
    private var lookupNames: MutableMap<String, Plugin>
    private val pluginsMap by lazy { pluginsList.associateBy { it.name }.toMutableMap() }
    private val pluginsList: MutableList<Plugin>
        get() {
            val pluginsField = getPluginManager()::class.java.getDeclaredField("plugins")
            pluginsField.isAccessible = true

            @Suppress("UNCHECKED_CAST")
            return pluginsField.get(Bukkit.getPluginManager()) as MutableList<Plugin>
        }

    init {
        @Suppress("UNCHECKED_CAST")
        lookupNames = getPluginManager()::class.java.getDeclaredField("lookupNames").let {
            it.isAccessible = true
            it.get(getPluginManager())
        } as MutableMap<String, Plugin>
    }

    override fun getPluginManager(): PluginManager =
        Bukkit.getPluginManager()

    override fun getPlugin(name: String): ProxyPlugin? = adaptPluginNullable(pluginsMap[name])

    override fun getProxyPluginsList(): List<ProxyPlugin> =
        pluginsList.map { adaptPlugin(it) }


    override fun getPluginsListString(): List<String> =
        getPluginManager().plugins.map { plugin: Plugin -> plugin.name }


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

    override fun enablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean {
        // Bukkit-API 开启插件
        getPluginManager().enablePlugin(proxyPlugin.cast())
        sender.sendLang("commands-enable-api", proxyPlugin.name, "Bukkit")

        sender.sendLang("commands-enable-finish", proxyPlugin.name)

        return true
    }

    override fun disablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean {
        val plugin = proxyPlugin.cast<Plugin>()
        // Bukkit-API 关闭插件
        getPluginManager().disablePlugin(plugin)
        sender.sendLang("commands-disable-api", plugin.name)

        // 注销命令
        unregisterCommand(getPluginManager(), plugin)
        sender.sendLang("commands-disable-command", plugin.name)

        sender.sendLang("commands-disable-finish", plugin.name)
        return true
    }

    override fun loadPlugin(pluginFile: File, sender: ProxyCommandSender): Boolean {
        val plugin: Plugin?
        try {
            plugin = getPluginManager().loadPlugin(pluginFile)
        } catch (e: InvalidDescriptionException) {
            sender.sendLang("commands-load-invalid-description", pluginFile.name)
            e.printStackTrace()
            return false
        } catch (e: InvalidPluginException) {
            sender.sendLang("commands-load-invalid-description")
            e.printStackTrace()
            return false
        } catch (e: UnknownDependencyException) {
            sender.sendLang("commands-load-unknown-dependency", e.message!!)
            e.printStackTrace()
            return false
        }

        plugin?.let {
            it.onLoad()
            enablePlugin(adaptPlugin(it), sender)
        }

        return true
    }

    override fun loadPlugin(name: String, sender: ProxyCommandSender): Boolean {
        getPlugin(name)?.let {
            sender.sendLang("commands-load-already-running", name)
        } ?: let {
            val dir = File("plugins")

            dir.listFiles()!!.forEach { file ->
                if (file.name.endsWith(".jar")) {
                    try {
                        if (BukkitPlugin.getInstance().pluginLoader.getPluginDescription(file).name == name) {
                            sender.sendLang("commands-load-file-found", file.name)
                            return loadPlugin(file, sender)
                        }
                    } catch (e: InvalidDescriptionException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        sender.sendLang("commands-unknown", name)

        return false
    }

    override fun unloadPlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean {
        disablePlugin(proxyPlugin, console())
        sender.sendLang("commands-unload-disable", proxyPlugin.name)

        pluginsList.remove(proxyPlugin.cast())
        lookupNames.remove(proxyPlugin.name)
        sender.sendLang("commands-unload-plugins-list", proxyPlugin.name)

        return true
    }

    override fun reloadPlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean {
        disablePlugin(proxyPlugin, sender)
        enablePlugin(proxyPlugin, sender)

        return true
    }
}