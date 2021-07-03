package io.github.thehrz.tpluginmanager.module.service

import io.github.thehrz.tpluginmanager.TPluginManager
import io.github.thehrz.tpluginmanager.module.command.CommandHandler
import io.github.thehrz.tpluginmanager.module.plugin.PluginManager
import io.izzel.taboolib.module.inject.TSchedule
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
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

    @TSchedule(period = 100)
    fun run() {
        var key: WatchKey?
        while (watchService.poll().also { key = it } != null) {
            for (event in key!!.pollEvents()) {
                val pluginFile = file.resolve(event.context().toString())

                try {
                    val pluginDescription = TPluginManager.plugin.pluginLoader.getPluginDescription(pluginFile)

                    TLocale.sendTo(
                        Bukkit.getConsoleSender(),
                        "Service.Auto-Load-Plugins.Found",
                        pluginDescription.name,
                        pluginFile.name,
                        pluginDescription.version
                    )
                    if (TPluginManager.config.getBoolean("Service.Auto-Load-Plugins.Enable")) {
                        TLocale.sendTo(Bukkit.getConsoleSender(), "Service.Auto-Load-Plugins.Load")
                        PluginManager.loadPlugin(pluginFile, Bukkit.getConsoleSender())
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

