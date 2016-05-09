package de.maibornwolff.microservices.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class RabbitMQAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQAdapter.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendDeviceEvent(DeviceEvent deviceEvent) {
        LOGGER.info("Sending DeviceEvent: " + deviceEvent);
        /*
        HANDSON - 3. Runde
        - Empfangenen DeviceEvent an RabbitMQ delegieren (via rabbitTemplate.convertAndSend(...))
         */
        //
        rabbitTemplate.convertAndSend(deviceEvent);
        //
    }
}
