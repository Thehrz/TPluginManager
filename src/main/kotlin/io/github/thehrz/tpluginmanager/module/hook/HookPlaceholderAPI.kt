package io.github.thehrz.tpluginmanager.module.hook

import io.github.thehrz.tpluginmanager.TPluginManager
import io.github.thehrz.tpluginmanager.module.plugin.PluginManager
import io.izzel.taboolib.module.inject.THook
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

@THook
class HookPlaceholderAPI : PlaceholderExpansion() {
    override fun getIdentifier(): String = "tpluginmanager"

    override fun getAuthor(): String = "Thehrz"

    override fun getVersion(): String = TPluginManager.plugin.description.version

    override fun persist(): Boolean = true

    override fun canRegister(): Boolean = true

    override fun onPlaceholderRequest(player: Player?, params: String): String {
        val args = params.split("_")

        return when (args[0]) {
            "plugins" -> PluginManager.getPluginsList().size.toString()
            "isenable" -> PluginManager.getPlugin(args[1])?.isEnabled?.toString() ?: "false"
            else -> ""
        }
    }
}