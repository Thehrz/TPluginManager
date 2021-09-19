package io.github.thehrz.tpluginmanager.module.menu

import io.github.thehrz.tpluginmanager.utils.Heads
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import taboolib.common.platform.function.console
import taboolib.library.xseries.XMaterial
import taboolib.module.lang.asLangText
import taboolib.module.ui.Menu
import taboolib.module.ui.buildMenu
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic
import taboolib.module.ui.type.Linked
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem

object AdvancedToolsMenu : Menu(console().asLangText("menu-main-advanced-tools-name")) {
    override fun build(): Inventory =
        buildMenu<Basic>(title) {
            handLocked(true)
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
            set('T', buildItem(XMaterial.PLAYER_HEAD) {
                name = console().asLangText("menu-console-cleaner-name")
                lore += " "
                lore += console().asLangText("menu-console-cleaner-lore")
                lore += " "
                skullTexture = ItemBuilder.SkullTexture(Heads.GARBAGE_CAN.texture)
            })
            onClick('T') {
                it.clicker.openMenu<Linked<Any>>(console().asLangText("menu-console-cleaner-name")) {
                    handLocked(true)
                    rows(6)
                }
            }
        }

    fun open(player: Player) {
        player.openInventory(build())
    }
}