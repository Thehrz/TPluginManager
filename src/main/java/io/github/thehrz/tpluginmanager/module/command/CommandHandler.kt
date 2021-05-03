package io.github.thehrz.tpluginmanager.module.command

import io.github.thehrz.tpluginmanager.module.command.impl.Disable
import io.github.thehrz.tpluginmanager.module.command.impl.Enable
import io.github.thehrz.tpluginmanager.module.command.impl.Menu
import io.izzel.taboolib.module.command.base.BaseCommand
import io.izzel.taboolib.module.command.base.BaseMainCommand
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.command.base.SubCommand

@BaseCommand(name = "TPluginManager", aliases = ["tpm"], permission = "tpluginmanager.access")
class CommandHandler : BaseMainCommand() {

    @SubCommand(permission = "enable", description = "Enable a plugin")
    val enable: BaseSubCommand = Enable()

    @SubCommand(permission = "disable", description = "Disable a plugin")
    val disable: BaseSubCommand = Disable()

    @SubCommand(permission = "menu", description = "Open management menu")
    val menu: BaseSubCommand = Menu()
}