import com.chat.application.mocks.MockWebSocketSession;
import org.chat.application.SessionsManager;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author Saransh Gupta
 */


public class SessionsManagerTest {

    @Test
    public void addSessionsTest() throws Exception {
        SessionsManager sessionsManager = new SessionsManager();

        sessionsManager.addSession(new MockWebSocketSession("123"));
        assertEquals(sessionsManager.getSessionList().size(), 1);

        sessionsManager.addSession(new MockWebSocketSession("1234"));
        sessionsManager.addSession(new MockWebSocketSession("12345"));
        sessionsManager.addSession(new MockWebSocketSession("123456"));
        sessionsManager.addSession(new MockWebSocketSession("1234567"));
        assertEquals(sessionsManager.getSessionList().size(), 5);
    }

    @Test(expected = Exception.class)
    public void addDuplicateSessionsTest() throws Exception {
        SessionsManager sessionsManager = new SessionsManager();

        MockWebSocketSession mockWebSocketSession = new MockWebSocketSession("123");

        sessionsManager.addSession(mockWebSocketSession);
        assertEquals(sessionsManager.getSessionList().size(), 1);

        sessionsManager.addSession(mockWebSocketSession);
    }

    @Test
    public void SessionsTest() throws Exception {
        SessionsManager sessionsManager = new SessionsManager();

        MockWebSocketSession mockWebSocketSession = new MockWebSocketSession("123");
        sessionsManager.addSession(mockWebSocketSession);
        assertEquals(sessionsManager.getSessionList().size(), 1);

        sessionsManager.removeSession(mockWebSocketSession);
        assertEquals(sessionsManager.getSessionList().size(), 0);
    }

    @Test(expected = Exception.class)
    public void removeNonExistentSessionsTest() throws Exception {
        SessionsManager sessionsManager = new SessionsManager();
        MockWebSocketSession mockWebSocketSession = new MockWebSocketSession("123");
        sessionsManager.removeSession(mockWebSocketSession);
    }



}
