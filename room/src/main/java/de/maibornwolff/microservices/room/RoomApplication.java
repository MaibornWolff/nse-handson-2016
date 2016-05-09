package de.maibornwolff.microservices.room;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import de.maibornwolff.microservices.room.event.DeviceEvent;
import de.maibornwolff.microservices.room.service.RoomService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RoomApplication {

    private static final String DEVICE_EVENT_QUEUE_NAME = "room.queue.device.event";
    private static final String DEVICE_EVENT_ROUTING_KEY = "device.event";
    private static final String DEVICE_EVENT_EXCHANGE_NAME = "device";

    public static final String ROOM_EVENT_EXCHANGE_NAME = "room";
    public static final String ROOM_EVENT_ROUTING_KEY = "room.event";


    @Bean
    Queue queue() {
        return new Queue(DEVICE_EVENT_QUEUE_NAME, false);
    }


    @Bean
    DirectExchange exchange() {
        return new DirectExchange(DEVICE_EVENT_EXCHANGE_NAME);
    }


    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEVICE_EVENT_ROUTING_KEY);
    }


    @Bean
    public ClassMapper typeMapper() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        Map<String, Class<?>> idTypeMapping = new HashMap<>();
        idTypeMapping.put("de.maibornwolff.microservices.device.DeviceEvent", DeviceEvent.class);
        idTypeMapping.put("de.maibornwolff.microservices.room.event.DeviceEvent", DeviceEvent.class);
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
    MessageListenerAdapter listenerAdapter(RoomService controller, MessageConverter converter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(controller, "onDeviceEvent");
        adapter.setMessageConverter(converter);
        return adapter;
    }


    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(DEVICE_EVENT_QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareExchange(new DirectExchange(ROOM_EVENT_EXCHANGE_NAME));
        return rabbitAdmin;
    }



    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        template.setExchange(ROOM_EVENT_EXCHANGE_NAME);
        template.setRoutingKey(ROOM_EVENT_ROUTING_KEY);
        return template;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(RoomApplication.class, args);
    }
}