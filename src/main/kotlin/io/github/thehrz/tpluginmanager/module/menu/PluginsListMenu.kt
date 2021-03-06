package io.github.thehrz.tpluginmanager.module.menu

import io.github.thehrz.tpluginmanager.api.plugin.PluginIcon
import io.github.thehrz.tpluginmanager.api.pluginManagerImpl
import io.github.thehrz.tpluginmanager.utils.Heads
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import taboolib.common.platform.function.console
import taboolib.library.xseries.XMaterial
import taboolib.module.lang.asLangText
import taboolib.module.ui.Menu
import taboolib.module.ui.buildMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.buildItem

val Menu.centeredSlots: List<Int>
    get() = listOf(
        9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
        27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 41, 43, 44
    )

object PluginsListMenu : Menu(console().asLangText("menu-plugins-list-title")) {
    override fun build(): Inventory =
        buildMenu<Linked<PluginIcon>>(title) {
            handLocked(true)
            rows(6)
            elements {
                pluginManagerImpl.getProxyPluginsList().map { PluginIcon(it) }
            }
            slots(centeredSlots)
            setPreviousPage(46) { _, hasPreviousPage ->
                buildItem(XMaterial.GREEN_STAINED_GLASS_PANE) {
                    name = if (hasPreviousPage) "&f上一页" else "&8上一页"
                    colored()
                }
            }
            setNextPage(52) { _, hasNextPage ->
                buildItem(XMaterial.GREEN_STAINED_GLASS_PANE) {
                    name = if (hasNextPage) "&f下一页" else "&8下一页"
                    colored()
                }
            }
            onGenerate(false) { _, pluginIcon, _, _ ->
                buildItem(XMaterial.PLAYER_HEAD) {
                    name =
                        (if (pluginIcon.isEnabled) ChatColor.GREEN.toString() else ChatColor.RED.toString()) + pluginIcon.name
                    lore += pluginIcon.description
                    skullTexture = (Heads.values().find { it.name[0] == pluginIcon.name[0] }
                        ?: Heads.P).getSkullTexture()
                }
            }
            onBuild(false) {
                for (slot in listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 47, 48, 49, 50, 51, 53)) {
                    it.setItem(slot, buildItem(XMaterial.GRAY_STAINED_GLASS_PANE) {
                        name = " "
                    })
                }
            }
            onClick { clickEvent, element ->
                PluginInfoMenu(element).open(clickEvent.clicker)
            }
        }

    fun open(player: Player) {
        player.openInventory(build())
    }
}