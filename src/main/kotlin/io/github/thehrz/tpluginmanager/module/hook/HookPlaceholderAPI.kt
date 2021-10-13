package io.github.thehrz.tpluginmanager.module.hook

import io.github.thehrz.tpluginmanager.api.pluginManagerImpl
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformSide
import taboolib.platform.compat.PlaceholderExpansion

/**
 * PlaceholderAPI支持
 */
@Awake(LifeCycle.ENABLE)
@PlatformSide([Platform.BUKKIT])
private object HookPlaceholderAPI : PlaceholderExpansion {
    override val identifier: String = "TPluginManager"

    override fun onPlaceholderRequest(player: Player, args: String): String {
        val params = args.split("_")

        return when (params[0]) {
            "plugins" -> pluginManagerImpl.getProxyPluginsList().size.toString()
            "isenable" -> pluginManagerImpl.getPlugin(params[1])?.cast<org.bukkit.plugin.Plugin>()?.isEnabled?.toString() ?: "false"
            else -> ""
        }
    }
}