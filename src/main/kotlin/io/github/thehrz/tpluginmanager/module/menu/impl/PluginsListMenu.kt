package io.github.thehrz.tpluginmanager.module.menu.impl

import io.github.thehrz.tpluginmanager.module.menu.PluginIcon
import io.github.thehrz.tpluginmanager.module.plugin.PluginManager
import io.izzel.taboolib.internal.xseries.XMaterial
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.util.item.ItemBuilder
import io.izzel.taboolib.util.item.inventory.ClickEvent
import io.izzel.taboolib.util.item.inventory.linked.MenuLinked
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class PluginsListMenu(player: Player) : MenuLinked<PluginIcon>(player) {
    init {
        addButtonPreviousPage(47)
        addButtonNextPage(51)
    }

    override fun getTitle(): String {
        return TLocale.asString("Menu.Plugins-List.Title")
    }

    override fun getRows(): Int {
        return 6
    }

    override fun getElements(): MutableList<PluginIcon> {
        val elements = mutableListOf<PluginIcon>()

        PluginManager.getPluginsList().forEach {
            elements.add(PluginIcon(it))
        }

        return elements
    }

    override fun getSlots(): MutableList<Int> {
        return mutableListOf(9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 41, 43, 44)
    }

    override fun onBuild(inv: Inventory) {
        for (slot in listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 47, 48, 49, 50, 51, 53)) {
            inv.setItem(slot, ItemBuilder(XMaterial.GRAY_STAINED_GLASS_PANE).name(" ").colored().build())
        }

        if (hasPreviousPage()) {
            inv.setItem(46, ItemBuilder(XMaterial.GREEN_STAINED_GLASS_PANE).name("&f上一页").colored().build())
        } else {
            inv.setItem(46, ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).name("&8上一页").colored().build())
        }

        if (hasNextPage()) {
            inv.setItem(52, ItemBuilder(XMaterial.GREEN_STAINED_GLASS_PANE).name("&f下一页").colored().build())
        } else {
            inv.setItem(52, ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).name("&8下一页").colored().build())
        }
    }

    override fun onClick(clickEvent: ClickEvent, pluginIcon: PluginIcon) {
        PluginInfoMenu(pluginIcon).open(clickEvent.clicker)
    }

    override fun generateItem(player: Player, pluginIcon: PluginIcon, index: Int, slot: Int): ItemStack? {
        return ItemBuilder(Material.PAPER)
            .name((if (pluginIcon.enable) ChatColor.GREEN.toString() else ChatColor.RED.toString()) + pluginIcon.name)
            .lore(pluginIcon.description)
            .build()
    }

    companion object {
        fun open(player: Player) {
            PluginsListMenu(player).open()
        }
    }
}