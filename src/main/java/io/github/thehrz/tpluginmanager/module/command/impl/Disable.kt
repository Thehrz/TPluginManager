package io.github.thehrz.tpluginmanager.module.command.impl

import io.github.thehrz.tpluginmanager.module.plugin.PluginManager
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class Disable : BaseSubCommand() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        PluginManager.disablePlugin(args[0], sender)
    }

    override fun getArguments(): Array<Argument> {
        return arrayOf(Argument("plugin", true) { PluginManager.getPluginsListString() })
    }

    override fun getDescription(): String {
        return TLocale.asString("Commands.Disable")
    }
}