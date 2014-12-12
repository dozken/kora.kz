package controllers;

import java.util.ArrayList;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.Json;
import play.mvc.WebSocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WS {

	public static ArrayList<WebSocket.Out<JsonNode>> channels = new ArrayList<WebSocket.Out<JsonNode>>();

	public static WebSocket<JsonNode> socket = new WebSocket<JsonNode>() {

		// called when the websocket is established
		@Override
		public void onReady(WebSocket.In<JsonNode> in,
				WebSocket.Out<JsonNode> out) {

			channels.add(out);

			// When the message come.
			in.onMessage(new Callback<JsonNode>() {
				@Override
				public void invoke(JsonNode event) {
				}
			});

			// When the socket is closed.
			in.onClose(new Callback0() {
				@Override
				public void invoke() {
					ObjectNode event = Json.newObject();
					event.put("connection", "disconnected");
				}
			});

			ObjectNode event = Json.newObject();
			event.put("connection", "estabilished");
			out.write(event);
		}
	};

	public static void send(JsonNode event) {
		for (WebSocket.Out<JsonNode> channel : channels) {
			channel.write(event);
		}
	}
}
