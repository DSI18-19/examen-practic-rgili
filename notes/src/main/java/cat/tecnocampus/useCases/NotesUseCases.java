package cat.tecnocampus.useCases;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.messaging.MessageSender;
import cat.tecnocampus.persistence.NoteLabDAO;
import cat.tecnocampus.userClient.UserClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("NotesUseCases")
public class NotesUseCases {

    private final NoteLabDAO noteLabDAO;
    private final UserClient userClient;
    private MessageSender messageSender;

    public NotesUseCases(NoteLabDAO noteLabDAO, UserClient userClient, MessageSender messageSender) {
        this.noteLabDAO = noteLabDAO;
        this.userClient = userClient;
        this.messageSender = messageSender;
    }

    public NoteLab createUserNote(String username, NoteLab noteLab) {
        noteLab.setDateCreation(LocalDateTime.now());
        noteLab.setDateEdit(LocalDateTime.now());

        //TODO: escriu un missatge assíncron al Logger. "Creat: noteTitle"
        int existsUser = userClient.userExists(username);

        if (existsUser == UserClient.USER_EXISTS || existsUser == UserClient.CLIENT_DOWN){
            messageSender.sendInformation("Creat: "+noteLab.getTitle().toString());
            return noteLabDAO.insert(noteLab, username, existsUser);
        }
        else return null;
    }

    public List<NoteLab> getAllnotCheckedOwner(){return noteLabDAO.findAllnotCheckedOwner();}


    public int deleteUserNote(Long id, String username) {
        //TODO: escriu un missatge assíncron al Logger. "esborrada nota: id"
        messageSender.sendInformation("esborrada nota: "+id);
        return noteLabDAO.deleteNote(id, username);
    }

    public int deleteUserNotes(String username) {
        //TODO: escriu un missatge assíncron al Logger. "esborrades les notes de: username"
        if(username != null){}
            messageSender.sendInformation("esborrades les notes de: "+username);
        return noteLabDAO.deleteUserNotes(username);
    }

    public List<NoteLab> getUserNotes(String userName) {
        return noteLabDAO.findByUsername(userName);
    }

    public List<NoteLab> getAllNotes() {
        return noteLabDAO.findAll();
    }

    public NoteLab findById(Long id) {
        return this.noteLabDAO.findById(id);
    }

    public void setOwnerExists(long id){ noteLabDAO.setOwnerExists(id);}


}
