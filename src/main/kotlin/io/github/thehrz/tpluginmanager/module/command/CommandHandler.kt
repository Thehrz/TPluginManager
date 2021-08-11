package io.github.thehrz.tpluginmanager.module.command

import io.github.thehrz.tpluginmanager.api.manager.IPluginManager
import io.github.thehrz.tpluginmanager.module.menu.impl.MainMenu
import taboolib.common.platform.*
import taboolib.module.chat.TellrawJson
import taboolib.module.lang.asLangText

@CommandHeader(name = "tpluginmanager", aliases = ["tpm", "pluginmanager"], permission = "tpluginmanager.access")
object CommandHandler {
    // 已经开启的插件
    private val enablePlugins = implementations<IPluginManager>().getPluginsListString().toMutableList()

    // 已经关闭的插件
    private val disablePlugins = mutableListOf<String>()

    // 准备加载的插件
    private val loadPlugins = mutableListOf<String>()



    @CommandBody
    val main = mainCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            generateMainHelper(sender)
        }
    }


    @CommandBody(permission = "enable", optional = true)
    val enable = subCommand {
        dynamic {
            suggestion<ProxyPlayer> { _, _ ->
                disablePlugins
            }
            execute<ProxyPlayer> { sender, _, argument ->
                enablePlugins.add(argument)
                if (implementations<IPluginManager>().enablePlugin(argument, sender)) {
                    disablePlugins.remove(argument)
                }
            }
        }
    }

    @CommandBody(permission = "disable", optional = true)
    val disable = subCommand {
        dynamic {
            suggestion<ProxyPlayer> { _, _ ->
                enablePlugins
            }
            execute<ProxyPlayer> { sender, _, argument ->
                if (implementations<IPluginManager>().disablePlugin(argument, sender)) {
                    enablePlugins.remove(argument)
                    disablePlugins.add(argument)
                }
            }
        }
    }

    @CommandBody(permission = "load", optional = true)
    val load = subCommand {
        dynamic {
            suggestion<ProxyPlayer> { _, _ ->
                loadPlugins
            }
            execute<ProxyPlayer> { sender, _, argument ->
                if (implementations<IPluginManager>().loadPlugin(argument, sender)) {
                    loadPlugins.remove(argument)
                    enablePlugins.add(argument)
                }
            }
        }
    }

    @CommandBody(permission = "unload", optional = true)
    val unload = subCommand {
        dynamic {
            suggestion<ProxyPlayer> { _, _ ->
                enablePlugins
            }
            execute<ProxyPlayer> { sender, _, argument ->
                if (implementations<IPluginManager>().unloadPlugin(argument, sender)) {
                    enablePlugins.remove(argument)
                }
            }
        }
    }

    @CommandBody(permission = "reload", optional = true)
    val reload = subCommand {
        dynamic {
            suggestion<ProxyPlayer> { _, _ ->
                enablePlugins
            }
            execute<ProxyPlayer> { sender, _, argument ->
                implementations<IPluginManager>().reloadPlugin(argument, sender)
            }
        }
    }

    @CommandBody(permission = "menu", optional = true)
    val menu = subCommand {
        execute<ProxyPlayer> { sender, _, _ ->
            MainMenu.open(sender.cast())
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