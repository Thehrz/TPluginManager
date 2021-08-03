package io.github.thehrz.tpluginmanager.module.hook

import io.github.thehrz.tpluginmanager.api.plugin.impl.BukkitPluginManager
import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion

object HookPlaceholderAPI : PlaceholderExpansion {
    override val identifier: String = "TPluginManager"

    override fun onPlaceholderRequest(player: Player, args: String): String {
        val params = args.split("_")

        return when (params[0]) {
            "plugins" -> BukkitPluginManager.pluginsList.size.toString()
            "isenable" -> BukkitPluginManager.getPlugin(params[1])?.isEnabled?.toString() ?: "false"
            else -> ""
        }
    }
}