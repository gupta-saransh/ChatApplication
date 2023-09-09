package org.chat.application;

import org.apache.logging.log4j.message.Message;
import org.chat.application.constants.AppConstants;
import org.chat.application.util.JsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.chat.application.constants.AppConstants.MessageType.*;

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

        try {
            this.SocketSessionManager.addSession(session);
            logger.info("User Connected to Server. UserName: " + session.getAttributes().get("userName") +" UserId: " + session.getId());

            String userConnMsg = "----- " + session.getAttributes().get("userName") +" Connected -------";


            List<String> userList = this.SocketSessionManager.getUserList();
            broadCastMessage(session, userConnMsg, SYS_MESSAGE, userList);
        }
        catch (Exception e)
        {
            String message = e.toString();
            sendMessageToClient(session, message, ERROR_MESSAGE);
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.SocketSessionManager.removeSession(session);

        logger.info("User Disconnected to Server. UserName: " + session.getAttributes().get("userName") +" UserId: " + session.getId());

        String userDisconnectedMsg = "----- " + session.getAttributes().get("userName") +" Disconnected -----";
        broadCastMessage(session, userDisconnectedMsg, SYS_MESSAGE);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {

        String incomingMessage = message.getPayload();
        String userName = getUserName(session);
        logger.info("Incoming TextMessage --> From: {" + userName +"} Message: {" + incomingMessage + "} UserId: {" + session.getId()+"}");

        broadCastMessage(session, incomingMessage, CHAT_MESSAGE);
    }

    private void broadCastMessage(WebSocketSession originator, String message, AppConstants.MessageType msgType)
    {
        broadCastMessage(originator, message, msgType, new ArrayList<>());
    }

    private void broadCastMessage(WebSocketSession originator, String message, AppConstants.MessageType msgType, List<String> userList)
    {
        List<WebSocketSession> sessionList = this.SocketSessionManager.getSessionList();

        for(WebSocketSession socketSession: sessionList)
        {
            String userName = getUserName(originator);
            try {

                String jsonMessage = new JsonBuilder()
                        .withMessageType(msgType)
                        .withUserName(userName)
                        .withMessage(message)
                        .withAttribute("CurrentUsers", userList)
                        .build();

                socketSession.sendMessage(new TextMessage(jsonMessage));
            }catch (Exception e)
            {
                System.out.println("Caught Exception: " + e.toString());
            }
        }
    }

    private void sendMessageToClient(WebSocketSession session, String message, AppConstants.MessageType msgType) throws IOException {

        try {
            String userName = getUserName(session);
            String jsonMessage = new JsonBuilder()
                    .withMessageType(msgType)
                    .withUserName(userName)
                    .withMessage(message)
                    .build();

            session.sendMessage(new TextMessage(jsonMessage));
        }
        catch (Exception e)
        {
            logger.info(e.toString());
        }
    }

    private String getUserName(WebSocketSession session)
    {
        return session.getAttributes().get("userName").toString();
    }
}
