package io.github.thehrz.tpluginmanager.module.command

import io.github.thehrz.tpluginmanager.api.plugin.impl.BukkitPluginManager
import io.github.thehrz.tpluginmanager.module.menu.impl.MainMenu
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command
import taboolib.module.lang.sendLang

object CommandHandler {
    // 已经开启的插件
    val enablePlugins = BukkitPluginManager.getPluginsListString().toMutableList()

    // 已经关闭的插件
    val disablePlugins = mutableListOf<String>()

    // 准备加载的插件
    val loadPlugins = mutableListOf<String>()

    @Awake(LifeCycle.ENABLE)
    fun commands() {
        command(name = "TPluginManager", aliases = listOf("tpm"), permission = "tpluginmanager.access") {
            literal("enable") {
                dynamic(optional = true) {
                    suggestion<ProxyPlayer> { _, _ ->
                        disablePlugins
                    }
                    execute<ProxyPlayer> { sender, _, argument ->
                        if (BukkitPluginManager.enablePlugin(argument, sender)) {
                            enablePlugins.add(argument)
                            disablePlugins.remove(argument)
                        }
                    }
                }
            }
            literal("disable") {
                dynamic(optional = true) {
                    suggestion<ProxyPlayer> { _, _ ->
                        enablePlugins
                    }
                    execute<ProxyPlayer> { sender, _, argument ->
                        if (BukkitPluginManager.disablePlugin(argument, sender)) {
                            enablePlugins.remove(argument)
                            disablePlugins.add(argument)
                        }
                    }
                }
            }
            literal("load") {
                dynamic(optional = true) {
                    suggestion<ProxyPlayer> { _, _ ->
                        loadPlugins
                    }
                    execute<ProxyPlayer> { sender, _, argument ->
                        if (BukkitPluginManager.loadPlugin(argument, sender)) {
                            loadPlugins.remove(argument)
                            enablePlugins.add(argument)
                        }
                    }
                }
            }
            literal("unload") {
                dynamic(optional = true) {
                    suggestion<ProxyPlayer> { _, _ ->
                        enablePlugins
                    }
                    execute<ProxyPlayer> { sender, _, argument ->
                        if (BukkitPluginManager.unloadPlugin(argument, sender)) {
                            enablePlugins.remove(argument)
                        }
                    }
                }
            }
            literal("reload") {
                dynamic(optional = true) {
                    suggestion<ProxyPlayer> { _, _ ->
                        enablePlugins
                    }
                    execute<ProxyPlayer> { sender, _, argument ->
                        BukkitPluginManager.reloadPlugin(argument, sender)
                    }
                }
            }
            literal("menu") {
                execute<ProxyPlayer> { sender, _, _ ->
                    MainMenu.open(sender.cast())
                }
                incorrectSender { sender, context ->
                    sender.sendLang("menu-not-player")
                }
            }
            incorrectCommand { sender, context, index, state ->

            }
        }
    }
}