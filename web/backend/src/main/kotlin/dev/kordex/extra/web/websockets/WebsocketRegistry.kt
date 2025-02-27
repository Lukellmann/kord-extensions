/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.kordex.extra.web.websockets

import com.kotlindiscord.kord.extensions.events.ExtensionStateEvent
import com.kotlindiscord.kord.extensions.extensions.ExtensionState
import com.kotlindiscord.kord.extensions.koin.KordExKoinComponent
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

public class WebsocketRegistry : KordExKoinComponent {
	private val socketBuilders: MutableMap<String, WebsocketBuilder> = mutableMapOf()
	private val activeSockets: MutableList<Websocket> = mutableListOf()

	public suspend fun handle(session: DefaultWebSocketServerSession) {
		val path = session.call.parameters.getAll("path")
			?.joinToString("/")

		val builder = socketBuilders[path]

		val socket = builder?.builder?.invoke(session)
			?: return session.call.respond(HttpStatusCode.NotFound)

		if (!socket.setup(session.call)) {
			if (
				session.call.response.status() == null &&
				!session.call.response.isSent &&
				!session.call.response.isCommitted
			) {
				session.call.respond(HttpStatusCode.Forbidden)
			}

			return
		}

		activeSockets.add(socket)
		socket.setupSocket(this, builder, path!!)
	}

	public suspend fun handleExtensionState(event: ExtensionStateEvent) {
		if (event.state == ExtensionState.UNLOADING) {
			socketBuilders
				.filter { it.value.extension == event.extension.name }
				.forEach { entry ->
					val builder = entry.value

					socketBuilders.remove(entry.key)

					activeSockets
						.filter { it.builder.extension == builder.extension }
						.forEach {
							it.close(CloseReason(CloseReason.Codes.NORMAL, "Extension was unloaded."))
						}
				}
		}
	}

	public fun add(path: String, builder: WebsocketBuilder): Boolean {
		if (path in socketBuilders) {
			return false
		}

		socketBuilders[path] = builder

		return true
	}

	public suspend fun remove(path: String): WebsocketBuilder? {
		activeSockets.filter { it.path == path }.forEach {
			it.close()
		}

		return socketBuilders.remove(path)
	}

	public fun removeSocket(socket: Websocket): Boolean =
		activeSockets.remove(socket)

	public suspend fun removeAll() {
		socketBuilders.clear()

		activeSockets.forEach { it.close() }
		activeSockets.clear()
	}
}
