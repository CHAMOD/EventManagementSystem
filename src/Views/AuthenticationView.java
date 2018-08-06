package Views;

import Services.AuthenticationServices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class AuthenticationView {

    public void Run()  {

        while (AuthenticationServices.getInstance().getLoggedUser() == null) {
            System.out.print("\n\n");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

           boolean isAuthenticated= AuthenticationServices.getInstance().AuthenticateUser(username, password);

           if(!isAuthenticated){
               System.out.print("Username or Password is wrong! ");

           }
        }

    }
}
