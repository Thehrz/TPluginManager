package io.github.thehrz.tpluginmanager.module.menu.impl

import io.github.thehrz.tpluginmanager.module.menu.Menu
import io.izzel.taboolib.module.locale.TLocale

object PluginsListMenu : Menu(TLocale.asString("Menu.Plugins-List"), 3) {
    init {
        menu.buildAsync {

        }
    }
}