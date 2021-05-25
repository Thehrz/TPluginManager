package io.github.thehrz.tpluginmanager.module.menu.impl

import io.github.thehrz.tpluginmanager.module.menu.PluginIcon
import io.github.thehrz.tpluginmanager.module.plugin.PluginManager
import io.izzel.taboolib.internal.xseries.XMaterial
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.util.item.ItemBuilder
import io.izzel.taboolib.util.item.Items
import io.izzel.taboolib.util.item.inventory.ClickEvent
import io.izzel.taboolib.util.item.inventory.linked.MenuLinked
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
        return TLocale.asString("Menu.Plugins-List")
    }

    override fun getRows(): Int {
        return 6
    }

    override fun getElements(): MutableList<PluginIcon> {
        val elements = mutableListOf<PluginIcon>()

        PluginManager.getPluginsList().forEach {
            elements.add(PluginIcon(it.description.name))
        }

        return elements
    }

    override fun getSlots(): MutableList<Int> {
        return Items.INVENTORY_CENTER.toMutableList()
    }

    override fun onBuild(inv: Inventory) {
        if (hasPreviousPage()) {
            inv.setItem(47, ItemBuilder(XMaterial.SPECTRAL_ARROW).name("&f上一页").colored().build())
        } else {
            inv.setItem(47, ItemBuilder(XMaterial.ARROW).name("&8上一页").colored().build())
        }

        if (hasNextPage()) {
            inv.setItem(51, ItemBuilder(XMaterial.SPECTRAL_ARROW).name("&f下一页").colored().build())
        } else {
            inv.setItem(51, ItemBuilder(XMaterial.ARROW).name("&8下一页").colored().build())
        }
    }

    override fun onClick(clickEvent: ClickEvent, pluginIcon: PluginIcon) {

    }

    override fun generateItem(player: Player, pluginIcon: PluginIcon, index: Int, slot: Int): ItemStack? {
        return ItemBuilder(Material.PAPER).name(pluginIcon.name).build()
    }

    companion object {
        fun open(player: Player) {
            PluginsListMenu(player).open()
        }
    }
}