package bigdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan("bigdata.web")
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		registry.addEndpoint("/websocket").withSockJS();

	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// TODO Auto-generated method stub
		super.configureMessageBroker(registry);
		
		registry.setApplicationDestinationPrefixes("/message");
		
		registry.enableSimpleBroker("/topic","/queue");
	}
	
	//MessageChannel
	/*
	@Bean(name="myMessageChannel")
	public  MessageChannel messageChannel(){
		//ExecutorSubscribableChannel channel=new  ExecutorSubscribableChannel(executor)
		ExecutorSubscribableChannel channel=new ExecutorSubscribableChannel();
		return channel;
	}
	
	@Bean(name="dude")
	public SimpMessagingTemplate simpleMessageTempalte(){

		return new SimpMessagingTemplate(messageChannel());
		
	}*/

}
