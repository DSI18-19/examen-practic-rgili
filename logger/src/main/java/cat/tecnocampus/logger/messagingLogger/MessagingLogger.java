package cat.tecnocampus.logger.messagingLogger;

import cat.tecnocampus.logger.LoggerApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ReceiverChannels.class)
public class MessagingLogger {
    //TODO: aquest servei ha d'escriure a pantalla els missatges asíncrons (a través de RabbitMQ) enviats pel servei de notes

    private Logger log = LogManager.getLogger(LoggerApplication.class);

    @StreamListener(ReceiverChannels.LOGGER_CHANNEL)
    public void notesReceiver(Object payload) {
        log.info(payload);
    }

}
