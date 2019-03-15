package cat.tecnocampus.messaging;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(NotesSenderChannels.class)
public class MessageSender {
    private MessageChannel notesChannel;

    public MessageSender(NotesSenderChannels channels) {
        this.notesChannel = channels.senderNotesChannel();
    }

    public void sendInformation(String message) {
        notesChannel.send(MessageBuilder.withPayload(message).build());

    }
}
