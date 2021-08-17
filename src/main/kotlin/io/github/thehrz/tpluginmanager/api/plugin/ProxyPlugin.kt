package io.github.thehrz.tpluginmanager.api.plugin

@Suppress("UNCHECKED_CAST")
interface ProxyPlugin {
    val name: String

    val isEnabled: Boolean

    val version: String

    val authors: List<String>

    val depend: List<String>

    val softDepend: List<String>

    val origin: Any

    fun <T> cast(): T {
        return origin as T
    }
}