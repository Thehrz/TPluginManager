package io.github.thehrz.tpluginmanager.module.taskmanager

import io.github.thehrz.tpluginmanager.module.menu.centeredSlots
import io.github.thehrz.tpluginmanager.utils.Heads
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitTask
import taboolib.common.platform.function.console
import taboolib.library.xseries.XMaterial
import taboolib.module.lang.asLangText
import taboolib.module.ui.Menu
import taboolib.module.ui.buildMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem

object TaskManagerMenu : Menu(console().asLangText("menu-task-manager-title")) {
    override fun build(): Inventory =
        buildMenu<Linked<BukkitTask>>(title) {
            handLocked(true)
            rows(6)
            elements {
                Bukkit.getScheduler().pendingTasks
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
            onGenerate(false) { _, bukkitTask, _, _ ->
                buildItem(XMaterial.PLAYER_HEAD) {
                    name = "&a${bukkitTask.taskId}"
                    lore += bukkitTask.description()
                    skullTexture = ItemBuilder.SkullTexture(Heads.T.texture)
                    colored()
                }
            }
            onBuild(true) {
                for (slot in listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 47, 48, 49, 50, 51, 53)) {
                    it.setItem(slot, buildItem(XMaterial.GRAY_STAINED_GLASS_PANE) {
                        name = " "
                    })
                }
            }
        }

    private fun BukkitTask.description() =
        mutableListOf(
            console().asLangText("menu-task-manager-owner", owner),
            console().asLangText("menu-task-manager-issync", isSync),
            console().asLangText("menu-task-manager-iscancelled", isCancelled)
        )

    fun open(player: Player) {
        player.openInventory(build())
    }
}