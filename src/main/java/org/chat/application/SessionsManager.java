package org.chat.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Saransh Gupta
 */
@Component
public class SessionsManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionsManager.class);
    Map<WebSocketSession, String> sessionMap = new HashMap<>();

    public void addSession(WebSocketSession session, String id) throws Exception
    {
        if(sessionMap.containsKey(session))
        {
            throw new Exception("Session" + id + "already exists!!");
        }
        sessionMap.put(session, id);
        logger.info("Added Session Info in Map. SessionId: " + id);
    }

    public void removeSession(WebSocketSession session) throws Exception
    {
        if(!sessionMap.containsKey(session))
        {
            throw new Exception("Session does not Exist!");
        }

        sessionMap.remove(session);
        logger.info("Removed Session Info in Map. SessionId: " + session.getId());
    }

    public List<WebSocketSession> getSessionList(){
        List<WebSocketSession> sessionList = new ArrayList<>(sessionMap.keySet());
        return sessionList;
    }
}
