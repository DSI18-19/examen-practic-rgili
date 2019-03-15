package cat.tecnocampus.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface NotesSenderChannels {

    String NOTES_CHANNEL = "senderNotesChannel";

    @Output(NOTES_CHANNEL)
    MessageChannel senderNotesChannel();
}
