package io.github.thehrz.tpluginmanager.api

import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import io.github.thehrz.tpluginmanager.implementation.bukkit.BukkitPlugin
import io.github.thehrz.tpluginmanager.implementation.bungeecore.BungeeCordPlugin
import io.github.thehrz.tpluginmanager.implementation.nukkit.NukkitPlugin

object TPluginManagerAPI

/**
 * 将原始平台插件转换为代理插件
 *
 * @param origin 原始平台插件
 * @return 代理插件
 */
fun adaptPlugin(origin: Any): ProxyPlugin =
    when (origin) {
        is org.bukkit.plugin.Plugin -> BukkitPlugin(origin)
        is cn.nukkit.plugin.Plugin -> NukkitPlugin(origin)
        is net.md_5.bungee.api.plugin.Plugin -> BungeeCordPlugin(origin)
        else -> throw RuntimeException("not support")
    }

/**
 * 将原始平台插件转换为代理插件
 *
 * @param origin 原始平台插件 (Nullable)
 * @return 代理插件 (Nullable)
 */
fun adaptPluginOrNull(origin: Any?): ProxyPlugin? =
    when (origin) {
        is org.bukkit.plugin.Plugin -> BukkitPlugin(origin)
        is cn.nukkit.plugin.Plugin -> NukkitPlugin(origin)
        is net.md_5.bungee.api.plugin.Plugin -> BungeeCordPlugin(origin)
        else -> null
    }