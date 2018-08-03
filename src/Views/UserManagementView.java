package Views;

import com.event.DaoImpl.PersonImpl;
import com.event.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class UserManagementView {

    PersonImpl personDAO;

    public UserManagementView() {
        personDAO = new PersonImpl();

    }


    public boolean Run() throws IOException {

        while (true) {
            EnumMenu choice = RenderMenu();

            switch (choice) {

                case List: {
                    List();
                    break;
                }
                case Add: {
                    Add();
                    break;
                }
                case Edit: {
                    Edit();
                    break;
                }
                case Delete: {
                    Delete();
                    break;
                }
                case View: {
                    View();
                    break;
                }
                case Back: {
                    return true;
                }

                case Exit: {
                    return false;
                }
            }
        }

    }

    private EnumMenu RenderMenu() throws IOException {

        while (true) {
            System.out.print("\n\n\n");

            System.out.println("[L]ist Users");
            System.out.println("[A]dd user");
            System.out.println("[E]dit User");
            System.out.println("[D]elete User");
            System.out.println("[V]iew User");
            System.out.println("[B]ack");
            System.out.println("E[x]it");


            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();

            switch (choice.toUpperCase()) {
                case "L": {
                    return EnumMenu.List;
                }
                case "A": {
                    return EnumMenu.Add;
                }
                case "E": {
                    return EnumMenu.Edit;
                }
                case "D": {
                    return EnumMenu.Delete;
                }
                case "V": {
                    return EnumMenu.View;
                }
                case "B": {
                    return EnumMenu.Back;
                }
                case "X": {
                    return EnumMenu.Exit;
                }
                default: {
                    continue;
                }
            }
        }

    }

    public void Add() {

        System.out.print("\n\n\n");
        System.out.println("####Add User####");

        Scanner scanner = new Scanner(System.in);

        User user = new User();

        System.out.print("name:");
        user.setName(scanner.nextLine());

        System.out.print("user name:");
        user.setUserName(scanner.nextLine());

        System.out.print("Password:");
        user.setPassword(scanner.nextLine());


        user.setType("user");

//		System.out.print("Firstname:");
//		item.setFirstName(scanner.nextLine());
//
//		System.out.print("Lastname:");
//		item.setLastName(scanner.nextLine());

//		UsersRepository UserRepo = new UsersRepository("users.txt");
//		UserRepo.Add(item);

        personDAO.insertUser(user);


        System.out.print("\n\n\n");
        System.out.println("User is added successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();


    }

    public void Delete() {

        System.out.print("\n\n\n");
        System.out.println("####Delete User####");

        ArrayList<User> users = personDAO.GetAllUsers();

        for (int i = 0; i < users.size(); i++) {

            User user = users.get(i);

            System.out.print(user.getUserName() + " ( " + user.getIdUser() + " )\t");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of user: ");
        int id = Integer.parseInt(scanner.nextLine());

        User user1 = personDAO.GetByUserId(id);

        personDAO.DeleteUser(user1.getIdUser());

        System.out.print("\n\n\n");
        System.out.println("Item deleted successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();
    }

    public void Edit() {

        System.out.print("\n\n\n");
        System.out.println("####Edit User####");

        ArrayList<User> users = personDAO.GetAllUsers();

        for (int i = 0; i < users.size(); i++) {

            User user = users.get(i);

            System.out.print(user.getUserName() + " ( " + user.getIdUser() + " )\t");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of user: ");
        int id = Integer.parseInt(scanner.nextLine());


        User user1 = personDAO.GetByUserId(id);


        System.out.print("Name (" + user1.getName() + "): ");
        user1.setName(scanner.nextLine());

        System.out.print("User Name ( " + user1.getUserName() + " ): ");
        user1.setUserName(scanner.nextLine());

        System.out.print("User Type: (" + user1.getType() + " ): ");
        user1.setType(scanner.nextLine());

        System.out.print("Do you want to Change password. [Y]/[N]:");
        String choice = scanner.nextLine();

        if (choice.toUpperCase().equals("Y")) {
            System.out.print("Enter new password:");
            user1.setPassword(scanner.nextLine());

        }


        personDAO.EditUser(user1);

        System.out.print("\n\n\n");
        System.out.println("Item updated successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();
    }

    public void List() {

        System.out.print("\n\n\n");
        System.out.println("####List Users####");

//		UsersRepository userRepo = new UsersRepository("userOlds.txt");
//		ArrayList<UserOld> userOlds = userRepo.GetAll();

        ArrayList<User> users = personDAO.GetAllUsers();

        for (int i = 0; i < users.size(); i++) {

            User userOld = users.get(i);
            System.out.print("User ID:");
            System.out.println(userOld.getIdUser());
            System.out.print("Name:");
            System.out.println(userOld.getName());
            System.out.print("User Name:");
            System.out.println(userOld.getUserName());
            System.out.print("User Type:");
            System.out.println(userOld.getType());
            System.out.println("---------------------------------");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Press [Enter] to continue");
        scanner.nextLine();
    }


    public void View() {

        System.out.print("\n\n\n");
        System.out.println("####View User####");

        ArrayList<User> users = personDAO.GetAllUsers();

        for (int i = 0; i < users.size(); i++) {

            User user = users.get(i);

            System.out.print(user.getUserName() + " ( " + user.getIdUser() + " )\t");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of user: ");
        int id = Integer.parseInt(scanner.nextLine());

        User user1 = personDAO.GetByUserId(id);


        System.out.print("\n\n\n");
        System.out.println("User ID:" + user1.getIdUser());
        System.out.println("Name:" + user1.getName());
        System.out.println("User Name:" + user1.getUserName());
        System.out.println("User Type:" + user1.getType());

        System.out.println("Press [Enter] to continue");
        scanner.nextLine();
    }


    public void Exit() {
        System.out.println("back");
    }


}
