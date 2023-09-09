package org.chat.application;

import org.chat.application.dao.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

/**
 * @author Saransh Gupta
 */
@Component
public class SessionsManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionsManager.class);
    Set<WebSocketSession> sessionSet;
    Map<String, WebSocketSession> userMap;

    public SessionsManager()
    {
        sessionSet = new HashSet<>();
        userMap = new HashMap<>();
    }

    public void addSession(WebSocketSession session) throws Exception
    {
        String id = session.getId();
        if(sessionSet.contains(session))
        {
            throw new Exception("Session" + id + "already exists!!");
        }

        if(userMap.containsKey(session.getAttributes().get("userName").toString()))
        {
            throw new Exception("User Already Exists");
        }


        sessionSet.add(session);
        userMap.put(session.getAttributes().get("userName").toString(), session);
        logger.info("Added Session Info in Map. SessionId: " + id);
    }

    public void removeSession(WebSocketSession session) throws Exception
    {
        if(!sessionSet.contains(session))
        {
            throw new Exception("Session does not Exist!");
        }

        if(!userMap.containsKey(session.getAttributes().get("userName")))
        {
            throw new Exception("User doesn't exist!");
        }

        sessionSet.remove(session);
        userMap.remove(session.getAttributes().get("userName").toString());
        logger.info("Removed Session Info in Map. SessionId: " + session.getId());
    }

    public List<WebSocketSession> getSessionList(){
        return new ArrayList<>(sessionSet);
    }

    public List<String> getUserList(){
        return new ArrayList<>(userMap.keySet());
    }
}
