package io.github.thehrz.tpluginmanager

import taboolib.common.platform.Plugin
import taboolib.common.platform.console
import taboolib.common.platform.pluginVersion
import taboolib.common.platform.runningPlatform
import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile
import taboolib.module.lang.sendLang
import taboolib.module.metrics.Metrics

object TPluginManager : Plugin() {
    @Config(value = "settings.yml", migrate = true)
    lateinit var config: SecuredFile
        private set

    override fun onEnable() {
        Metrics(11184, pluginVersion, runningPlatform)
        console().sendLang("plugin-enabled", pluginVersion, KotlinVersion.CURRENT.toString())
    }
}