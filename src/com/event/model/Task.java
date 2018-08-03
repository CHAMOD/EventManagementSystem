package com.event.model;

import java.sql.Date;


public class Task {

    private int idTask;
    private String taskName;
    private Date dateCreate;
    private int duration;
    private int createUserId;
    private String createdUserName;
    private String assignedMemberName;
    private String status;


    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    public String getAssignedMemberName() {
        return assignedMemberName;
    }

    public void setAssignedMemberName(String assignedMemberName) {
        this.assignedMemberName = assignedMemberName;
    }
}
