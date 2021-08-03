package io.github.thehrz.tpluginmanager.module.menu.impl

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import taboolib.common.platform.console
import taboolib.library.xseries.XMaterial
import taboolib.module.lang.asLangText
import taboolib.module.ui.Menu
import taboolib.module.ui.buildMenu
import taboolib.module.ui.type.Basic
import taboolib.platform.util.buildItem

object AdvancedToolsMenu : Menu(console().asLangText("menu-main-advanced-tools-name")) {
    override fun build(): Inventory =
        buildMenu<Basic>(title) {
            rows(5)
            map(
                "#########",
                "T        ",
                "         ",
                "         ",
                "#########"
            )
            set('#', buildItem(XMaterial.GRAY_STAINED_GLASS_PANE) {
                name = " "
                colored()
            })
        }

    fun open(player: Player) {
        player.openInventory(build())
    }
}