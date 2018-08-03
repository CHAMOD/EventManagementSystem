package Views;

import Services.AuthenticationServices;
import com.event.DaoImpl.MemberImpl;
import com.event.DaoImpl.PersonImpl;
import com.event.DaoImpl.TaskImpl;
import com.event.DaoImpl.TeamImpl;
import com.event.model.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class UserTaskManagementView {

    TeamImpl teamDao;
    PersonImpl personDAO;
    MemberImpl memberDao;
    TaskImpl taskDao;


    public UserTaskManagementView() {
        teamDao = new TeamImpl();
        personDAO = new PersonImpl();
        memberDao = new MemberImpl();
        taskDao = new TaskImpl();

    }


    public boolean Run() throws IOException {


        while (true) {
            EnumMenu choice = RenderMenuUser();

            switch (choice) {

                case List: {
                    ListAllocatedTasks();
                    break;
                }
                case Add: {
                    AddStatusTask();
                    break;
                }
                case Delete: {
                    //Delete();
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


    }


    private EnumMenu RenderMenuUser() throws IOException {

        while (true) {
            System.out.print("\n\n\n");

            System.out.println("[L]ist All Tasks");
            System.out.println("[A]dd Status Task");
			System.out.println("[V]iew Task");
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


    /*add status to task by member*/
    public void AddStatusTask() {

        System.out.print("\n\n\n");
        System.out.println("####Update Status Task####");

        Scanner scanner = new Scanner(System.in);

        int idMember = AuthenticationServices.getInstance().getLoggedUser().getIdUser();


        ArrayList<Task> tasks = taskDao.GetAllTasksByMember(idMember);

        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.print(task.getTaskName() + " ( " + task.getIdTask() + " )\t");
        }


        System.out.print("Enter ID of Task: ");
        int idTask = Integer.parseInt(scanner.nextLine());

        Task task1 = taskDao.GetByTaskId(idTask);


        System.out.print("\n\n\n");
        System.out.println("Task ID:" + task1.getIdTask());
        System.out.println("Task Name:" + task1.getTaskName());
        System.out.println("Task Duration:" + task1.getDuration());
        System.out.println("Created Date:" + task1.getDateCreate());
        System.out.println("Current Status:" + task1.getStatus());
        System.out.println("Assigned member:" + task1.getAssignedMemberName());
        System.out.println("Created User:" + task1.getCreatedUserName());

        System.out.print("Choose Status [I]n progress/[D]one:");
        String choise = scanner.nextLine();

        String status = "pending";
        switch (choise.toUpperCase()) {

            case "I": {
                status = "in progress";
                break;
            }
            case "D": {
                status = "done";
                break;
            }
            default: {
                status = "pending";
                break;
            }


        }

        taskDao.changeTaskStatus(idMember, idTask, status);

        System.out.println("Status updated successfully");
        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }

    /*view all task assigned to member*/
    public void ListAllocatedTasks() {


        System.out.print("\n\n\n");
        System.out.println("####List Allocated Tasks####");

        Scanner scanner = new Scanner(System.in);


        int idMember = AuthenticationServices.getInstance().getLoggedUser().getIdUser();


        ArrayList<Task> tasks = taskDao.GetAllTasksByMember(idMember);

        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.println("Task ID:" + task.getIdTask());
            System.out.println("Task Name:" + task.getTaskName());
            System.out.println("Task Duration:" + task.getDuration());
            System.out.println("Created Date:" + task.getDateCreate());
            System.out.println("Status:" + task.getStatus());
            System.out.println("Created User:" + task.getCreatedUserName());
            System.out.println("---------------------------------");

        }


        System.out.println("Press [Enter] to continue");
        scanner.nextLine();

    }

    /*view task details by member*/
    public void ViewTask() {

        java.sql.Date date = getCurrentDatetime();
        Scanner scanner = new Scanner(System.in);

        int idMember = AuthenticationServices.getInstance().getLoggedUser().getIdUser();


        ArrayList<Task> tasks = taskDao.GetAllTasksByMember(idMember);

        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            System.out.print(task.getTaskName() + " ( " + task.getIdTask() + " )\t");
        }


        System.out.print("Enter ID of Task: ");
        int idTask = Integer.parseInt(scanner.nextLine());

        Task task1 = taskDao.GetByTaskId(idTask);


        System.out.print("\n\n\n");
        System.out.println("Task ID:" + task1.getIdTask());
        System.out.println("Task Name:" + task1.getTaskName());
        System.out.println("Task Duration:" + task1.getDuration());
        System.out.println("Created Date:" + task1.getDateCreate());
        System.out.println("Current Status:" + task1.getStatus());
        System.out.println("Assigned member:" + task1.getAssignedMemberName());
        System.out.println("Created User:" + task1.getCreatedUserName());


        Long differenceOfDates=ChronoUnit.DAYS.between(LocalDate.parse(task1.getDateCreate().toString()),LocalDate.parse(date.toString()));
        System.out.println("---------------------------------");

        if(differenceOfDates<task1.getDuration()){
        System.out.println("Remaining dates:"+(task1.getDuration()-differenceOfDates));
        }
        else {

            System.out.println("you are delayed");
            System.out.println("you have got additional "+(differenceOfDates-task1.getDuration())+" days");


        }


        System.out.println("\nPress [Enter] to continue");
        scanner.nextLine();
    }


    public void Exit() {


    }

    public java.sql.Date getCurrentDatetime() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

}
