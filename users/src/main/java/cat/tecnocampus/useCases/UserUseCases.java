package cat.tecnocampus.useCases;

import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.messaging.MessageSender;
import cat.tecnocampus.persistence.UserLabDAO;
import cat.tecnocampus.userClient.UserClient;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userUseCases")
public class UserUseCases {

    private final UserLabDAO userLabDAO;
    private final MessageSender messageSender;
    private UserClient userClient;

    public UserUseCases(UserLabDAO UserLabDAO, MessageSender messageSender, UserClient userClient) {
        this.userLabDAO = UserLabDAO;
        this.messageSender = messageSender;
        this.userClient = userClient;
    }

    public UserLab createUser(String username, String name, String secondName, String email) {
        UserLab userLab = new UserLab.UserLabBuilder(username, email).name(name).secondName(secondName).build();
        registerUser(userLab);
        return userLab;
    }

    public int registerUser(UserLab userLab) {
        //TODO: escriu un missatge síncron al Logger. "Creat user: userName". Si communicació tallada s'ha d'escriure un missatge a pantalla local
        int logger_result = userClient.comunicateCreateUser();
        if(logger_result == -1)
            System.out.println("Error comunicating the creation to the logger");

        return userLabDAO.insert(userLab);
    }

    public int deleteUser(String username) {
        //TODO: escriu un missatge síncron al Logger. "Esborrat user: userName". Si communicació tallada s'ha d'escriure un missatge a pantalla local
        int result = userLabDAO.delete(username);
        if (result > 0) messageSender.sendDeleteNotes(username);

        int logger_result = userClient.comunicateDeleteUser(username);
        if(logger_result == -2)
            System.out.println("Error comunicating the remove to the logger");

        return result;
    }


    public void errorRegister(UserLab userLab){
        System.out.println("Couldn't be registered: "+userLab);
    }



    //Note that users don't have their notes with them
    public List<UserLab> getUsers() {
        return userLabDAO.findAll();
    }

    public UserLab getUser(String userName) {
        return userLabDAO.findByUsername(userName);
    }

    public boolean existUser(String username) {
        return userLabDAO.existsUser(username);
    }

}
