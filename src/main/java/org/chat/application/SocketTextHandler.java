package org.chat.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
/**
 * @author Saransh Gupta
 */
@Component
public class SocketTextHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(SocketTextHandler.class);
    private SessionsManager SocketSessionManager;

    public SocketTextHandler(SessionsManager SocketSessionManager) {
        this.SocketSessionManager = SocketSessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.SocketSessionManager.addSession(session);

        logger.info("User Connected to Server. UserName: " + session.getAttributes().get("userName") +" UserId: " + session.getId());

        String userConnMsg = "----- " + session.getAttributes().get("userName") +" Connected -------";
        broadCastMessage(session, userConnMsg, true);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.SocketSessionManager.removeSession(session);

        logger.info("User Disconnected to Server. UserName: " + session.getAttributes().get("userName") +" UserId: " + session.getId());

        String userDisconnectedMsg = "----- " + session.getAttributes().get("userName") +" Disconnected -----";
        broadCastMessage(session, userDisconnectedMsg, true);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {

        String incomingMessage = message.getPayload();

        logger.info("Incoming TextMessage --> From: {" + session.getAttributes().get("userName") +"} Message: {" + message.getPayload() + "} UserId: {" + session.getId()+"}");

        broadCastMessage(session, message.getPayload(), false);
    }

    private void broadCastMessage(WebSocketSession originator, String message,boolean isSystemMsg)
    {
        List<WebSocketSession> sessionList = this.SocketSessionManager.getSessionList();

        String currentActiveUsersList = "";

        for(WebSocketSession socketSession: sessionList)
        {
            String userName = (String) originator.getAttributes().get("userName");
            try {
                socketSession.sendMessage(new TextMessage(isSystemMsg ? message : userName + " : " + message));
            }catch (Exception e)
            {
                System.out.println("Caught Exception: " + e.toString());
            }

            currentActiveUsersList+=socketSession.getAttributes().get("userName")+",";
        }

        logger.info("CurrentActiveUsers: {" + currentActiveUsersList.substring(0, currentActiveUsersList.length()-1)+"}");
    }
}
