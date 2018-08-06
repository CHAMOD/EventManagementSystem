package Services;

import com.event.DaoImpl.PersonImpl;
import com.event.model.User;


public class AuthenticationServices {

    private static AuthenticationServices instance = null;
    PersonImpl personDAO;

    private AuthenticationServices() {
        personDAO = new PersonImpl();
    }

    public static AuthenticationServices getInstance() {

        if (AuthenticationServices.instance == null)
            AuthenticationServices.instance = new AuthenticationServices();


        return AuthenticationServices.instance;
    }

    private User authenticatedUser = null;

    public User getLoggedUser() {
        return authenticatedUser;
    }

    public boolean AuthenticateUser(String username, String password) {

        this.authenticatedUser = personDAO.GetByUsernameAndPassword(username, password);

        if(this.authenticatedUser!=null)
            return true;
        else
            return false;
    }
}
