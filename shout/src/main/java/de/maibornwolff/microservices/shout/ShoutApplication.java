package de.maibornwolff.microservices.shout;

import de.maibornwolff.microservices.shout.event.RoomEvent;
import de.maibornwolff.microservices.shout.event.RoomEventType;
import de.maibornwolff.microservices.shout.exception.NotFoundException;
import de.maibornwolff.microservices.shout.model.Account;
import de.maibornwolff.microservices.shout.model.Room;
import de.maibornwolff.microservices.shout.service.ShoutService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableSwagger2
public class ShoutApplication {


    private static final String ROOM_EVENT_QUEUE_NAME = "shout.queue.room.event";
    private static final String ROOM_EVENT_ROUTING_KEY = "room.event";
    private static final String ROOM_EVENT_EXCHANGE_NAME = "room";

    @Bean
    Queue queue() {
        return new Queue(ROOM_EVENT_QUEUE_NAME, false);
    }


    @Bean
    DirectExchange exchange() {
        return new DirectExchange(ROOM_EVENT_EXCHANGE_NAME);
    }


    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROOM_EVENT_ROUTING_KEY);
    }


    @Bean
    public ClassMapper typeMapper() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        Map<String, Class<?>> idTypeMapping = new HashMap<>();
        idTypeMapping.put("de.maibornwolff.microservices.room.event.RoomEvent", RoomEvent.class);
        idTypeMapping.put("de.maibornwolff.microservices.shout.event.RoomEvent", RoomEvent.class);
        idTypeMapping.put("de.maibornwolff.microservices.room.event.RoomEventType", RoomEventType.class);
        idTypeMapping.put("de.maibornwolff.microservices.shout.event.RoomEventType", RoomEventType.class);
        idTypeMapping.put("de.maibornwolff.microservices.room.model.Room", Room.class);
        idTypeMapping.put("de.maibornwolff.microservices.shout.model.Room", Room.class);
        idTypeMapping.put("de.maibornwolff.microservices.account.model.Account", Account.class);
        idTypeMapping.put("de.maibornwolff.microservices.shout.model.Account", Account.class);
        typeMapper.setIdClassMapping(idTypeMapping);
        return typeMapper;
    }


    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper typeMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(typeMapper); //TODO: Könnte ich hier jetzt auch einfach typeMapper() machen?
        return converter;
    }


    @Bean
    MessageListenerAdapter listenerAdapter(ShoutService controller, MessageConverter converter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(controller, "onRoomEvent");
        adapter.setMessageConverter(converter);
        return adapter;
    }


    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ROOM_EVENT_QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        container.setAcknowledgeMode(AcknowledgeMode.NONE);
        return container;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ShoutApplication.class, args);
    }
}