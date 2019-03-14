package cat.tecnocampus.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SendDeleteNote {
    String DELETE_NOTES_CHANNEL = "deleteNotesChannel";

    @Output(DELETE_NOTES_CHANNEL)
    MessageChannel deleteNotesChannel();
}


