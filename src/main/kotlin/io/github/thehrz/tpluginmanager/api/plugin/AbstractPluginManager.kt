package io.github.thehrz.tpluginmanager.api.plugin

import org.bukkit.plugin.InvalidDescriptionException
import org.bukkit.plugin.Plugin
import taboolib.common.platform.ProxyCommandSender
import taboolib.module.lang.sendLang
import taboolib.platform.BukkitPlugin
import java.io.File

abstract class AbstractPluginManager {
    /**
     * 通过插件名获取插件实例
     *
     * @param name 插件名
     * @return 插件实例
     */
    abstract fun getPlugin(name: String): Plugin?

    /**
     * 获取插件列表
     *
     * @return 插件列表(String)
     */
    abstract fun getPluginsListString(): List<String>

    /**
     * 开启一个插件
     *
     * @param plugin 要开启的插件
     * @param sender 命令执行者
     */
    abstract fun enablePlugin(plugin: Plugin, sender: ProxyCommandSender): Boolean

    /**
     * 关闭一个插件
     *
     * @param plugin 要关闭的插件
     * @param sender 命令执行者
     */
    abstract fun disablePlugin(plugin: Plugin, sender: ProxyCommandSender): Boolean

    /**
     * 加载一个插件
     *
     * @param pluginFile 要加载的插件文件
     * @param sender 命令执行者
     */
    abstract fun loadPlugin(pluginFile: File, sender: ProxyCommandSender): Boolean

    /**
     * 卸载一个插件
     *
     * @param plugin 要加载的插件
     * @param sender 命令执行者
     */
    abstract fun unloadPlugin(plugin: Plugin, sender: ProxyCommandSender): Boolean

    /**
     * 重新加载一个插件
     *
     * @param plugin 要重新加载的插件
     * @param sender 命令执行者
     */
    abstract fun reloadPlugin(plugin: Plugin, sender: ProxyCommandSender): Boolean

    /**
     * 开启一个插件
     *
     * @param name 要开启的插件名
     * @param sender 命令执行者
     */
    internal fun enablePlugin(name: String, sender: ProxyCommandSender): Boolean {
        getPlugin(name)?.let {
            enablePlugin(it, sender)
            return true
        } ?: let {
            sender.sendLang("commands-unknown", name)
            return false
        }
    }

    /**
     * 关闭一个插件
     *
     * @param name 要关闭的插件名
     * @param sender 命令执行者
     */
    internal fun disablePlugin(name: String, sender: ProxyCommandSender): Boolean {
        getPlugin(name)?.let {
            return disablePlugin(it, sender)
        } ?: let {
            sender.sendLang("commands-unknown", name)
            return false
        }
    }

    /**
     * 加载一个插件
     *
     * @param name 要加载的插件名
     * @param sender 命令执行者
     */
    internal fun loadPlugin(name: String, sender: ProxyCommandSender): Boolean {
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

    /**
     * 卸载一个插件
     *
     * @param name 要加载的插件名
     * @param sender 命令执行者
     */
    internal fun unloadPlugin(name: String, sender: ProxyCommandSender): Boolean {
        getPlugin(name)?.let {
            return unloadPlugin(it, sender)
        } ?: let {
            sender.sendLang("commands-unknown", name)
            return false
        }
    }

    /**
     * 重新加载一个插件
     *
     * @param name 要重新加载的插件名
     * @param sender 命令执行者
     */
    internal fun reloadPlugin(name: String, sender: ProxyCommandSender): Boolean {
        getPlugin(name)?.let {
            reloadPlugin(it, sender)
            return true
        } ?: let {
            sender.sendLang("commands-unknown", name)
            return false
        }
    }
}