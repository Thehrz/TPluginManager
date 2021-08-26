package io.github.thehrz.tpluginmanager.api.plugin

/**
 * 代理插件, 用于在各个平台中交换插件信息
 */
@Suppress("UNCHECKED_CAST")
interface ProxyPlugin {
    val name: String

    val isEnabled: Boolean

    val version: String

    val authors: List<String>

    val depend: List<String>

    val softDepend: List<String>

    val origin: Any

    /**
     * 获取原来的插件实例
     */
    fun <T> cast(): T {
        return origin as T
    }
}