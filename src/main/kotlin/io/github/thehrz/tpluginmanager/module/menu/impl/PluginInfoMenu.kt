package io.github.thehrz.tpluginmanager.module.menu.impl

import io.github.thehrz.tpluginmanager.module.menu.Menu
import io.github.thehrz.tpluginmanager.module.menu.PluginIcon
import io.izzel.taboolib.internal.xseries.XMaterial
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.util.item.ItemBuilder

class PluginInfoMenu(pluginIcon: PluginIcon) : Menu(pluginIcon.name, 4) {
    init {
        menu
            .items(
                "  P   S  ",
                "#########",
                "  ERUFW  ",
                "         "
            )
            .put('P', ItemBuilder(XMaterial.SKELETON_SKULL).name("&a${pluginIcon.name}").build())
            .put('S', ItemBuilder(XMaterial.COMPASS).name(TLocale.asString("Menu.Plugins-List.Info.Setting")).build())
            .put('#', ItemBuilder(XMaterial.ORANGE_STAINED_GLASS_PANE).name(" ").build())
            .put(
                'E',
                if (pluginIcon.enable)
                    ItemBuilder(XMaterial.REDSTONE).name(TLocale.asString("Menu.Plugins-List.Info.Enable")).build()
                else
                    ItemBuilder(XMaterial.GUNPOWDER).name(TLocale.asString("Menu.Plugins-List.Info.Disable")).build()
            )
            .click {

            }
    }
}