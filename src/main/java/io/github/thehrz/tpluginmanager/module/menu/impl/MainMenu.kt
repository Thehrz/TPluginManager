package io.github.thehrz.tpluginmanager.module.menu.impl

import io.github.thehrz.tpluginmanager.module.menu.Menu
import io.izzel.taboolib.internal.xseries.XMaterial
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.util.item.ItemBuilder
import org.bukkit.Material

object MainMenu : Menu(TLocale.asString("Menu.Main-Menu.Title"), 3) {
    init {
        menu
            .items(
                "###@@@&&&",
                "# #@ @& &",
                "###@@@&&&"
            )
            .put('#', ItemBuilder(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE).name(" ").build())
            .put('@', ItemBuilder(XMaterial.BLUE_STAINED_GLASS_PANE).name(" ").build())
            .put('&', ItemBuilder(XMaterial.ORANGE_STAINED_GLASS_PANE).name(" ").build())
            .build { inventory ->
                inventory.setItem(
                    10,
                    ItemBuilder(Material.REDSTONE).name(TLocale.asString("Menu.Main-Menu.Click-Optimize.name"))
                        .lore("", TLocale.asString("Menu.Main-Menu.Click-Optimize.lore"), "").build()
                )

                inventory.setItem(
                    13,
                    ItemBuilder(Material.PAPER).name(TLocale.asString("Menu.Main-Menu.Plugins-List.name"))
                        .lore("", TLocale.asString("Menu.Main-Menu.Plugins-List.lore"), "").build()
                )

                inventory.setItem(
                    16,
                    ItemBuilder(Material.BOOK).name(TLocale.asString("Menu.Main-Menu.Plugins-Log-List.name"))
                        .lore("", TLocale.asString("Menu.Main-Menu.Plugins-Log-List.lore"), "").build()
                )
            }
            .click { clickEvent ->
                clickEvent.onClick {
                    when (it.slot) {
                        // 一键优化
                        10 -> System.gc()

                        // 插件列表
                        13 -> PluginsListMenu.open(clickEvent.clicker)

                        // 插件日志列表
                        16 -> PluginsLogsListMenu.open(clickEvent.clicker)
                    }
                }
            }
    }
}