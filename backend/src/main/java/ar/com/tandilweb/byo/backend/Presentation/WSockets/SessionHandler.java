package ar.com.tandilweb.byo.backend.Presentation.WSockets;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class SessionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionHandler.class);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<String, WebSocketSession>();

    public SessionHandler() {
        scheduler.scheduleAtFixedRate(new Runnable() {
        	public void run() {
                sessionMap.keySet().forEach(new Consumer<String>() {
					public void accept(String k) {
					    try {
					        sessionMap.get(k).close();
					        sessionMap.remove(k);
					    } catch (IOException e) {
					        LOGGER.error("Error while closing websocket session: {}", e);
					    }
					}
				});
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    public void register(WebSocketSession session) {
        sessionMap.put(session.getId(), session);
    }

}