package io.github.thehrz.tpluginmanager.module.command

import io.github.thehrz.tpluginmanager.module.menu.impl.MainMenu
import io.github.thehrz.tpluginmanager.module.plugin.PluginManager
import io.izzel.taboolib.module.command.base.*
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@BaseCommand(name = "TPluginManager", aliases = ["tpm"], permission = "tpluginmanager.access")
class CommandHandler : BaseMainCommand() {

    /**
     * enable命令
     *
     * 用于开启一个插件
     */
    @SubCommand(permission = "enable")
    val enable: BaseSubCommand = object : BaseSubCommand() {
        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
            Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().getPlugin(args[0])!!)
        }

        override fun getArguments(): Array<Argument> {
            return arrayOf(Argument("plugin", true) { PluginManager.getPluginsListString() })
        }

        override fun getDescription(): String {
            return TLocale.asString("Commands.Enable")
        }
    }

    /**
     * disable命令
     *
     * 用于关闭一个插件
     */
    @SubCommand(permission = "disable")
    val disable: BaseSubCommand = object : BaseSubCommand() {
        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
            PluginManager.disablePlugin(args[0], sender)
        }

        override fun getArguments(): Array<Argument> {
            return arrayOf(Argument("plugin", true) { PluginManager.getPluginsListString() })
        }

        override fun getDescription(): String {
            return TLocale.asString("Commands.Disable.Display")
        }
    }

    /**
     * menu命令
     *
     * 用于打开菜单界面
     */
    @SubCommand(permission = "menu")
    val menu: BaseSubCommand = object : BaseSubCommand() {
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
}