package cat.tecnocampus.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface NotesSenderChannels {

    String LOGGER_CHANNEL = "senderLoggerChannel";

    @Output(LOGGER_CHANNEL)
    MessageChannel senderLoggerChannel();
}
