import Services.AuthenticationServices;
import Views.*;
import com.event.DaoImpl.PersonImpl;
import com.event.model.User;

import java.util.Scanner;

public class MainProgram {


    public static void main(String[] args) throws Exception {

        AuthenticationView authView = new AuthenticationView();
        authView.Run();

        if (AuthenticationServices.getInstance().getLoggedUser() != null) {


            if (AuthenticationServices.getInstance().getLoggedUser().getType().equals("admin")) {
                isAdminMenu();
            } else if (AuthenticationServices.getInstance().getLoggedUser().getType().equals("user") && isLead(AuthenticationServices.getInstance().getLoggedUser())) {

                leadMenu();

            } else if (isMember(AuthenticationServices.getInstance().getLoggedUser())) {

                UserTaskManagementView taskView = new UserTaskManagementView();
                taskView.Run();


            }

            System.out.print("\n\n");
            System.out.println("Good bye");
        }
    }


    static void isAdminMenu() throws Exception {


        while (true) {
            System.out.print("Choose [U]sers T[e]am [M]ember or [T]asks : ");
            Scanner scanner = new Scanner(System.in);
            String choise = scanner.nextLine();

            switch (choise.toUpperCase()) {
                case "U": {
                    UserManagementView userView = new UserManagementView();
                    boolean status = userView.Run();
                    if (status) {
                        continue;
                    } else {
                        System.exit(0);
                    }
                    break;

                }
                case "T": {
                    TaskManagementView taskView = new TaskManagementView();
                    boolean status = taskView.Run("admin");
                    if (status) {
                        continue;
                    } else {
                        System.exit(0);
                    }
                    break;
                }
                case "X": {

                    System.exit(0);
                    break;
                }

                case "E": {
                    TeamManagementView teamView = new TeamManagementView();
                    boolean status = teamView.Run("admin");

                    if (status) {
                        continue;
                    } else {
                        System.exit(0);
                    }

                    break;
                }

                case "M": {
                    MemberManagementView memberView = new MemberManagementView();
                    boolean status = memberView.Run();

                    if (status) {
                        continue;
                    } else {
                        System.exit(0);
                    }

                    break;
                }

                default: {
                    continue;
                }


            }
            scanner.close();

        }


    }


    private static void leadMenu() throws Exception {

        while (true) {
            System.out.print("Choose T[e]am or [T]asks : ");
            Scanner scanner = new Scanner(System.in);
            String choise = scanner.nextLine();

            switch (choise.toUpperCase()) {

                case "T": {
                    TaskManagementView taskView = new TaskManagementView();
                    boolean status = taskView.Run("lead");

                    if (status) {
                        continue;
                    } else {
                        System.exit(0);
                    }
                    break;
                }
                case "X": {

                    System.exit(0);
                    break;
                }

                case "E": {
                    TeamManagementView teamView = new TeamManagementView();
                    boolean status = teamView.Run("lead");

                    if (status) {
                        continue;
                    } else {
                        System.exit(0);
                    }

                    break;
                }

                default: {
                    continue;
                }


            }
            scanner.close();

        }


    }


    private static boolean isLead(User loggedUser) {

        PersonImpl personDAO = new PersonImpl();

        return personDAO.isLead(loggedUser.getIdUser());

    }


    static boolean isMember(User user) {

        PersonImpl personDAO = new PersonImpl();

        return personDAO.isMember(user.getIdUser());

    }

}
