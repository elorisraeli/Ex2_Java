import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * This class created for the execution of the tasks in the thread pool
 * it is our version for the FutureTask class
 */
public class MyFutureTask<T> extends FutureTask<T> implements Comparable<MyFutureTask<T>>
{
    private int priority;// priority of the task

    /**
     * Creates a new FutureTask that will, upon running, execute the given
     * @param callable the callable task to be executed
     * @param priority the priority of the task
     * @throws NullPointerException if the callable is null
     */
    public MyFutureTask(Callable<T> callable, int priority) {
        super(callable);
        this.priority = priority;
    }

    /**
     * Creates a new FutureTask that will, upon running, execute the given
     *
     * @param task the task to submit
     */
    public MyFutureTask(Task<T> task) {
        super(task.getCallable());
        this.priority = task.getPriority();
    }




    /**
     * returns the priority of the task
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

    @Override
    /**
     * compares the priority of the task to the priority of another task
     * @param otherTask the other task to compare to
     *                  the priority of the task
     *                  1 if the priority of the task is greater than the priority of the other task
     *                  -1 if the priority of the task is less than the priority of the other task
     *                  0 if the priority of the task is equal to the priority of the other task
     */
    public int compareTo(MyFutureTask<T> otherTask) {
        if (this.priority > otherTask.priority) {
            return 1;
        } else if (this.priority < otherTask.priority) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    /**
     * toString method for the task
     * @return the String representation of the task
     */
    public String toString() {
        return "TaskFactory [priority=" + priority + "]";
    }


}
