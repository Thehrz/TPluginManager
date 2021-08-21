package io.github.thehrz.tpluginmanager.module.command

import io.github.thehrz.tpluginmanager.api.manager.IPluginManager
import io.github.thehrz.tpluginmanager.api.manager.Result
import io.github.thehrz.tpluginmanager.module.menu.impl.MainMenu
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.implementations
import taboolib.common.platform.function.pluginVersion
import taboolib.module.chat.TellrawJson
import taboolib.module.lang.asLangText
import taboolib.module.lang.sendLang

@CommandHeader(name = "tpluginmanager", aliases = ["tpm", "pluginmanager"], permission = "tpluginmanager.access")
object CommandHandler {
    // 已经开启的插件
    private val enablePlugins = implementations<IPluginManager>().getPluginsListString().toMutableList()

    // 已经关闭的插件
    private val disablePlugins = mutableListOf<String>()

    // 准备加载的插件
    val loadPlugins = mutableListOf<String>()

    @CommandBody
    val main = mainCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            generateMainHelper(sender)
        }
    }

    @CommandBody(permission = "enable", aliases = ["e"], optional = true)
    val enable = subCommand {
        dynamic {
            suggestion<ProxyCommandSender>(true) { _, _ ->
                disablePlugins
            }
            execute<ProxyCommandSender> { sender, _, argument ->
                when (implementations<IPluginManager>().enablePlugin(argument, sender)) {
                    Result.SUCCESS -> {
                        disablePlugins.remove(argument)
                        enablePlugins.add(argument)
                    }
                    Result.FAIL -> disablePlugins.remove(argument)
                    Result.NOTFOUND -> {
                        sender.sendLang("commands-unknown", argument)
                    }
                }
            }
        }
    }

    @CommandBody(permission = "disable", aliases = ["d"], optional = true)
    val disable = subCommand {
        dynamic {
            suggestion<ProxyCommandSender>(true) { _, _ ->
                enablePlugins
            }
            execute<ProxyCommandSender> { sender, _, argument ->
                when (implementations<IPluginManager>().disablePlugin(argument, sender)) {
                    Result.FAIL -> enablePlugins.remove(argument)
                    Result.SUCCESS -> {
                        enablePlugins.remove(argument)
                        disablePlugins.add(argument)
                    }
                    Result.NOTFOUND -> {
                        sender.sendLang("commands-unknown", argument)
                    }
                }
            }
        }
    }

    @CommandBody(permission = "load", aliases = ["l"], optional = true)
    val load = subCommand {
        dynamic(optional = true) {
            suggestion<ProxyCommandSender>(true) { _, _ ->
                loadPlugins
            }
            execute<ProxyCommandSender> { sender, _, argument ->
                when (implementations<IPluginManager>().loadPlugin(argument, sender)) {
                    Result.SUCCESS -> {
                        loadPlugins.remove(argument)
                        enablePlugins.add(argument)
                    }
                    Result.FAIL -> loadPlugins.remove(argument)
                    Result.NOTFOUND -> {
                        sender.sendLang("commands-load-already-running", argument)
                    }
                }
            }
        }
    }

    @CommandBody(permission = "unload", aliases = ["u"], optional = true)
    val unload = subCommand {
        dynamic(optional = true) {
            suggestion<ProxyCommandSender>(true) { _, _ ->
                enablePlugins
            }
            execute<ProxyCommandSender> { sender, _, argument ->
                when (implementations<IPluginManager>().unloadPlugin(argument, sender)) {
                    Result.FAIL, Result.SUCCESS -> enablePlugins.remove(argument)
                    Result.NOTFOUND -> {
                        sender.sendLang("commands-unknown", argument)
                    }
                }
            }
        }
    }

    @CommandBody(permission = "reload", aliases = ["r"], optional = true)
    val reload = subCommand {
        dynamic(optional = true) {
            suggestion<ProxyCommandSender>(true) { _, _ ->
                enablePlugins
            }
            execute<ProxyCommandSender> { sender, _, argument ->
                when (implementations<IPluginManager>().reloadPlugin(argument, sender)) {
                    Result.SUCCESS -> { }
                    Result.FAIL -> enablePlugins.remove(argument)
                    Result.NOTFOUND -> {
                        sender.sendLang("commands-unknown", argument)
                    }
                }
            }
        }
    }

    @CommandBody(permission = "menu", aliases = ["m"], optional = true)
    val menu = subCommand {
        execute<ProxyPlayer> { sender, _, _ ->
            MainMenu.open(sender.cast())
        }
    }

    @CommandBody(permission = "clear", aliases = ["c"], optional = true)
    val clear = subCommand {
        execute<ProxyPlayer> { sender, _, _ ->

        }
    }

    // 借鉴一下下TrMenu~
    private fun generateMainHelper(proxySender: ProxyCommandSender) {
        proxySender.sendMessage("")
        TellrawJson()
            .append("  ").append("§3TPluginManager")
            .hoverText("§7TPluginManager is a faster and more functional plugin manager.")
            .append(" ").append("§f$pluginVersion")
            .hoverText("§7Plugin version: §2$pluginVersion")
            .append(" ")
            .sendTo(proxySender)

        TellrawJson()
            .append("  §7${proxySender.asLangText("command-help-type")}: ").append("§f/trmenu §8[...]")
            .hoverText("§f/tpluginmanager §8[...]")
            .suggestCommand("/tpluginmanager ")
            .sendTo(proxySender)
        proxySender.sendMessage("  §7${proxySender.asLangText("command-help-args")}:")

        fun displayArg(name: String, description: String) {
            TellrawJson()
                .append("    §8- ").append("§f$name")
                .hoverText("§f/tpluginmanager $name §8- §7$description")
                .suggestCommand("/tpluginmanager $name ")
                .sendTo(proxySender)
            proxySender.sendMessage("      §7$description")
        }

        displayArg("enable", proxySender.asLangText("commands-enable-description"))
        displayArg("disable", proxySender.asLangText("commands-disable-description"))
        displayArg("load", proxySender.asLangText("commands-load-description"))
        displayArg("unload", proxySender.asLangText("commands-unload-description"))
        displayArg("reload", proxySender.asLangText("commands-reload-description"))
        displayArg("menu", proxySender.asLangText("commands-menu-description"))
        proxySender.sendMessage("")
    }
}