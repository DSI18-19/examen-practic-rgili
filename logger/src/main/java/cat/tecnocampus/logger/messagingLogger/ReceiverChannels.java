package cat.tecnocampus.logger.messagingLogger;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReceiverChannels {
    String NOTES_CHANNEL = "receiverNotesChannel";

    @Input(NOTES_CHANNEL)
    SubscribableChannel receiverNotesChannel();

}
