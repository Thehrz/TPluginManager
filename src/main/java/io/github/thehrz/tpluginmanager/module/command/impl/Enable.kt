package io.github.thehrz.tpluginmanager.module.command.impl

import io.github.thehrz.tpluginmanager.module.plugin.PluginManager
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class Enable : BaseSubCommand() {
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