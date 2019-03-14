package cat.tecnocampus.messaging;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(SendDeleteNote.class)
public class MessageSender {
    private MessageChannel greetingsChannel;

    public MessageSender(SendDeleteNote channels) {
        this.greetingsChannel = channels.deleteNotesChannel();
    }

    public void sendDeleteNotes(String username) {
        greetingsChannel.send(MessageBuilder.withPayload(username).build());

    }
}
