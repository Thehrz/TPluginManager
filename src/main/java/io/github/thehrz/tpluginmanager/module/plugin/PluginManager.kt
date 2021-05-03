package io.github.thehrz.tpluginmanager.module.plugin

import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.jetbrains.annotations.NotNull

object PluginManager {
    private val pluginsMap: MutableMap<String, Plugin> = HashMap()

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

    fun getPlugninMap(): MutableMap<String, Plugin> {
        getPluginsList().forEach {
            pluginsMap[it.name] = it
        }
        return pluginsMap
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
                    if (it is PluginCommand && it.plugin === plugin) {
                        it.unregister(commandMap as @NotNull CommandMap)
                        mutableIterator.remove()
                    }
                }
            }
        }


//        Reflex(commandMap::class.java).set("")

//        knownCommands.entries.iterator().forEach { mutableEntry ->
//            mutableEntry.value.let {
//                if (it is PluginCommand && it.plugin == plugin) {
//                    it.unregister(commandMap as @NotNull CommandMap)
//                }
//            }
//        }


//        for (command in knownCommands.entries.iterator()) {
//            command.value.let {
//                if (it is PluginCommand && it.plugin == plugin) {
//                    it.unregister(commandMap as @NotNull CommandMap)
//
//                }
//            }
//
//        }


    }

    /**
     * 关闭一个插件
     *
     * @param plugin 要关闭的插件
     */
    fun disablePlugin(plugin: Plugin) {
        // Bukkit 关闭插件
        Bukkit.getPluginManager().disablePlugin(plugin)
        // 注销监听器
        HandlerList.unregisterAll(plugin)
        // 从插件列表删除
        getPluginsList().remove(plugin)

        @Suppress("UNCHECKED_CAST")
        val lookupNames = Bukkit.getPluginManager()::class.java.getDeclaredField("lookupNames").let {
            it.isAccessible = true
            it.get(Bukkit.getPluginManager())
        } as MutableMap<String, Plugin>

        lookupNames.remove(plugin.description.name)

        // 注销命令
        unregisterCommand(Bukkit.getPluginManager(), plugin)
    }

    /**
     * 关闭一个插件
     *
     * @param name 要关闭的插件名
     * @param sender 命令执行者
     */
    fun disablePlugin(name: String, sender: CommandSender) {
        getPlugin(name)?.let {
            disablePlugin(it)
        } ?: let {
            TLocale.sendTo(sender, "Commands.Unknown", name)
        }
    }


}