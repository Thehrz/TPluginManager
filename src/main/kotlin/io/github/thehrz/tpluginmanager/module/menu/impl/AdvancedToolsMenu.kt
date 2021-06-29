package io.github.thehrz.tpluginmanager.module.menu.impl

import io.github.thehrz.tpluginmanager.module.menu.Menu
import io.izzel.taboolib.internal.xseries.XMaterial
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.util.item.ItemBuilder

object AdvancedToolsMenu : Menu(TLocale.asString("Menu.Main-Menu.Advanced-Tools.name"), 5) {
    init {
        menu
            .items(
                "#########",
                "         ",
                "         ",
                "         ",
                "#########"
            )
            .put('#', ItemBuilder(XMaterial.GRAY_STAINED_GLASS_PANE).name(" ").build())
    }



}