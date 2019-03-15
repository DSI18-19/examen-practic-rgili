package cat.tecnocampus.messaging;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(NotesSenderChannels.class)
public class MessageSender {
    private MessageChannel loggerChannel;

    public MessageSender(NotesSenderChannels channels) {
        this.loggerChannel = channels.senderLoggerChannel();
    }

    public void sendInformation(String message) {
        loggerChannel.send(MessageBuilder.withPayload(message).build());

    }
}
