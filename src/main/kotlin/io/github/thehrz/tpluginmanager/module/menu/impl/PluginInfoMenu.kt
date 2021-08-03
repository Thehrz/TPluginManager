package io.github.thehrz.tpluginmanager.module.menu.impl

import io.github.thehrz.tpluginmanager.module.menu.PluginIcon
import io.github.thehrz.tpluginmanager.utils.Heads
import org.bukkit.inventory.Inventory
import taboolib.common.platform.console
import taboolib.library.xseries.XMaterial
import taboolib.module.lang.asLangText
import taboolib.module.ui.Menu
import taboolib.module.ui.buildMenu
import taboolib.module.ui.type.Basic
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import java.util.*

class PluginInfoMenu(val pluginIcon: PluginIcon) : Menu(pluginIcon.name) {
    override fun build(): Inventory =
        buildMenu<Basic> {
            rows(6)
            map(
                "  P   S  ",
                "#########",
                "  ERUFW  ",
                "         "
            )
            set('P', buildItem(XMaterial.PLAYER_HEAD) {
                name = "&a${pluginIcon.name}"
                skullTexture = ItemBuilder.SkullTexture((Heads.values().find { it.name[0] == pluginIcon.name[0] }
                    ?: Heads.P).texture, UUID.randomUUID())
            })
            set('S', buildItem(XMaterial.COMPASS) {
                name = console().asLangText("menu-plugins-list-info-setting")
            })
            set('#', buildItem(XMaterial.ORANGE_STAINED_GLASS_PANE) {
                name = " "
            })
            set('E',
                if (pluginIcon.enable)
                    buildItem(XMaterial.REDSTONE) {
                        name = console().asLangText("menu-plugins-list-info-enable")
                    }
                else
                    buildItem(XMaterial.GUNPOWDER) {
                        name = console().asLangText("menu-plugins-list-info-disable")
                    }
            )
        }


}