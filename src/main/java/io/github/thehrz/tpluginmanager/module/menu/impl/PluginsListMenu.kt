package io.github.thehrz.tpluginmanager.module.menu.impl

import io.github.thehrz.tpluginmanager.module.menu.Menu
import io.github.thehrz.tpluginmanager.module.plugin.PluginManager
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.util.item.ItemBuilder
import org.bukkit.ChatColor
import org.bukkit.Material

object PluginsListMenu : Menu(TLocale.asString("Menu.Plugins-List"), 6) {
    init {
        menu
            .buildAsync {
                for ((slot, plugin) in PluginManager.getPluginsList().withIndex()) {
                    val pluginIcon = ItemBuilder(Material.PAPER)
                        .name(ChatColor.GREEN.toString() + plugin.name)
                        .build()

                    it.setItem(slot, pluginIcon)
                }
            }
            .click {
                it.onClick {
                    when (it.slot) {

                    }
                }
            }
    }
}