package comparators;

import model.Task;

import java.time.LocalDateTime;
import java.util.Comparator;

public class PriorityComparator<T> implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2){
        LocalDateTime localDateTime1 = task1.getLocalDateTime();
        LocalDateTime localDateTime2 = task2.getLocalDateTime();

        if((localDateTime1 == null) && (localDateTime2 == null)){
            return Integer.compare(task1.getIdOfTask(), task2.getIdOfTask());
        }
        if (localDateTime1 == null){
            return 1;
        }
        if (localDateTime2 == null){
            return -1;
        }
        if (localDateTime1.compareTo(localDateTime2) == 0){
            return Integer.compare(task1.getIdOfTask(), task2.getIdOfTask());
        }
        return localDateTime1.compareTo(localDateTime2);
    }
}
