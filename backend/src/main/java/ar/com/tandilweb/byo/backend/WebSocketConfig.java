package ar.com.tandilweb.byo.backend;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurationSupport;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import ar.com.tandilweb.byo.backend.Presentation.WSockets.UserService;
import ar.com.tandilweb.byo.backend.Presentation.WSockets.WebSocketMonitor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends  WebSocketMessageBrokerConfigurationSupport implements WebSocketMessageBrokerConfigurer {
	
	public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/chatService"); // prefijos de salida
        config.setApplicationDestinationPrefixes("/api"); // prefijos de entrada (api)
        config.setUserDestinationPrefix("/tome"); // prefijo para single user (tome)
    }
	
	public void registerStompEndpoints(StompEndpointRegistry registry) {
//        RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
//        registry.addEndpoint("/internal").withSockJS(); //endpoints internos
//        registry.addEndpoint("/internal")
//                .setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy));

        registry.addEndpoint("/external").setAllowedOrigins("http://localhost:8100", "*").withSockJS(); //endpoints externos
//        registry.addEndpoint("/external")
//        .setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy))
//        .setAllowedOrigins("*");
    }
	
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
	        return super.configureMessageConverters(messageConverters);
	}

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }
    
    public void configureClientInboundChannel(ChannelRegistration registration) {
        super.configureClientInboundChannel(registration);
    }

    public void configureClientOutboundChannel(ChannelRegistration registration) {
        super.configureClientOutboundChannel(registration);
	}

    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        super.configureWebSocketTransport(registry);
	}
    
    public WebSocketHandler subProtocolWebSocketHandler() {
	        return new WebSocketMonitor(clientInboundChannel(), clientOutboundChannel());
	}
    
    @Bean 
    public UserService userService() {
    	return new UserService();
    }

}
