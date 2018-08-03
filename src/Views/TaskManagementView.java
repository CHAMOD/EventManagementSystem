package Views;

import Services.AuthenticationServices;
import com.event.DaoImpl.MemberImpl;
import com.event.DaoImpl.PersonImpl;
import com.event.DaoImpl.TaskImpl;
import com.event.DaoImpl.TeamImpl;
import com.event.model.Member;
import com.event.model.Task;
import com.event.model.Team;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManagementView {

    TeamImpl teamDao;
    PersonImpl personDAO;
    TaskImpl taskDao;
    MemberImpl memberDao;

    public TaskManagementView() {
        teamDao = new TeamImpl();
        personDAO = new PersonImpl();
        taskDao = new TaskImpl();
        memberDao = new MemberImpl();
    }

    public boolean Run(String role) throws Exception {

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

        }


        if (role.contains("lead")) {
            while (true) {
                EnumMenu choice = RenderMenuLead();

                switch (choice) {

                    case List: {
                        ListTask();
                        break;
                    }
                    case Add: {
                        AddTask();
                        break;
                    }
                    case Edit: {
                        //yet to complete
                        Edit();
                        break;
                    }
                    case Assign: {
                        AssignTask();
                        break;
                    }
                    case Delete: {
                        Delete();
                        break;
                    }
                    case View: {
                        ViewTask();
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

    /*menu for admin*/
    private EnumMenu RenderMenu() throws IOException {

        while (true) {
            System.out.print("\n\n\n");

            System.out.println("[L]ist Tasks");
            System.out.println("[A]dd Tasks");
            System.out.println("[E]dit Tasks");
            System.out.println("[D]elete Tasks");
            System.out.println("[V]iew Tasks");
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

    /*menu for lead*/
    private EnumMenu RenderMenuLead() throws IOException {

        while (true) {
            System.out.print("\n\n\n");

            System.out.println("[L]ist Tasks");
            System.out.println("[A]dd Tasks");
            System.out.println("[E]dit Tasks");
            System.out.println("A[s]sign Tasks");
            System.out.println("[D]elete Tasks");
            System.out.println("[V]iew Task details");
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

    /*view tasks by admin*/
    private void View() {

        System.out.print("\n\n\n");
        System.out.println("####View Task####");

        ArrayList<Task> tasks = taskDao.GetAllTasks();

        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.print(task.getTaskName() + " ( " + task.getIdTask() + " )\t");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of task:");
        int id = Integer.parseInt(scanner.nextLine());

        Task task1 = taskDao.GetByTaskId(id);


        System.out.print("\n\n\n");
        System.out.println("Task ID:" + task1.getIdTask());
        System.out.println("Task Name:" + task1.getTaskName());
        System.out.println("Task Duration:" + task1.getDuration());
        System.out.println("Created Date:" + task1.getDateCreate());
        if (task1.getStatus() != null) {
            System.out.println("Status:" + task1.getStatus());
            System.out.println("Assigned member:" + task1.getAssignedMemberName());
        } else {
            System.out.println("Task in not assigned yet");
        }
        System.out.println("Created User:" + task1.getCreatedUserName());

        System.out.print("\n\n\n");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();


    }

    private void Delete() {

        System.out.print("\n\n\n");
        System.out.println("####Delete Task####");

        ArrayList<Task> tasks = taskDao.GetAllTasks();

        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.print(task.getTaskName() + " ( " + task.getIdTask() + " )\t");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of task:");
        int id = Integer.parseInt(scanner.nextLine());

        Task task1 = taskDao.GetByTaskId(id);

        taskDao.DeleteTask(task1.getIdTask());

        System.out.print("\n\n\n");
        System.out.println("Item deleted successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }

    private void Edit() {

        System.out.print("\n\n\n");
        System.out.println("####Edit Task####");

        ArrayList<Task> tasks = taskDao.GetAllTasks();

        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.print(task.getTaskName() + " ( " + task.getIdTask() + " )\t ");
        }
        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter Id of Task: ");
        int id = Integer.parseInt(scanner.nextLine());
        Task item = taskDao.GetByTaskId(id);

        System.out.print("Task Name ( " + item.getTaskName() + " ): ");
        item.setTaskName(scanner.nextLine());

        System.out.print("Duration (" + item.getDuration() + "): ");
        item.setDuration(scanner.nextInt());

        System.out.print("Assigned Member (" + item.getAssignedMemberName() + "): \n");

        System.out.print("Do you want to change assigned member? [Y]/[N]:");
        scanner.nextLine();
        String choice = scanner.nextLine();

        if (choice.toUpperCase().contains("Y")) {
            ArrayList<Team> teams = viewTeams();

            for (int i = 0; i < teams.size(); i++) {

                Team team = teams.get(i);

                System.out.println(team.getTeamName() + " ( " + team.getIdTeam() + " )\t");
            }
            System.out.println("Select a team:");

            int teamId = scanner.nextInt();
            ArrayList<Member> members = viewTeamMembers(teamId);

            for (int i = 0; i < members.size(); i++) {

                Member member = members.get(i);

                System.out.println(member.getNameMember() + " ( " + member.getIdMember() + " )\t");

            }


            if (!members.isEmpty()) {
                System.out.print("Enter Responsible Member ID:");
                int assignedMember = 0;
                assignedMember = scanner.nextInt();
                item.setCreateUserId(AuthenticationServices.getInstance().getLoggedUser().getIdUser());
                taskDao.EditTask(item, assignedMember);
            } else {

                System.out.print("No members in the team:");
                item.setCreateUserId(AuthenticationServices.getInstance().getLoggedUser().getIdUser());
                taskDao.EditTask(item);
            }

            System.out.println("Press [Enter] to continue");
            scanner.nextLine();

        } else {

            item.setCreateUserId(AuthenticationServices.getInstance().getLoggedUser().getIdUser());


            taskDao.EditTask(item);
            System.out.println("Press [Enter] to continue");
            scanner.nextLine();

        }


    }

    private void Add() {

        System.out.print("\n\n\n");
        System.out.println("####Add Task####");
        Scanner scanner = new Scanner(System.in);

        Task item = new Task();

        System.out.print("Task Name:");
        item.setTaskName(scanner.nextLine());

        System.out.print("Duration:");
        item.setDuration(Integer.parseInt(scanner.nextLine()));

        System.out.println("Do you want to allocate the task? [Y]/[N]:");
        String choice = scanner.nextLine();
        System.out.println(choice);

        if (choice.toUpperCase().equals("Y")) {
            ArrayList<Team> teams = viewTeams();


            for (int i = 0; i < teams.size(); i++) {

                Team team = teams.get(i);

                System.out.println(team.getTeamName() + " ( " + team.getIdTeam() + " )\t");
            }
            System.out.println("Select a team:");

            int teamId = scanner.nextInt();
            ArrayList<Member> members = viewTeamMembers(teamId);

            for (int i = 0; i < members.size(); i++) {

                Member member = members.get(i);

                System.out.println(member.getNameMember() + " ( " + member.getIdMember() + " )\t");

            }


            System.out.print("Enter Responsible Member ID:");
            int assignedMember = scanner.nextInt();


            item.setStatus("pending");


            item.setCreateUserId(AuthenticationServices.getInstance().getLoggedUser().getIdUser());

            java.sql.Date date = getCurrentDatetime();
            item.setDateCreate(date);

            taskDao.insertTask(item, assignedMember);

        } else {

            item.setCreateUserId(AuthenticationServices.getInstance().getLoggedUser().getIdUser());
            java.sql.Date date = getCurrentDatetime();
            item.setDateCreate(date);
            item.setStatus("pending");

            taskDao.insertTask(item);

        }

        System.out.print("\n\n\n");
        System.out.println("Item added successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();
    }


    private void List() {

        System.out.print("\n\n\n");
        System.out.println("####List Task####");

        ArrayList<Task> tasks = taskDao.GetAllTasks();

        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.println("Task ID:" + task.getIdTask());
            System.out.println("Task Name:" + task.getTaskName());
            System.out.println("Task Duration:" + task.getDuration());
            System.out.println("Created Date:" + task.getDateCreate());
            if (task.getStatus() != null) {
                System.out.println("Status:" + task.getStatus());
                System.out.println("Assigned Member:" + task.getAssignedMemberName());
            } else {
                System.out.println("Task in not assigned yet");
            }
            System.out.println("Created User:" + task.getCreatedUserName());
            System.out.println("---------------------------------");

        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }


    /*add task by lead*/
    private void AddTask() {

        System.out.print("\n\n\n");
        System.out.println("####Add Task####");
        Scanner scanner = new Scanner(System.in);

        Task item = new Task();

        System.out.print("Task Name:");
        item.setTaskName(scanner.nextLine());

        System.out.print("Duration:");
        item.setDuration(Integer.parseInt(scanner.nextLine()));

        System.out.println("Do you want to allocate the task? [Y]/[N]:");
        String choice = scanner.nextLine();
        System.out.println(choice);

        if (choice.toUpperCase().equals("Y")) {

            int idLead = AuthenticationServices.getInstance().getLoggedUser().getIdUser();
            int idTeam = teamDao.getTeamIdByUser(idLead);


            ArrayList<Member> members = viewTeamMembers(idTeam);

            for (int i = 0; i < members.size(); i++) {

                Member member = members.get(i);

                System.out.println(member.getNameMember() + " ( " + member.getIdMember() + " )\t");

            }

            System.out.print("Enter Responsible Member ID:");
            int assignedMember = scanner.nextInt();


            item.setStatus("pending");


            item.setCreateUserId(AuthenticationServices.getInstance().getLoggedUser().getIdUser());

            java.sql.Date date = getCurrentDatetime();
            item.setDateCreate(date);

            taskDao.insertTask(item, assignedMember);

        } else {

            item.setCreateUserId(AuthenticationServices.getInstance().getLoggedUser().getIdUser());
            java.sql.Date date = getCurrentDatetime();
            item.setDateCreate(date);
            item.setStatus("pending");

            taskDao.insertTask(item);

        }

        System.out.print("\n\n\n");
        System.out.println("Item added successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();
    }


    private void ListTask() {
        System.out.print("\n\n\n");
        System.out.println("####List Team####");

        int idLead = AuthenticationServices.getInstance().getLoggedUser().getIdUser();
        int idTeam = teamDao.getTeamIdByUser(idLead);

        ArrayList<Task> tasks = taskDao.GetAllTasks(idTeam);

        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.println("Task ID:" + task.getIdTask());
            System.out.println("Task Name:" + task.getTaskName());
            System.out.println("Task Duration:" + task.getDuration());
            System.out.println("Created Date:" + task.getDateCreate());
            if (task.getStatus() != null) {
                System.out.println("Status:" + task.getStatus());
                System.out.println("Assigned Member:" + task.getAssignedMemberName());
            } else {
                System.out.println("Task in not assigned yet");
            }
            System.out.println("Created User:" + task.getCreatedUserName());
            System.out.println("---------------------------------");

        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Press [Enter] to continue");
        scanner.nextLine();


    }


    /*assign task by lead*/
    private void AssignTask() {

        System.out.print("\n\n\n");
        System.out.println("####Assign Task####");

        int idLead = AuthenticationServices.getInstance().getLoggedUser().getIdUser();
        int idTeam = teamDao.getTeamIdByUser(idLead);

        ArrayList<Task> tasks = taskDao.GetAllTasksToAllocate();


        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.print(task.getTaskName() + " ( " + task.getIdTask() + " )\t");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of task:");
        int idTask = Integer.parseInt(scanner.nextLine());

        ArrayList<Member> members = memberDao.GetAllTeamMembers(idTeam);

        for (int i = 0; i < members.size(); i++) {

            Member member = members.get(i);

            System.out.println(member.getNameMember() + " ( " + member.getIdMember() + " )");
        }
        System.out.print("Enter ID of Member: ");
        int idMember = Integer.parseInt(scanner.nextLine());


        taskDao.insertTaskByLead(idMember, idTask);

        System.out.print("\n\n\n");
        System.out.println("Item added successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();


    }

    /*view a task by lead*/
    private void ViewTask() {

        System.out.print("\n\n\n");
        System.out.println("####View Task####");

        int idLead = AuthenticationServices.getInstance().getLoggedUser().getIdUser();
        int idTeam = teamDao.getTeamIdByUser(idLead);

        ArrayList<Task> tasks = taskDao.GetAllTasks(idTeam);


        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.print(task.getTaskName() + " ( " + task.getIdTask() + " )\t");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of task:");
        int id = Integer.parseInt(scanner.nextLine());

        Task task1 = taskDao.GetByTaskId(id);


        System.out.print("\n\n\n");
        System.out.println("Task ID:" + task1.getIdTask());
        System.out.println("Task Name:" + task1.getTaskName());
        System.out.println("Task Duration:" + task1.getDuration());
        System.out.println("Created Date:" + task1.getDateCreate());
        if (task1.getStatus() != null) {
            System.out.println("Status:" + task1.getStatus());
            System.out.println("Assigned member:" + task1.getAssignedMemberName());
        } else {
            System.out.println("Task in not assigned yet");
        }
        System.out.println("Created User:" + task1.getCreatedUserName());

        System.out.print("\n\n\n");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();


    }


    public ArrayList<Team> viewTeams() {


        ArrayList<Team> teams = teamDao.GetAllTeams();

        return teams;
    }

    public ArrayList<Member> viewTeamMembers(int idTeam) {


        ArrayList<Member> members = personDAO.GetAllMembersByTeam(idTeam);
        return members;

    }

    public java.sql.Date getCurrentDatetime() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

}

