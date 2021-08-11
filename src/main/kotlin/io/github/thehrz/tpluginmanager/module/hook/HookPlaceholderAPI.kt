package io.github.thehrz.tpluginmanager.module.hook

import io.github.thehrz.tpluginmanager.implementation.bukkit.BukkitPluginManager
import org.bukkit.entity.Player
import taboolib.common.platform.implementations
import taboolib.platform.compat.PlaceholderExpansion

object HookPlaceholderAPI : PlaceholderExpansion {
    override val identifier: String = "TPluginManager"

    override fun onPlaceholderRequest(player: Player, args: String): String {
        val params = args.split("_")

        return when (params[0]) {
            "plugins" -> implementations<BukkitPluginManager>().getProxyPluginsList().size.toString()
            "isenable" -> implementations<BukkitPluginManager>().getPlugin(params[1])?.cast<org.bukkit.plugin.Plugin>()?.isEnabled?.toString() ?: "false"
            else -> ""
        }
    }
}