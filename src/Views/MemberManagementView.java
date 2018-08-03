package Views;

import Services.AuthenticationServices;
import com.event.DaoImpl.MemberImpl;
import com.event.DaoImpl.PersonImpl;
import com.event.DaoImpl.TaskImpl;
import com.event.DaoImpl.TeamImpl;
import com.event.model.Member;
import com.event.model.Task;
import com.event.model.Team;
import com.event.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberManagementView {

    TeamImpl teamDao;
    PersonImpl personDAO;
    MemberImpl memberDao;
    TaskImpl taskDao;

    public MemberManagementView() {
        teamDao = new TeamImpl();
        personDAO = new PersonImpl();
        memberDao = new MemberImpl();
        taskDao = new TaskImpl();

    }


    public boolean Run() throws IOException {


        while (true) {
            EnumMenu choice = RenderMenuAdmin();

            switch (choice) {

                case List: {
                    ListTeamMembers();
                    break;
                }
                case Add: {
                    AddTeamMembers();
                    break;
                }
                case Assign: {
                    AssignTaskByAdmin();
                    break;
                }
                case Delete: {
                    DeleteTeamMembers();
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


    }


    private EnumMenu RenderMenuAdmin() throws IOException {

        while (true) {
            System.out.print("\n\n\n");

            System.out.println("[L]ist Team Members");
            System.out.println("[A]dd Team Members");
            System.out.println("[D]elete Team Members");
            System.out.println("[V]iew Team Member Details");
            System.out.println("A[s]sign Tasks");
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
                case "S": {
                    return EnumMenu.Assign;
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
    /*add members to any team*/
    public void AddTeamMembers() {

        System.out.print("\n\n\n");
        System.out.println("####Add Team Members####");

        Scanner scanner = new Scanner(System.in);

        ArrayList<Team> teams = teamDao.GetAllTeams();

        for (int i = 0; i < teams.size(); i++) {

            Team team = teams.get(i);

            System.out.print(team.getTeamName() + " ( " + team.getIdTeam() + " )\t");
        }

        System.out.print("Enter ID of team: ");
        int idTeam = Integer.parseInt(scanner.nextLine());


        System.out.println("Available Users:");

        ArrayList<User> users = memberDao.getAvailableUsers();

        for (int i = 0; i < users.size(); i++) {

            User user = users.get(i);

            System.out.println(user.getName() + " ( " + user.getIdUser() + " )\t");
        }

        if (!users.isEmpty()) {
            System.out.print("Do you want to add members?[Y]/[N]:");
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


    /*Assign Task by admin*/
    private void AssignTaskByAdmin() {

        System.out.print("\n\n\n");
        System.out.println("####Assign Task####");


        ArrayList<Task> tasks = taskDao.GetAllTasksToAllocate();


        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.print(task.getTaskName() + " ( " + task.getIdTask() + " )\t");
        }


        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of task:");
        int idTask = Integer.parseInt(scanner.nextLine());

        ArrayList<Team> teams = teamDao.GetAllTeams();

        for (int i = 0; i < teams.size(); i++) {

            Team team = teams.get(i);

            System.out.print(team.getTeamName() + " ( " + team.getIdTeam() + " )\t");
        }


        System.out.print("Enter ID of team: ");
        int idTeam = Integer.parseInt(scanner.nextLine());


        ArrayList<Member> members = memberDao.GetAllTeamMembers(idTeam);

        for (int i = 0; i < members.size(); i++) {

            Member member = members.get(i);

            System.out.println(member.getNameMember() + " ( " + member.getIdMember() + " )");
        }
        System.out.print("Enter ID of Member: ");
        int idMember = Integer.parseInt(scanner.nextLine());


        taskDao.insertTaskByLead(idMember, idTask);

        System.out.print("\n\n\n");
        System.out.println("Task is assigned successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();


    }


    /*view team members by admin*/
    public void ListTeamMembers() {


        System.out.print("\n\n\n");
        System.out.println("####List Team Members####");

        Scanner scanner = new Scanner(System.in);

        ArrayList<Team> teams = teamDao.GetAllTeams();

        for (int i = 0; i < teams.size(); i++) {

            Team team = teams.get(i);

            System.out.print(team.getTeamName() + " ( " + team.getIdTeam() + " )\t");
        }

        System.out.print("Enter ID of team: ");
        int idTeam = Integer.parseInt(scanner.nextLine());


        ArrayList<Member> members = memberDao.GetAllTeamMembers(idTeam);

        for (int i = 0; i < members.size(); i++) {

            Member member = members.get(i);
            System.out.print("Member ID:");
            System.out.println(member.getIdMember());
            System.out.print("Member Name:");
            System.out.println(member.getNameMember());
            System.out.println("---------------------------------");
        }

        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }

    /*view team members details by admin*/
    public void ViewMemberDetails() {

        System.out.print("\n\n\n");
        System.out.println("####View Member####");

        Scanner scanner = new Scanner(System.in);

        ArrayList<Team> teams = teamDao.GetAllTeams();

        for (int i = 0; i < teams.size(); i++) {

            Team team = teams.get(i);

            System.out.print(team.getTeamName() + " ( " + team.getIdTeam() + " )\t");
        }

        System.out.print("Enter ID of team: ");
        int idTeam = Integer.parseInt(scanner.nextLine());
        ;

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


    public void DeleteTeamMembers() {

        System.out.print("\n\n\n");
        System.out.println("####Delete Member####");

        Scanner scanner = new Scanner(System.in);

        ArrayList<Team> teams = teamDao.GetAllTeams();

        for (int i = 0; i < teams.size(); i++) {

            Team team = teams.get(i);

            System.out.print(team.getTeamName() + " ( " + team.getIdTeam() + " )\t");
        }

        System.out.print("Enter ID of team: ");
        int idTeam = Integer.parseInt(scanner.nextLine());


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
