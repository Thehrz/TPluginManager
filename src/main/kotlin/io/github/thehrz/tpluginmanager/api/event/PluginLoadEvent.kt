package io.github.thehrz.tpluginmanager.api.event

import taboolib.common.platform.event.ProxyEvent
import java.io.File

/**
 * 当使用TPluginManager尝试加载一个插件时触发
 *
 * @param pluginFile 插件文件
 */
class PluginLoadEvent(pluginFile: File) : ProxyEvent()