package io.github.thehrz.tpluginmanager.module.service

import io.github.thehrz.tpluginmanager.TPluginManager
import io.github.thehrz.tpluginmanager.api.plugin.impl.BukkitPluginManager
import io.github.thehrz.tpluginmanager.module.command.CommandHandler
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.console
import taboolib.common.platform.submit
import taboolib.module.lang.sendLang
import taboolib.platform.BukkitPlugin
import java.io.File
import java.nio.file.*

object PluginFileWatcher {
    private lateinit var watchService: WatchService
    private val file = File("plugins")

    init {
        try {
            watchService = FileSystems.getDefault().newWatchService()
            Paths.get(file.toURI()).register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Awake(LifeCycle.ACTIVE)
    fun run() {
        submit(period = 100) {
            var key: WatchKey?
            while (watchService.poll().also { key = it } != null) {
                for (event in key!!.pollEvents()) {
                    val pluginFile = file.resolve(event.context().toString())

                    try {
                        val pluginDescription = BukkitPlugin.getInstance().pluginLoader.getPluginDescription(pluginFile)

                        console().sendLang(
                            "service-auto-load-plugins-found",
                            pluginDescription.name,
                            pluginFile.name,
                            pluginDescription.version
                        )
                        if (TPluginManager.config.getBoolean("Service.Auto-Load-Plugins.Enable")) {
                            console().sendLang("service-auto-load-plugins-load")
                            BukkitPluginManager.loadPlugin(pluginFile, console())
                        } else {
                            CommandHandler.loadPlugins.add(pluginDescription.name)
                        }
                    } catch (e: Exception) {
                    }
                }
                key!!.reset()
            }
        }
    }
}

