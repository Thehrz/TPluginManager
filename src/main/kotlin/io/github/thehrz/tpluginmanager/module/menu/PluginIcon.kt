package io.github.thehrz.tpluginmanager.module.menu

import io.github.thehrz.tpluginmanager.api.manager.IPluginManager
import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import org.bukkit.ChatColor
import taboolib.common.platform.function.console
import taboolib.common.platform.function.implementations
import taboolib.module.lang.asLangText

class PluginIcon(proxyPlugin: ProxyPlugin) {
    val isEnabled: Boolean = proxyPlugin.isEnabled
    val name: String = proxyPlugin.name
    private var version: String = proxyPlugin.version
    private var authors: String = String()
    private var softDepend: String = String()
    private var depend: String = String()
    val description: MutableList<String> = mutableListOf()
        get() {
            if (version.isNotEmpty()) {
                field.add(console().asLangText("menu-plugins-list-version", version))
            }

            if (authors.isNotEmpty()) {
                field.add(console().asLangText("menu-plugins-list-authors", authors))
            }

            if (softDepend.isNotEmpty()) {
                field.add(console().asLangText("menu-plugins-list-softdepend", softDepend))
            }

            if (depend.isNotEmpty()) {
                field.add(console().asLangText("menu-plugins-list-depend", depend))
            }

            return field
        }

    init {
        proxyPlugin.authors.forEach {
            authors += "$it "
        }

        if (proxyPlugin.softDepend.isNotEmpty()) {
            proxyPlugin.softDepend.forEach { softDepend ->
                this.softDepend +=
                    implementations<IPluginManager>().getPlugin(softDepend)
                        ?.let { ChatColor.GREEN.toString() + "$softDepend " }
                        ?: let { ChatColor.RED.toString() + "$softDepend " }
            }
        }

        if (proxyPlugin.depend.isNotEmpty()) {
            proxyPlugin.depend.forEach { depend ->
                this.depend +=
                    implementations<IPluginManager>().getPlugin(depend)
                        ?.let { ChatColor.GREEN.toString() + "$depend " }
                        ?: let { ChatColor.RED.toString() + "$depend " }
            }
        }
    }
}