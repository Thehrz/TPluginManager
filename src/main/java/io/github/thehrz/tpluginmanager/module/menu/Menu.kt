package io.github.thehrz.tpluginmanager.module.menu

import io.github.thehrz.tpluginmanager.TPluginManager
import io.izzel.taboolib.util.item.inventory.MenuBuilder
import org.bukkit.entity.Player

abstract class Menu(title: String, rows: Int = 6) {
    protected var menu: MenuBuilder = MenuBuilder.builder(TPluginManager.plugin)
        .title(title)
        .rows(rows)
        .lockHand(true)

    fun open(player: Player) {
        menu.open(player)
    }
}