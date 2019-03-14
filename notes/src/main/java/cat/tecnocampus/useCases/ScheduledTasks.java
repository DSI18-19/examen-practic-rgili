package cat.tecnocampus.useCases;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.userClient.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class ScheduledTasks {

    private final NotesUseCases notesUseCases;
    private  final UserClient userClient;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public ScheduledTasks(NotesUseCases notesUseCases, UserClient userClient) {
        this.notesUseCases = notesUseCases;
        this.userClient = userClient;
    }

    @Scheduled(fixedRate = 5000)
    public void checkUsersExist() {

        List<NoteLab> noteLabList = this.notesUseCases.getAllnotCheckedOwner();
        System.out.println("Notes user down: " + noteLabList.size());
        noteLabList.forEach(noteLab -> {
            int exitUser = this.userClient.userExists(noteLab.getOwner());

            switch (exitUser){
                case(UserClient.USER_EXISTS):
                    this.notesUseCases.setOwnerExists(noteLab.getId());
                    break;
                case(UserClient.USER_DOES_NOT_EXIST):
                    this.notesUseCases.deleteUserNote(noteLab.getId(), noteLab.getOwner());
                    break;
                case(UserClient.CLIENT_DOWN):
                    return;
                default:
                    return;
            }
        });

    }
}
