package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class Task  {

    protected int id;
    protected String name;
    protected String description;

    protected Status status;
    private Type type = Type.TASK;

    private LocalDateTime endTime;

    private Boolean isSubtask = false;
    private int minutesToDo;
    private LocalDateTime startTime;
    private int epicId = 0;

    public Task(String name, String description, int id, Status status, int minutesToDo, String startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.minutesToDo = minutesToDo;
        this.startTime = LocalDateTime.parse(startTime);
        this.endTime = setEndTime();
    }

    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
    }


    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + "," + startTime.toString() + "," + minutesToDo + "," +  getEndTime().toString();
    }

    public Integer getIdOfTask() {
        return id;
    }

    public int getEpicId() {
        return epicId;
    }

    public Type getType() {
        return type;
    }

    public int getMinutesToDo(){
        return minutesToDo;
    }

    public LocalDateTime getLocalDateTime(){
        return startTime;
    }

    public LocalDateTime getEndTime(){
        if (startTime != null){
            LocalDateTime endTime = startTime.plusMinutes(minutesToDo);
            return endTime;
        }
      return LocalDateTime.now();
    }

    public LocalDateTime setEndTime(){
        if (startTime != null){
            return startTime.plusMinutes(minutesToDo);
        }
        return LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task task = (Task) obj;

        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(id, task.id) && Objects.equals(type, task.type) && Objects.equals(status, task.status) && Objects.equals(startTime, task.startTime) && Objects.equals(minutesToDo, task.minutesToDo) && Objects.equals(endTime, task.endTime);

    }

}