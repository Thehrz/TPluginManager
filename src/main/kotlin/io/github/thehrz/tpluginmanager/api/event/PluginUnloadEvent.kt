package io.github.thehrz.tpluginmanager.api.event

import io.github.thehrz.tpluginmanager.api.plugin.ProxyPlugin
import taboolib.common.platform.event.ProxyEvent

/**
 * 当使用TPluginManager尝试卸载一个插件时触发
 *
 * @param plugin 插件实例
 */
class PluginUnloadEvent(val plugin: ProxyPlugin) : ProxyEvent()