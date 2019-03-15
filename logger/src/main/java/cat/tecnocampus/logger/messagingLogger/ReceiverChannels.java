package cat.tecnocampus.logger.messagingLogger;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReceiverChannels {
    String LOGGER_CHANNEL = "receiverLoggerChannel";

    @Input(LOGGER_CHANNEL)
    SubscribableChannel receiverLoggerChannel();

}
