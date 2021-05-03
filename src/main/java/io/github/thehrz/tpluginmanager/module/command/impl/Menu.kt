package io.github.thehrz.tpluginmanager.module.command.impl

import io.github.thehrz.tpluginmanager.module.menu.impl.MainMenu
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Menu : BaseSubCommand() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        if (sender is Player) {
            MainMenu.open(sender)
        } else {
            TLocale.sendTo(sender, "Menu.Not-Player")
        }
    }

    override fun getDescription(): String {
        return TLocale.asString("Commands.Menu")
    }
}