package io.github.thehrz.tpluginmanager

import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.metrics.BMetrics
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject
import io.izzel.taboolib.module.locale.TLocale

object TPluginManager : Plugin() {

    @TInject(value = ["settings.yml"], locale = "Options.Language", migrate = true)
    lateinit var config: TConfig

    override fun onEnable() {
        BMetrics(plugin, 11184)
        TLocale.sendToConsole("Plugin.Enabled", plugin.description.version, KotlinVersion.CURRENT.toString())
    }
}