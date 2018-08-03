package Views;

import Services.AuthenticationServices;
import com.event.DaoImpl.MemberImpl;
import com.event.DaoImpl.PersonImpl;
import com.event.DaoImpl.TeamImpl;
import com.event.model.Member;
import com.event.model.Team;
import com.event.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TeamManagementView {

    TeamImpl teamDao;
    PersonImpl personDAO;
    MemberImpl memberDao;

    public TeamManagementView() {
        teamDao = new TeamImpl();
        personDAO = new PersonImpl();
        memberDao = new MemberImpl();
    }


    public boolean Run(String role) throws IOException {


        if (role.contains("admin")) {
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
        } else if (role.contains("lead")) {

            while (true) {
                EnumMenu choice = RenderMenuLead();

                switch (choice) {

                    case List: {
                        ListTeamMembers();
                        break;
                    }
                    case Add: {
                        AddTeamMembers();
                        break;
                    }
                    case Delete: {
                        DeleteMembers();
                        break;
                    }
                    case View: {
                        ViewMemberDetails();
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


        } else {
            return false;
        }

    }

    private EnumMenu RenderMenu() throws IOException {

        while (true) {
            System.out.print("\n\n\n");

            System.out.println("[L]ist Teams");
            System.out.println("[A]dd Team");
            System.out.println("[E]dit Team");
            System.out.println("[D]elete Team");
            System.out.println("[V]iew Team");
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

    private EnumMenu RenderMenuLead() throws IOException {

        while (true) {
            System.out.print("\n\n\n");

            System.out.println("[L]ist Teams Members");
            System.out.println("[A]dd Team Members");
            System.out.println("[D]elete Team Members");

            System.out.println("[V]iew Team Member Details");
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


    /*methods for admin*/
    public void Add() {

        System.out.print("\n\n\n");
        System.out.println("####Add Team####");

        Scanner scanner = new Scanner(System.in);

        Team team = new Team();

        System.out.print("name:");
        team.setTeamName(scanner.nextLine());

        System.out.print("Select user as team lead:");

        ArrayList<User> users = personDAO.GetAllUsersForTeam();

        for (int i = 0; i < users.size(); i++) {

            User user = users.get(i);

            System.out.print(user.getUserName() + " ( " + user.getIdUser() + " )\t");
        }

        int idUser = Integer.parseInt(scanner.nextLine());
        team.setTeamLead(idUser);

        teamDao.insertTeam(team);


        System.out.print("\n\n\n");
        System.out.println("Team is added successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }

    public void Delete() {

        System.out.print("\n\n\n");
        System.out.println("####Delete Team####");

        ArrayList<Team> teams = teamDao.GetAllTeams();

        for (int i = 0; i < teams.size(); i++) {

            Team team = teams.get(i);

            System.out.print(team.getTeamName() + " ( " + team.getIdTeam() + " )\t");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of team: ");
        int id = Integer.parseInt(scanner.nextLine());

        Team team1 = teamDao.GetByTeamId(id);

        teamDao.DeleteTeam(team1.getIdTeam());

        System.out.print("\n\n\n");
        System.out.println("Item deleted successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();
    }

    public void Edit() {

        System.out.print("\n\n\n");
        System.out.println("####Edit Team####");

        ArrayList<Team> teams = teamDao.GetAllTeams();

        for (int i = 0; i < teams.size(); i++) {

            Team team = teams.get(i);

            System.out.print(team.getTeamName() + " ( " + team.getIdTeam() + " )\t");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of Team:");
        int id = Integer.parseInt(scanner.nextLine());


        Team team1 = teamDao.GetByTeamId(id);


        System.out.print("Team Name (" + team1.getTeamName() + "): ");
        team1.setTeamName(scanner.nextLine());

        System.out.print("Do you want to Change Team lead. [Y]/[N]:");
        String choice = scanner.nextLine();

        if (choice.toUpperCase().equals("Y")) {
            System.out.print("Select a user as team lead:");

            ArrayList<User> users = personDAO.GetAllUsersForTeam();

            for (int i = 0; i < users.size(); i++) {

                User user = users.get(i);

                System.out.print(user.getName() + " ( " + user.getIdUser() + " )\t");
                team1.setTeamLead(scanner.nextInt());

            }

        }

        teamDao.EditTeam(team1);

        System.out.print("\n\n\n");
        System.out.println("Item updated successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();
    }

    public void List() {

        System.out.print("\n\n\n");
        System.out.println("####List Team####");


        ArrayList<Team> teams = teamDao.GetAllTeams();

        for (int i = 0; i < teams.size(); i++) {

            Team team = teams.get(i);
            System.out.print("Team ID:");
            System.out.println(team.getIdTeam());
            System.out.print("Team Name:");
            System.out.println(team.getTeamName());
            System.out.print("Team Lead:");
            System.out.println(team.getTeamLeadName());
            System.out.println("---------------------------------");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Press [Enter] to continue");
        scanner.nextLine();
    }


    public void View() {

        System.out.print("\n\n\n");
        System.out.println("####View Team####");

        ArrayList<Team> teams = teamDao.GetAllTeams();

        for (int i = 0; i < teams.size(); i++) {

            Team team = teams.get(i);

            System.out.print(team.getTeamName() + " ( " + team.getIdTeam() + " )\t");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of team: ");
        int id = Integer.parseInt(scanner.nextLine());

        Team team1 = teamDao.GetByTeamId(id);


        System.out.print("\n\n\n");
        System.out.println("Team ID:" + team1.getIdTeam());
        System.out.println("Team Name:" + team1.getTeamName());
        System.out.println("Lead Name:" + team1.getTeamLeadName());


        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }


    /*methods for lead*/
    /*add members to own team*/
    public void AddTeamMembers() {

        System.out.print("\n\n\n");
        System.out.println("####Add Team Members####");

        Scanner scanner = new Scanner(System.in);

        // Team []team =new Team[];


        int idLead = AuthenticationServices.getInstance().getLoggedUser().getIdUser();
        int idTeam = teamDao.getTeamIdByUser(idLead);

        System.out.println("Available Users:");

        ArrayList<User> users = memberDao.getAvailableUsers();

        for (int i = 0; i < users.size(); i++) {

            User user = users.get(i);

            System.out.println(user.getName() + " ( " + user.getIdUser() + " )\t");
        }

        if (!users.isEmpty()) {
            System.out.println("Do you want to add members?[Y]/[N]");
            String choice = scanner.nextLine();


            while (choice.toUpperCase().contains("Y")) {

                ArrayList<User> users1 = memberDao.getAvailableUsers();

                if (!users1.isEmpty()) {
                    for (int i = 0; i < users1.size(); i++) {

                        User user = users1.get(i);

                        System.out.println(user.getName() + " ( " + user.getIdUser() + " )\t");
                    }
                    System.out.print("Enter ID of user: ");
                    int idUser = Integer.parseInt(scanner.nextLine());

                    memberDao.insertMember(idTeam, idUser);


                    System.out.println("Do you want to add members?[Y]/[N]");
                    choice = scanner.nextLine();
                } else {
                    System.out.println("No members available to add");
                    break;
                }

            }

            System.out.print("\n\n\n");
            System.out.println("Members are added successfully");
        } else {

            System.out.println("No members available to add");
        }

        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }

    /*view team members by lead*/
    public void ListTeamMembers() {


        System.out.print("\n\n\n");
        System.out.println("####List Team Members####");

        int idLead = AuthenticationServices.getInstance().getLoggedUser().getIdUser();
        int idTeam = teamDao.getTeamIdByUser(idLead);

        ArrayList<Member> members = memberDao.GetAllTeamMembers(idTeam);

        for (int i = 0; i < members.size(); i++) {

            Member member = members.get(i);
            System.out.print("Member ID:");
            System.out.println(member.getIdMember());
            System.out.print("Member Name:");
            System.out.println(member.getNameMember());
            System.out.println("---------------------------------");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }

    /*view team members details by lead*/
    public void ViewMemberDetails() {

        System.out.print("\n\n\n");
        System.out.println("####View Member####");

        Scanner scanner = new Scanner(System.in);

        int idLead = AuthenticationServices.getInstance().getLoggedUser().getIdUser();
        int idTeam = teamDao.getTeamIdByUser(idLead);

        ArrayList<Member> members = memberDao.GetAllTeamMembers(idTeam);

        for (int i = 0; i < members.size(); i++) {

            Member member = members.get(i);

            System.out.println(member.getNameMember() + " ( " + member.getIdMember() + " )");
        }
        System.out.print("Enter ID of Member: ");
        int idMember = Integer.parseInt(scanner.nextLine());

        User user1 = memberDao.GetByMemberId(idMember);


        System.out.print("\n\n\n");
        System.out.println("User ID:" + user1.getIdUser());
        System.out.println("Name:" + user1.getName());
        System.out.println("User Name:" + user1.getUserName());

        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }

    public void DeleteMembers() {

        System.out.print("\n\n\n");
        System.out.println("####Delete Member####");

        Scanner scanner = new Scanner(System.in);


        int idLead = AuthenticationServices.getInstance().getLoggedUser().getIdUser();
        int idTeam = teamDao.getTeamIdByUser(idLead);

        ArrayList<Member> members = memberDao.GetAllTeamMembers(idTeam);


        if (!members.isEmpty()) {
            System.out.println("Do you want to delete members?[Y]/[N]");
            String choice = scanner.nextLine();


            while (choice.toUpperCase().contains("Y")) {


                if (!members.isEmpty()) {
                    for (int i = 0; i < members.size(); i++) {

                        Member member = members.get(i);

                        System.out.println(member.getNameMember() + " ( " + member.getIdMember() + " )");
                    }

                    System.out.print("Enter ID of user: ");
                    int idUser = Integer.parseInt(scanner.nextLine());

                    memberDao.DeleteTeamMembers(idTeam, idUser);

                    System.out.println("Do you want to delete members?[Y]/[N]");
                    choice = scanner.nextLine();
                } else {
                    System.out.println("No members available to delete");
                    break;
                }

            }

            System.out.print("\n\n\n");
            System.out.println("Members are deleted successfully");
        } else {

            System.out.println("No members available to delete");
        }

        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }


    public void Exit() {


    }


}
