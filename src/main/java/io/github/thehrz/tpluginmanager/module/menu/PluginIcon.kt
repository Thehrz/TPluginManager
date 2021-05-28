package io.github.thehrz.tpluginmanager.module.menu

import io.github.thehrz.tpluginmanager.module.plugin.PluginManager
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.ChatColor
import org.bukkit.plugin.Plugin

class PluginIcon(plugin: Plugin) {
    val enable: Boolean = plugin.isEnabled
    val name: String = plugin.name
    private var version: String = String()
    private var authors: String = String()
    private var softDepend: String = String()
    private var depend: String = String()
    val description: MutableList<String> = mutableListOf()
        get() {
            if (version.isNotEmpty()) {
                field.add(TLocale.asString("Menu.Plugins-List.Version", version))
            }

            if (authors.isNotEmpty()) {
                field.add(TLocale.asString("Menu.Plugins-List.Authors", authors))
            }

            if (softDepend.isNotEmpty()) {
                field.add(TLocale.asString("Menu.Plugins-List.SoftDepend", softDepend))
            }

            if (depend.isNotEmpty()) {
                field.add(TLocale.asString("Menu.Plugins-List.Depend", depend))
            }

            return field
        }

    init {
        plugin.description.let { description ->

            version = description.version

            description.authors.forEach {
                authors += "$it "
            }

            if (description.softDepend.isNotEmpty()) {
                description.softDepend.forEach { softDepend ->
                    this.softDepend +=
                        PluginManager.getPlugin(softDepend)?.let { ChatColor.GREEN.toString() + "$softDepend " }
                            ?: let { ChatColor.RED.toString() + "$softDepend " }
                }
            }

            if (description.depend.isNotEmpty()) {
                description.depend.forEach { depend ->
                    this.depend +=
                        PluginManager.getPlugin(depend)?.let { ChatColor.GREEN.toString() + "$depend " }
                            ?: let { ChatColor.RED.toString() + "$depend " }
                }
            }
        }
    }
}