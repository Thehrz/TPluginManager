package io.github.thehrz.tpluginmanager.module.menu.impl

import io.github.thehrz.tpluginmanager.api.plugin.impl.BukkitPluginManager
import io.github.thehrz.tpluginmanager.module.menu.PluginIcon
import io.github.thehrz.tpluginmanager.utils.Heads
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import taboolib.common.platform.console
import taboolib.library.xseries.XMaterial
import taboolib.module.lang.asLangText
import taboolib.module.ui.Menu
import taboolib.module.ui.buildMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem

object PluginsListMenu : Menu(console().asLangText("menu-plugins-list-title")) {
    override fun build(): Inventory =
        buildMenu<Linked<PluginIcon>>(title) {
            handLocked(true)
            rows(6)
            elements {
                BukkitPluginManager.pluginsList.map { PluginIcon(it) }
            }
            setPreviousPage(46) { _, hasPreviousPage ->
                buildItem(XMaterial.GREEN_STAINED_GLASS_PANE) {
                    name = if (hasPreviousPage) "&f上一页" else "&8上一页"
                }
            }
            setNextPage(52) { _, hasNextPage ->
                buildItem(XMaterial.GREEN_STAINED_GLASS_PANE) {
                    name = if (hasNextPage) "&f下一页" else "&8下一页"
                }
            }
            onGenerate(false) { _, pluginIcon, _, _ ->
                buildItem(XMaterial.PLAYER_HEAD) {
                    name = (if (pluginIcon.enable) ChatColor.GREEN.toString() else ChatColor.RED.toString()) + pluginIcon.name
                    lore += pluginIcon.description
                    skullTexture = ItemBuilder.SkullTexture((Heads.values().find { it.name[0] == pluginIcon.name[0] } ?: Heads.P).texture)
                }
            }
            onBuild(false) {
                for (slot in listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 47, 48, 49, 50, 51, 53)) {
                    it.setItem(slot, buildItem(XMaterial.GRAY_STAINED_GLASS_PANE) {
                        name = " "
                    })
                }
            }
        }

    fun open(player: Player) {
        player.openInventory(build())
    }
}