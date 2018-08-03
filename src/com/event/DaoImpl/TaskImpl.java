package com.event.DaoImpl;

import com.event.Config.Config;
import com.event.Dao.TaskDao;
import com.event.model.Task;

import java.sql.*;
import java.util.ArrayList;

public class TaskImpl implements TaskDao {

    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    Connection connection;

    public TaskImpl() {
        connection = Config.createConnection();
    }


    @Override
    public boolean insertTask(Task task, int assignedMember) {

        String query = "insert into `task` (`taskName`, `duration`, `dateCreate`, `createUserId`) values (?,?,?,?)";
        String query2 = "insert into `task_user` (`idUser`,`idTask`,`status`	) values (?,?,?)";

        int generatedKey = 0;

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setInt(2, task.getDuration());
            preparedStatement.setDate(3, task.getDateCreate());
            preparedStatement.setInt(4, task.getCreateUserId());

            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setInt(1, assignedMember);
            preparedStatement.setInt(2, generatedKey);
            preparedStatement.setString(3, task.getStatus());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean insertTask(Task task) {

        String query = "insert into `task` (`taskName`, `duration`, `dateCreate`, `createUserId`) values (?,?,?,?)";

        int generatedKey = 0;

        try {
            //taskname,duration,dateCreated, created user
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setInt(2, task.getDuration());
            preparedStatement.setDate(3, task.getDateCreate());
            preparedStatement.setInt(4, task.getCreateUserId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    }

    @Override
    public ArrayList<Task> GetAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            String sql = "SELECT t.*,tu.status,u.name as assignedName,u1.name as createdUser FROM mydb.task t \n" +
                    "\t\tLEFT JOIN\n" +
                    "        mydb.task_user tu    ON  t.idTask = tu.idTask LEFT JOIN\n" +
                    "        mydb.user u    ON  u.idUser = tu.idUser LEFT JOIN\n" +
                    "        mydb.user u1   ON  t.createUserId = u1.idUser WHERE t.isActive=1";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Task task = new Task();
                task.setIdTask(resultSet.getInt(1));
                task.setTaskName(resultSet.getString(2));
                task.setDuration(resultSet.getInt(3));
                task.setDateCreate(resultSet.getDate(4));
                task.setStatus(resultSet.getString(7));
                task.setAssignedMemberName(resultSet.getString(8));
                task.setCreatedUserName(resultSet.getString(9));

                tasks.add(task);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;

    }


    @Override
    public ArrayList<Task> GetAllTasks(int teamId) {

        ArrayList<Task> tasks = new ArrayList<>();
        try {
            String sql = "SELECT t.*,tu.status,u.name as assignedName,u1.name as createdUser FROM mydb.task t \n" +
                    "LEFT JOIN\n" +
                    " mydb.task_user tu    ON  t.idTask = tu.idTask LEFT JOIN\n" +
                    " mydb.user u    ON  u.idUser = tu.idUser LEFT JOIN\n" +
                    " mydb.user u1   ON  t.createUserId = u1.idUser \n" +
                    " \n" +
                    " LEFT JOIN\n" +
                    " mydb.member m   ON  u.idUser = m.idMember \n" +
                    "\n" +
                    " WHERE t.isActive=1 AND m.teamId=" + teamId;

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Task task = new Task();
                task.setIdTask(resultSet.getInt(1));
                task.setTaskName(resultSet.getString(2));
                task.setDuration(resultSet.getInt(3));
                task.setDateCreate(resultSet.getDate(4));
                task.setStatus(resultSet.getString(7));
                task.setAssignedMemberName(resultSet.getString(8));
                task.setCreatedUserName(resultSet.getString(9));

                tasks.add(task);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    //allocate tasks
    @Override
    public ArrayList<Task> GetAllTasksToAllocate() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {

            String sql = "SELECT t.*, u.name as createdUser FROM mydb.task t Left join mydb.user u on u.idUser=t.createUserId WHERE t.isActive=1 AND NOT EXISTS (select null from mydb.task_user tu where tu.idTask = t.idTask)";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Task task = new Task();
                task.setIdTask(resultSet.getInt(1));
                task.setTaskName(resultSet.getString(2));
                task.setDuration(resultSet.getInt(3));
                task.setDateCreate(resultSet.getDate(4));
                task.setCreatedUserName(resultSet.getString(7));

                tasks.add(task);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }


    @Override
    public Task GetByTaskId(int idTask) {
        Task task = new Task();

        try {
            String sql = "SELECT t.*,tu.status,u.name as assignedName,u1.name as createdUser FROM mydb.task t \n" +
                    "\t\tLEFT JOIN\n" +
                    "        mydb.task_user tu    ON  t.idTask = tu.idTask LEFT JOIN\n" +
                    "        mydb.user u    ON  u.idUser = tu.idUser LEFT JOIN\n" +
                    "        mydb.user u1   ON  t.createUserId = u1.idUser WHERE t.idTask=" + idTask;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                task.setIdTask(resultSet.getInt(1));
                task.setTaskName(resultSet.getString(2));
                task.setDuration(resultSet.getInt(3));
                task.setDateCreate(resultSet.getDate(4));
                task.setStatus(resultSet.getString(7));
                task.setAssignedMemberName(resultSet.getString(8));
                task.setCreatedUserName(resultSet.getString(9));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return task;
    }

    @Override
    public void EditTask(Task task, int assignedMember) {

        String sql = "UPDATE mydb.task t,mydb.task_user tu SET t.taskName=?,t.duration=?,t.createUserId=?,tu.idUser=? WHERE t.idTask=" + task.getIdTask() + " AND tu.idTask=" + task.getIdTask();
        String sql2 = "UPDATE mydb.task t SET t.taskName=?,t.duration=? ,t.createUserId=? WHERE t.idTask=?";
        String sql3 = "insert into task_user(`idTask`,`idUser`) values (?,?)";

        if (task.getAssignedMemberName() != null) {

            int rowsUpdated = 0;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, task.getTaskName());
                preparedStatement.setInt(2, task.getDuration());
                preparedStatement.setInt(3, task.getCreateUserId());
                preparedStatement.setInt(4, assignedMember);

                rowsUpdated = preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (rowsUpdated > 0) {
                System.out.println("An existing task was updated successfully!");
            }

        } else if (task.getAssignedMemberName() == null) {

            int rowsUpdated = 0;
            try {
                preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setString(1, task.getTaskName());
                preparedStatement.setInt(2, task.getDuration());
                preparedStatement.setInt(3, task.getCreateUserId());
                preparedStatement.setInt(4, task.getIdTask());

                rowsUpdated = preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(sql3);
                preparedStatement.setInt(1, task.getIdTask());
                preparedStatement.setInt(2, assignedMember);


                rowsUpdated = preparedStatement.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (rowsUpdated > 0) {
                System.out.println("An existing task was updated successfully!");
            }


        }


    }

    @Override
    public void EditTask(Task task) {

        String sql = "UPDATE mydb.task t SET t.taskName=?,t.duration=?,t.createUserId=? WHERE t.idTask=" + task.getIdTask();
        int rowsUpdated = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setInt(2, task.getDuration());
            preparedStatement.setInt(3, task.getCreateUserId());

            rowsUpdated = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (rowsUpdated > 0) {
            System.out.println("An existing task was updated successfully!");
        }

    }

    @Override
    public void DeleteTask(int idTask) {
        try {
            String query = "UPDATE mydb.task t,mydb.task_user tu SET t.isActive=0,tu.isActive=0 where t.idTask =" + idTask + " AND tu.idTask=" + idTask;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insertTaskByLead(int idMember, int idTask) {
        String query = "insert into `task_user` (`idUser`,`idTask`	) values (?,?)";

        int generatedKey = 0;


        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idMember);
            preparedStatement.setInt(2, idTask);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public ArrayList<Task> GetAllTasksByMember(int memberId) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            String sql = "SELECT t.*,tu.status,u.name as assignedName,u1.name as createdUser FROM mydb.task t \n" +
                    "LEFT JOIN  mydb.task_user tu    ON  t.idTask = tu.idTask LEFT JOIN\n" +
                    " mydb.user u    ON  u.idUser = tu.idUser LEFT JOIN\n" +
                    "mydb.user u1   ON  t.createUserId = u1.idUser WHERE tu.idUser=" + memberId;

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Task task = new Task();
                task.setIdTask(resultSet.getInt(1));
                task.setTaskName(resultSet.getString(2));
                task.setDuration(resultSet.getInt(3));
                task.setDateCreate(resultSet.getDate(4));
                task.setStatus(resultSet.getString(7));
                task.setAssignedMemberName(resultSet.getString(8));
                task.setCreatedUserName(resultSet.getString(9));

                tasks.add(task);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public void changeTaskStatus(int idMember, int idTask, String status) {


        try {
            String query = "UPDATE mydb.task_user tu SET tu.status='" + status + "' where tu.idTask=" + idTask + " AND tu.idUser=" + idMember;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

	

	

