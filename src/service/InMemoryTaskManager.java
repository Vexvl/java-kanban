package service;

import java.io.IOException;
import java.util.*;

import model.*;

public class InMemoryTaskManager implements TaskManager {

    protected int id = 1;

    private Set<Task> orderedTasks = new TreeSet<>(new PriorityComparator<Task>());
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subTasks = new HashMap<>();
    private Map<Integer, Task> allTasks = new HashMap<>();

    protected static HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void createTask(String name, String description, int minutesToDo, String startTime) throws ManagerSaveException, IOException {
        Task task = new Task(name, description, id, Status.NEW, minutesToDo, startTime);
        tasks.put(id, task);
        allTasks.put(id, task);
        id++;
    }

    @Override
    public void createEpic(String name, String description) throws ManagerSaveException, IOException {
        Epic epic = new Epic(name, description, id, Status.NEW);
        epics.put(id, epic);
        allTasks.put(id, epic);
        id++;
    }

    @Override
    public void createSubTask(String name, String description, int epicId, int minutesToDo, String startTime) throws ManagerSaveException, IOException {
        Subtask subtask = new Subtask(name, description, id, Status.NEW, epicId, minutesToDo, startTime);
        subTasks.put(id, subtask);
        allTasks.put(id, subtask);
        epics.get(epicId).getSubtasks().add(subtask);
        epics.get(epicId).calculateDuration();
        id++;
    }


    @Override
    public Map<Integer, Task> getAllTypeTasks() {
        return allTasks;
    }

    @Override
    public Map<Integer,Epic> getAllEpics() {
        return epics;
    }

    @Override
    public Map<Integer,Task> getAllTasks() {
        return tasks;
    }

    @Override
    public Map<Integer,Subtask> getAllSubTasks() {
        return subTasks;
    }


    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        subTasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        if (!epics.get(id).getSubtasks().isEmpty()) {
            int findSubtask = epics.get(id).getEpicId();
            for (Subtask subtask : epics.get(id).getSubtasks()) {
                if (subtask.getEpicIdOfSubtask() == findSubtask) {
                    subTasks.remove(subtask.getIdOfSubtask());
                }
            }
        }
        epics.remove(id);
    }

    @Override
    public void deleteAll() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
    public Subtask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    @Override
    public List<Subtask> getSubtasksByEpic(Epic epic) {
        return epic.getSubtasks();
    }

    @Override
    public void updateStatusOfEpic(Epic epic) {
        if (epic.getSubtasks().isEmpty()) {
            epic.setEpicStatus(Status.NEW);
        } else if (!epic.getSubtasks().isEmpty()) {
            int doneTask = 0;
            int toDoTask = epic.getSubtasks().size();
            for (Subtask subtask : epic.getSubtasks()) {
                if (subtask.getStatusSubtask() == (Status.DONE)) {
                    doneTask++;
                }
            }
            if (doneTask == toDoTask) {
                epic.setEpicStatus(Status.DONE);
            } else epic.setEpicStatus(Status.IN_PROGRESS);
        }
    }


    private void changeSubtaskStatus(int id, Status status) {
        subTasks.get(id).setStatusSubtask(Status.DONE);
    }


    @Override
    public Epic getEpic(int id) {
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        return subTasks.get(id);
    }

    @Override
    public Task getTask(int id) {
        return tasks.get(id);
    }

    protected static HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        for (Object task : getAllTasks().values()) {
            orderedTasks.add((Task) task);
        }
        for (Object epic : getAllEpics().values()) {
            orderedTasks.add((Task) epic);
        }
        for (Object subtask : getAllSubTasks().values()) {
            orderedTasks.add((Task) subtask);
        }
        return orderedTasks;
    }


    @Override
    public boolean IfIntersection(Task task) throws IntersectionException {
        if (task.getEndTime().isBefore(allTasks.get(task.getIdOfTask()-1).getEndTime())) {
            throw new IntersectionException("Присутствует пересечение времени задач");
        }
        return false;
    }
}