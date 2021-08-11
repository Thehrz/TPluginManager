package io.github.thehrz.tpluginmanager.api.manager

import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import taboolib.common.platform.ProxyCommandSender
import taboolib.module.lang.sendLang
import java.io.File

interface IPluginManager {
    /**
     * 获取各个平台上的PluginManager
     *
     * @return PluginManager
     */
    fun getPluginManager(): Any

    /**
     * 通过插件名获取插件实例
     *
     * @param name 插件名
     * @return 插件实例
     */
    fun getPlugin(name: String): ProxyPlugin?

    fun getProxyPluginsList(): List<ProxyPlugin>

    /**
     * 获取插件列表
     *
     * @return 插件列表(String)
     */
    fun getPluginsListString(): List<String>

    /**
     * 开启一个插件
     *
     * @param proxyPlugin 要开启的插件
     * @param sender 命令执行者
     */
    fun enablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean

    /**
     * 关闭一个插件
     *
     * @param proxyPlugin 要关闭的插件
     * @param sender 命令执行者
     */
    fun disablePlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean

    /**
     * 加载一个插件
     *
     * @param pluginFile 要加载的插件文件
     * @param sender 命令执行者
     */
    fun loadPlugin(pluginFile: File, sender: ProxyCommandSender): Boolean

    /**
     * 卸载一个插件
     *
     * @param proxyPlugin 要加载的插件
     * @param sender 命令执行者
     */
    fun unloadPlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean

    /**
     * 重新加载一个插件
     *
     * @param proxyPlugin 要重新加载的插件
     * @param sender 命令执行者
     */
    fun reloadPlugin(proxyPlugin: ProxyPlugin, sender: ProxyCommandSender): Boolean

    /**
     * 开启一个插件
     *
     * @param name 要开启的插件名
     * @param sender 命令执行者
     */
    fun enablePlugin(name: String, sender: ProxyCommandSender): Boolean {
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
    fun disablePlugin(name: String, sender: ProxyCommandSender): Boolean {
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
    fun loadPlugin(name: String, sender: ProxyCommandSender): Boolean

    /**
     * 卸载一个插件
     *
     * @param name 要加载的插件名
     * @param sender 命令执行者
     */
    fun unloadPlugin(name: String, sender: ProxyCommandSender): Boolean {
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
    fun reloadPlugin(name: String, sender: ProxyCommandSender): Boolean {
        getPlugin(name)?.let {
            reloadPlugin(it, sender)
            return true
        } ?: let {
            sender.sendLang("commands-unknown", name)
            return false
        }
    }
}