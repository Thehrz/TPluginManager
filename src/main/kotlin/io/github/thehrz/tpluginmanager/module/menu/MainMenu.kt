package io.github.thehrz.tpluginmanager.module.menu

import io.github.thehrz.tpluginmanager.module.taskmanager.TaskManagerMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import taboolib.common.platform.function.console
import taboolib.library.xseries.XMaterial
import taboolib.module.lang.asLangText
import taboolib.module.ui.Menu
import taboolib.module.ui.buildMenu
import taboolib.module.ui.type.Basic
import taboolib.platform.util.buildItem

object MainMenu : Menu(console().asLangText("menu-main-title", "")) {
    override fun build(): Inventory =
        buildMenu<Basic>(title) {
            handLocked(true)
            rows(3)
            map(
                "###@@@&&&",
                "#T#@L@&A&",
                "###@@@&&&"
            )
            set('#', buildItem(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE) {
                name = " "
                colored()
            })
            set('@', buildItem(XMaterial.BLUE_STAINED_GLASS_PANE) {
                name = " "
                colored()
            })
            set('&', buildItem(XMaterial.ORANGE_STAINED_GLASS_PANE) {
                name = " "
                colored()
            })
            set('T', buildItem(XMaterial.REDSTONE) {
                name = console().asLangText("menu-main-task-manager-name")
                lore += ""
                lore += console().asLangText("menu-main-task-manager-lore")
                lore += ""
            })
            set('L', buildItem(XMaterial.PAPER) {
                name = console().asLangText("menu-main-plugins-list-name")
                lore += ""
                lore += console().asLangText("menu-main-plugins-list-lore")
                lore += ""
            })
            set('A', buildItem(XMaterial.BOOK) {
                name = console().asLangText("menu-main-advanced-tools-name")
                lore += ""
                lore += console().asLangText("menu-main-advanced-tools-lore")
                lore += ""
            })
            onClick('T') {
                TaskManagerMenu.open(it.clicker)
            }
            onClick('L') {
                PluginsListMenu.open(it.clicker)
            }
            onClick('A') {
                AdvancedToolsMenu.open(it.clicker)
            }
        }

    fun open(player: Player) {
        player.openInventory(build())
    }
}