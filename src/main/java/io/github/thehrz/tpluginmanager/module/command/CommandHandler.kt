package io.github.thehrz.tpluginmanager.module.command

import io.github.thehrz.tpluginmanager.module.menu.impl.MainMenu
import io.github.thehrz.tpluginmanager.module.plugin.PluginManager
import io.izzel.taboolib.module.command.base.*
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@BaseCommand(name = "TPluginManager", aliases = ["tpm"], permission = "tpluginmanager.access")
class CommandHandler : BaseMainCommand() {
    companion object {
        val disablePlugins = PluginManager.getPluginsListString().toMutableList()
        val enablePlugins = mutableListOf<String>()
    }

    /**
     * enable命令
     *
     * 用于开启一个插件
     */
    @SubCommand(permission = "enable")
    val enable: BaseSubCommand = object : BaseSubCommand() {
        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
            PluginManager.enablePlugin(args[0], sender)
        }

        override fun getArguments(): Array<Argument> {
            return arrayOf(Argument("plugin", true) { enablePlugins })
        }

        override fun getDescription(): String {
            return TLocale.asString("Commands.Enable.Display")
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
            return arrayOf(Argument("plugin", true) { disablePlugins })
        }

        override fun getDescription(): String {
            return TLocale.asString("Commands.Disable.Display")
        }
    }

    @SubCommand(permission = "load")
    val load: BaseSubCommand = object : BaseSubCommand() {
        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
            PluginManager.loadPlugin(args[0], sender)
        }

        override fun getDescription(): String {
            return TLocale.asString("Commands.Load.Display")
        }
    }

    @SubCommand(permission = "unload")
    val unload: BaseSubCommand = object : BaseSubCommand() {
        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        }

        override fun getArguments(): Array<Argument> {
            return arrayOf(Argument("plugin", true) { disablePlugins })
        }

        override fun getDescription(): String {
            return TLocale.asString("Commands.Unload.Display")
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