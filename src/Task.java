import java.util.concurrent.Callable;

/**
 * A Task is a class that implements the Callable interface and has a priority field
 * and a constructor that sets the priority field
 */
public class Task<T> implements Callable<T> {
    private TaskType taskType;// the type of the task
    private  int priority;// the priority of the task
    private final Callable<T> callable;// the callable task to be executed

    /**
     * Creates a new Task that will, upon running, execute the given
     * this constructor is protected, so it can only be used by the TaskFactory class
     * @param callable the callable task to be executed
     * @param taskType the type of the task
     * @param priority the priority of the task
     */
    protected Task(Callable<T> callable, TaskType taskType, int priority) {
        this.callable = callable;
        this.taskType = taskType;
        this.priority = priority;
    }

    /**
     *  creates a new Task that will, upon running, execute the given
     * @param callable the callable task to be executed
     * @return the Task
     */
    public static <T> Task<T> createTask(Callable<T> callable){
        return new Task<T>(callable, TaskType.OTHER, 3);
    }

    /**
     * Creates a new Task that will, upon running, execute the given
     * @param callable the callable task to be executed
     * @param taskType the type of the task
     * @return the Task
     */
    public static <T> Task<T> createTask(Callable<T> callable, TaskType taskType) {
        return new Task<T>(callable, taskType, taskType.getPriorityValue());
    }

    /**
     * this method is for getting the priority of the task
     * @return the priority of the task
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * sets the priority of the task
     * @param priority the priority of the task
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * this method is to ge the callable task
     * @return the Callable task to be executed
     */
    public <T> Callable<T> getCallable(){
        return (Callable<T>) this.callable;
    }


    @Override
    /**
     * calls the callable task to be executed
     * @return the result of the callable task to be executed
     */
    public T call() throws Exception {
        return this.callable.call();
    }

    /**
     * this function is to return the type of the task
     * @return the task type
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * sets the task type
     * @param taskType the task type
     */
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }


}
