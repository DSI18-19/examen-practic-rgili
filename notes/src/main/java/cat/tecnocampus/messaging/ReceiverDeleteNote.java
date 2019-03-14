package cat.tecnocampus.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReceiverDeleteNote {
    String DELETE_NOTE_CHANNEL = "receiverNotesChannel";

    @Input(DELETE_NOTE_CHANNEL)
    SubscribableChannel receiverNotesChannel();

}
