package io.github.thehrz.tpluginmanager.module.taskmanager

import io.github.thehrz.tpluginmanager.module.menu.PluginIcon
import io.github.thehrz.tpluginmanager.module.menu.impl.PluginsListMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import taboolib.common.platform.console
import taboolib.module.lang.asLangText
import taboolib.module.ui.Menu
import taboolib.module.ui.buildMenu
import taboolib.module.ui.type.Linked

object TaskManagerMenu : Menu(console().asLangText("menu-task-manager-title")) {
    override fun build(): Inventory =
        buildMenu<Linked<PluginIcon>>(title) {

        }

    fun open(player: Player) {
        player.openInventory(PluginsListMenu.build())
    }
}