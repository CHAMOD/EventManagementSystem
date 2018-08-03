package com.event.Dao;

import com.event.model.Task;

import java.util.ArrayList;

public interface TaskDao {


    //	added by champ
    public boolean insertTask(Task task, int assignedMember);

    public boolean insertTask(Task task);

    ArrayList<Task> GetAllTasks();

    ArrayList<Task> GetAllTasks(int teamId);

    ArrayList<Task> GetAllTasksByMember(int memberId);

    public void insertTaskByLead(int idMember, int idTask);

    ArrayList<Task> GetAllTasksToAllocate();

    void changeTaskStatus(int idMember, int idTask, String status);

    public Task GetByTaskId(int idTask);

    void EditTask(Task task, int assignedMember);

    void EditTask(Task task);

    void DeleteTask(int idTask);

}
