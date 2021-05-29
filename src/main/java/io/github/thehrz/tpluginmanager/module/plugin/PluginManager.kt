package io.github.thehrz.tpluginmanager.module.plugin

import io.github.thehrz.tpluginmanager.TPluginManager
import io.github.thehrz.tpluginmanager.module.command.CommandHandler
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.plugin.*
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.NotNull
import java.io.File


object PluginManager {
    var lookupNames: MutableMap<String, Plugin>

    init {
        @Suppress("UNCHECKED_CAST")
        lookupNames = Bukkit.getPluginManager()::class.java.getDeclaredField("lookupNames").let {
            it.isAccessible = true
            it.get(Bukkit.getPluginManager())
        } as MutableMap<String, Plugin>
    }

    /**
     * 通过插件名获取插件实例
     *
     * @param name 插件名
     * @return 插件实例
     */
    fun getPlugin(name: String): Plugin? = getPlugninMap()[name]

    /**
     * 获取插件列表
     *
     * @return 插件列表(Plugin)
     */
    fun getPluginsList(): MutableList<Plugin> {
        val pluginsField = Bukkit.getPluginManager()::class.java.getDeclaredField("plugins")
        pluginsField.isAccessible = true

        @Suppress("UNCHECKED_CAST")
        return pluginsField.get(Bukkit.getPluginManager()) as MutableList<Plugin>
    }

    /**
     * 获取插件列表
     *
     * @return 插件列表(String)
     */
    fun getPluginsListString(): List<String> {
        return Bukkit.getPluginManager().plugins.map { plugin: Plugin -> plugin.name }
    }

    /**
     * 获取插件名和插件实例的映射
     *
     * @return 插件名和插件实例的映射
     */
    fun getPlugninMap(): MutableMap<String, Plugin> {
        return getPluginsList().associateBy { it.name }.toMutableMap()
    }

    /**
     *  注销一个插件注册的命令
     *
     *  @param pluginManager PluginManager实例
     */
    private fun unregisterCommand(pluginManager: PluginManager, plugin: Plugin) {
        @Suppress("UNCHECKED_CAST")
        val commandMap = pluginManager::class.java.getDeclaredField("commandMap").let {
            it.isAccessible = true
            it.get(pluginManager)
        } as SimpleCommandMap

        @Suppress("UNCHECKED_CAST")
        val knownCommands = commandMap::class.java.getDeclaredField("knownCommands").let {
            it.isAccessible = true
            it.get(commandMap)
        } as MutableMap<String, Command>

        val iterator = knownCommands.entries.iterator()

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

    /**
     * 关闭一个插件
     *
     * @param plugin 要关闭的插件
     * @param sender 命令执行者
     */
    fun disablePlugin(plugin: Plugin, sender: CommandSender = Bukkit.getConsoleSender()) {
        // Bukkit 关闭插件
        Bukkit.getPluginManager().disablePlugin(plugin)
        TLocale.sendTo(sender, "Commands.Disable.Bukkit", plugin.name)

        CommandHandler.disablePlugins.remove(plugin.name)
        CommandHandler.enablePlugins.add(plugin.name)

        // 注销命令
        unregisterCommand(Bukkit.getPluginManager(), plugin)
        TLocale.sendTo(sender, "Commands.Disable.Command", plugin.name)

        TLocale.sendTo(sender, "Commands.Disable.Finish", plugin.name)
    }

    /**
     * 关闭一个插件
     *
     * @param name 要关闭的插件名
     * @param sender 命令执行者
     */
    fun disablePlugin(name: String, sender: CommandSender) {
        getPlugin(name)?.let {
            disablePlugin(it, sender)
        } ?: let {
            TLocale.sendTo(sender, "Commands.Unknown", name)
        }
    }

    /**
     * 开启一个插件
     *
     * @param plugin 要开启的插件
     * @param sender 命令执行者
     */
    fun enablePlugin(plugin: Plugin, sender: CommandSender) {
        // Bukkit 开启插件
        Bukkit.getPluginManager().enablePlugin(plugin)
        TLocale.sendTo(sender, "Commands.Enable.Bukkit", plugin.name)

        CommandHandler.disablePlugins.add(plugin.name)
        CommandHandler.enablePlugins.remove(plugin.name)

        TLocale.sendTo(sender, "Commands.Enable.Finish", plugin.name)
    }

    /**
     * 开启一个插件
     *
     * @param name 要开启的插件名
     * @param sender 命令执行者
     */
    fun enablePlugin(name: String, sender: CommandSender = Bukkit.getConsoleSender()) {
        getPlugin(name)?.let {
            enablePlugin(it, sender)
        } ?: let {
            TLocale.sendTo(sender, "Commands.Unknown", name)
        }
    }

    /**
     * 加载一个插件
     *
     * @param pluginFile 要加载的插件文件
     * @param sender 命令执行者
     */
    fun loadPlugin(pluginFile: File, sender: CommandSender) {
        val plugin: Plugin?
        try {
            plugin = Bukkit.getPluginManager().loadPlugin(pluginFile)
        } catch (e: InvalidDescriptionException) {
            e.printStackTrace()
            return
        } catch (e: InvalidPluginException) {
            e.printStackTrace()
            return
        } catch (e: UnknownDependencyException) {
            e.printStackTrace()
            return
        }

        plugin?.let {
            it.onLoad()
            enablePlugin(it, sender)
        }
    }

    /**
     * 加载一个插件
     *
     * @param pluginFile 要加载的插件名
     * @param sender 命令执行者
     */
    fun loadPlugin(name: String, sender: CommandSender = Bukkit.getConsoleSender()) {
        getPlugin(name)?.let {
            TLocale.sendTo(sender, "Commands.Load.Already-Running", name)
        } ?: let {
            val dir = File("plugins")

            dir.listFiles()!!.forEach { file ->
                if (file.name.endsWith(".jar")) {
                    try {
                        if (TPluginManager.plugin.pluginLoader.getPluginDescription(file).name == name) {
                            TLocale.sendTo(sender, "Commands.Load.File-Found", file.name)
                            return loadPlugin(file, sender)
                        }
                    } catch (e: InvalidDescriptionException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        TLocale.sendTo(sender, "Commands.Unknown", name)
    }


    fun unloadPlugin(plugin: Plugin, sender: CommandSender = Bukkit.getConsoleSender()) {
        disablePlugin(plugin)
        TLocale.sendTo(sender, "Commands.Unload.Disable", plugin.name)

        CommandHandler.disablePlugins.add(plugin.name)
        getPluginsList().remove(plugin)
        lookupNames.remove(plugin.name)
        TLocale.sendTo(sender, "Commands.Unload.Plugins-List", plugin.name)
    }

    fun unloadPlugin(name: String, sender: CommandSender) {
        getPlugin(name)?.let {
            unloadPlugin(it, sender)
        } ?: let {
            TLocale.sendTo(sender, "Commands.Unknown", name)
        }
    }
}