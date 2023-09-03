package org.chat.application.configurator;

import org.chat.application.SessionsManager;
import org.chat.application.SocketTextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
/**
 * @author Saransh Gupta
 */
@Configuration
@EnableWebSocket
public class WebSocketConfigurator implements WebSocketConfigurer {

    @Autowired
    SessionsManager SocketSessionManager;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketTextHandler(this.SocketSessionManager), "/register/*").
                setAllowedOriginPatterns("*").addInterceptors(userNameInterceptor());
    }

    @Bean
    public HandshakeInterceptor userNameInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

                // Get the URI segment corresponding to the userName during handshake
                String path = request.getURI().getPath();
                String userName = path.substring(path.lastIndexOf('/') + 1);

                // This will be added to the websocket session
                attributes.put("userName", userName);
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Exception exception) {
                // Nothing to do after handshake
            }
        };
    }
}
