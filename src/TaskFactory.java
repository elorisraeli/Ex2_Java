import java.util.concurrent.Callable;

/**
 * A TaskFactory is a class that creates Task objects
 *
 * @param <T>
 */
public class TaskFactory<T> {
    public Task<T> createTask(Callable<T> callable, TaskType taskType) {
        int taskTypePriority = taskType.getPriorityValue();
        return new Task<T>(callable, taskType, taskTypePriority);
    }

    public Task<T> createTask(Callable<T> callable) {
        return new Task<T>(callable, TaskType.OTHER, 3);
    }
}
