package io.github.thehrz.tpluginmanager.api

import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import io.github.thehrz.tpluginmanager.implementation.bukkit.BukkitPlugin
import io.github.thehrz.tpluginmanager.implementation.nukkit.NukkitPlugin

object TPluginManagerAPI

fun adaptPlugin(any: Any): ProxyPlugin =
    when (any) {
        is org.bukkit.plugin.Plugin -> BukkitPlugin(any)
        is cn.nukkit.plugin.Plugin -> NukkitPlugin(any)
        else -> throw RuntimeException("not support")
    }

fun adaptPluginNullable(any: Any?): ProxyPlugin? =
    when (any) {
        is org.bukkit.plugin.Plugin -> BukkitPlugin(any)
        is cn.nukkit.plugin.Plugin -> NukkitPlugin(any)
        else -> null
    }