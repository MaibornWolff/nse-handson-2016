package de.maibornwolff.microservices.dashboard;

import de.maibornwolff.microservices.dashboard.event.RoomEvent;
import de.maibornwolff.microservices.dashboard.event.RoomEventType;
import de.maibornwolff.microservices.dashboard.service.DashboardService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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

/**
 * @author Bartosz Boron, MaibornWolff GmbH
 */
@SpringBootApplication
@EnableSwagger2
public class DashboardApplication {

    public static final String ROOM_EVENT_QUEUE_NAME = "dashboard.queue.room.event";
    public static final String ROOM_EVENT_ROUTING_KEY = "room.event";
    private static final String ROOM_EVENT_EXCHANGE_NAME = "room";

    @Bean
    Queue queue() {
        return new Queue(ROOM_EVENT_QUEUE_NAME, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange((ROOM_EVENT_EXCHANGE_NAME));
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
        idTypeMapping.put("de.maibornwolff.microservices.dashboard.event.RoomEvent", RoomEvent.class);
        idTypeMapping.put("de.maibornwolff.microservices.room.event.RoomEventType", RoomEventType.class);
        idTypeMapping.put("de.maibornwolff.microservices.dashboard.event.RoomEventType", RoomEventType.class);
        typeMapper.setIdClassMapping(idTypeMapping);
        return typeMapper;
    }

    @Bean
    MessageConverter jsonMessageConverter(ClassMapper typeMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(typeMapper);
        return converter;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(DashboardService controller, MessageConverter converter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(controller, "onRoomEvent");
        adapter.setMessageConverter(converter);
        return adapter;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(ROOM_EVENT_QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DashboardApplication.class, args);
    }
}
