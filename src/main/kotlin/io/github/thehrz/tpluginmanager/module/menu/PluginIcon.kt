package io.github.thehrz.tpluginmanager.module.menu

import io.github.thehrz.tpluginmanager.api.plugin.impl.BukkitPluginManager
import org.bukkit.ChatColor
import org.bukkit.plugin.Plugin
import taboolib.common.platform.console
import taboolib.module.lang.asLangText

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
                field.add(console().asLangText("menu-plugins-list-version", null, version))
            }

            if (authors.isNotEmpty()) {
                field.add(console().asLangText("menu-plugins-list-authors", null, authors))
            }

            if (softDepend.isNotEmpty()) {
                field.add(console().asLangText("menu-plugins-list-softdepend", null, softDepend))
            }

            if (depend.isNotEmpty()) {
                field.add(console().asLangText("menu-plugins-list-depend", null, depend))
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
                        BukkitPluginManager.getPlugin(softDepend)?.let { ChatColor.GREEN.toString() + "$softDepend " }
                            ?: let { ChatColor.RED.toString() + "$softDepend " }
                }
            }

            if (description.depend.isNotEmpty()) {
                description.depend.forEach { depend ->
                    this.depend +=
                        BukkitPluginManager.getPlugin(depend)?.let { ChatColor.GREEN.toString() + "$depend " }
                            ?: let { ChatColor.RED.toString() + "$depend " }
                }
            }
        }
    }
}