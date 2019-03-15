package cat.tecnocampus.RESTcontrollers;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.messaging.MessageSender;
import cat.tecnocampus.useCases.NotesUseCases;
import com.netflix.discovery.converters.Auto;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class NoteRESTController {

    private NotesUseCases notesUseCases;

    public NoteRESTController(NotesUseCases notesUseCases) {
        this.notesUseCases = notesUseCases;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteLab> getNotes() {

        List<NoteLab> noteLabs = notesUseCases.getAllNotes();

        return noteLabs;
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteLab> getUserNotes(@PathVariable String username) {
        return notesUseCases.getUserNotes(username);
    }

    @GetMapping(value = "/{username}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteLab getUserNote(@PathVariable String username, @PathVariable Long id) {
        return notesUseCases.findById(id);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteLab createNote(@RequestBody @Valid NoteLab note) {

        NoteLab newNote = this.notesUseCases.createUserNote(note.getOwner(), note);

        return newNote;
    }

    @DeleteMapping(value = "/{username}")
    public void deleteNote(@PathVariable String username) {
        notesUseCases.deleteUserNotes(username);
    }

    @DeleteMapping(value = "/{username}/{id}")
    public void deleteNoteId(@PathVariable String username, @PathVariable Long id) {
        notesUseCases.deleteUserNote(id, username);
    }

}
