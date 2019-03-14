package cat.tecnocampus.messaging;

import cat.tecnocampus.useCases.NotesUseCases;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(ReceiverDeleteNote.class)
public class NotesMessageReceiver {
    private final NotesUseCases notesUseCases;

    public NotesMessageReceiver(NotesUseCases notesUseCases) {
        this.notesUseCases = notesUseCases;
    }

    @StreamListener(ReceiverDeleteNote.DELETE_NOTE_CHANNEL)
    public void deleteNoteReceiver(String payload) {
        notesUseCases.deleteUserNotes(payload);
    }
}



