package io.github.thehrz.tpluginmanager.module.menu

import io.github.thehrz.tpluginmanager.TPluginManager
import io.izzel.taboolib.util.item.ItemBuilder
import io.izzel.taboolib.util.item.Items
import io.izzel.taboolib.util.item.inventory.MenuBuilder
import org.bukkit.entity.Player

open class Menu(title: String, rows: Int = 6) {
    private var menu: MenuBuilder = MenuBuilder.builder(TPluginManager.plugin)
        .title(title)
        .rows(rows)
        .buildAsync {
            // 构建菜单边框
            for (i in 0..8) {
                it.setItem(i, ItemBuilder(Items.asMaterial("BLACK_STAINED_GLASS_PANE")).damage(7).name(" ").build())
            }

            for (i in 45..53) {
                it.setItem(i, ItemBuilder(Items.asMaterial("BLACK_STAINED_GLASS_PANE")).damage(7).name(" ").build())
            }

            for (i in 9..36 step 9) {
                it.setItem(i, ItemBuilder(Items.asMaterial("BLACK_STAINED_GLASS_PANE")).damage(7).name(" ").build())
            }

            for (i in 17..44 step 9) {
                it.setItem(i, ItemBuilder(Items.asMaterial("BLACK_STAINED_GLASS_PANE")).damage(7).name(" ").build())
            }
        }

    fun open(player: Player) {
        player.openInventory(menu.build())
    }
}