package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task  {

    protected int id;
    protected String name;
    protected String description;

    protected Status status;
    private Type type = Type.TASK;

    private LocalDateTime endTime;

    private Boolean isSubtask = false;
    private Duration duration;
    private int minutesToDo;
    private LocalDateTime startTime;
    private int epicId = 0;

    public Task(String name, String description, int id, Status status, int minutesToDo, String startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.minutesToDo = minutesToDo;
        this.duration = Duration.ofMinutes(minutesToDo);
        this.startTime = LocalDateTime.parse(startTime);
        this.endTime = setEndTime();
    }


    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + "," + duration.toMinutes() + "," + startTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + getEndTime().toString();
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
        LocalDateTime endTime = startTime.plusMinutes(minutesToDo);
        return endTime;
    }

    public LocalDateTime setEndTime(){
        return startTime.plusMinutes(minutesToDo);
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

        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(id, task.id) && Objects.equals(type, task.type) && Objects.equals(status, task.status) && Objects.equals(minutesToDo, task.minutesToDo)&& Objects.equals(startTime, task.startTime) && Objects.equals(endTime, task.endTime);

    }

}