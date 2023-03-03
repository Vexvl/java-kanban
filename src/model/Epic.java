package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private List<Subtask> subtasks;

    private Type type = Type.EPIC;

    private Duration duration;

    private int minutesToDo;

    private LocalDateTime endTime;

    private LocalDateTime startTime;

    public Epic(String name, String description, int id, Status status, int minutesToDo, String startTime) {
        super(name, description, id, status, minutesToDo, startTime);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        subtasks = new ArrayList<>();
        this.startTime = LocalDateTime.parse(startTime);
        this.endTime = setEndTime();
    }

    public void setMinutesToDoOfEpic(int minutesToDo){
         this.minutesToDo = minutesToDo;
         duration = Duration.ofMinutes(minutesToDo);
    }

    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }


    public void setEpicStatus(Status status) {
        this.status = status;
    }

    public Status getEpicStatus(){
        return status;
    }

    public int getEpicId() {
        return id;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + "," + minutesToDo + startTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + getEndTime().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Epic epic = (Epic) obj;

        return Objects.equals(name, epic.name) && Objects.equals(description, epic.description) && Objects.equals(id, epic.id) && Objects.equals(type, epic.type) && Objects.equals(status, epic.status) && Objects.equals(minutesToDo, epic.minutesToDo) && Objects.equals(startTime, epic.startTime) && Objects.equals(subtasks, epic.subtasks)  && Objects.equals(endTime, epic.endTime);

    }

}