package org.chat.application;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    private SessionsManager SocketSessionManager;

    public SocketTextHandler(SessionsManager SocketSessionManager) {
        this.SocketSessionManager = SocketSessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.SocketSessionManager.addSession(session, session.getId());

        String userConnMsg = "----- " + session.getAttributes().get("userName") +" Connected -------";
        broadCastMessage(session, userConnMsg, true);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.SocketSessionManager.removeSession(session);
        String userDisconnectedMsg = "----- " + session.getAttributes().get("userName") +" Disconnected -----";
        broadCastMessage(session, userDisconnectedMsg, true);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {

        String incomingMessage = message.getPayload();
        broadCastMessage(session, message.getPayload(), false);
    }

    private void broadCastMessage(WebSocketSession originator, String message,boolean isSystemMsg)
    {
        List<WebSocketSession> sessionList = this.SocketSessionManager.getSessionList();

        for(WebSocketSession socketSession: sessionList)
        {
            try {
                socketSession.sendMessage(new TextMessage(isSystemMsg ? message : originator.getAttributes().get("userName") + " : " + message));
            }catch (Exception e)
            {
                System.out.println("Caught Exception: " + e.toString());
            }
        }
    }
}
