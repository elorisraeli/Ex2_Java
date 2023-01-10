import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.concurrent.*;

/**
 * A CustomExecutor is a class that implements the Executor interface and has a priority queue
 * and a constructor that sets the priority queue
 */
public class CustomExecutor  extends ThreadPoolExecutor {

    private int currentMaxPriority;// the current max priority of the tasks in the queue
    private HashMap<Integer, Integer> priorityHashMap = new HashMap<>(); // section 7

    /**
     * Creates a new CustomExecutor that will, upon running, execute the given
     */
    public CustomExecutor() {
        super(Runtime.getRuntime().availableProcessors()/2,
                Runtime.getRuntime().availableProcessors()-1,
                300L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(new TreeSet<>(new CustomExecutorComparator())));
    }


    /**
     *  submits a task to the executor and adds it to the priority queue
     * @param callable the callable task to be executed
     * @param taskType the type of the task
     * @return the Task that was submitted
     */
    public <T> Future<T> submit(Callable<T> callable, TaskType taskType){ // section 2
        if (callable == null) {
            throw new NullPointerException("Callable is null");
        }
        if (taskType == null) {
            throw new NullPointerException("TaskType is null");
        }
        Task<T> task = Task.createTask(callable, taskType);
        MyFutureTask<T> myFutureTask = new MyFutureTask<T>(callable, taskType.getPriorityValue());
        if(task.getPriority() < currentMaxPriority){
            currentMaxPriority = task.getPriority();
        }
        priorityHashMap.put(taskType.getPriorityValue(), priorityHashMap.getOrDefault(taskType.getPriorityValue(), 0) + 1);
        execute(myFutureTask);
        return myFutureTask;
    }

    /**
     * submits a task to the executor and adds it to the priority queue
     * @param callable the task to submit
     * @return the Task that was submitted
     */
    public <T> Future<T> submit(Callable<T> callable) { // section 3
        if (callable == null) {
            throw new NullPointerException("Callable is null");
        }
        Task<T> task = Task.createTask(callable);
        MyFutureTask<T> myFutureTask = new MyFutureTask<T>(callable, task.getPriority());
        if(task.getPriority() < currentMaxPriority){
            currentMaxPriority = task.getPriority();
        }
        priorityHashMap.put(task.getPriority(), priorityHashMap.getOrDefault(task.getPriority(), 0) + 1);
        execute(myFutureTask);
        return myFutureTask;
    }

    @Override
    /**
     * before executing a task, checks if the task's priority is higher than the current max priority
     * if it is, the current max priority is updated
     * @param r the task to be executed
     *          @param t the thread that will execute the task
     *
     */
    protected void beforeExecute(Thread t, Runnable r) {
        MyFutureTask myFutureTask = (MyFutureTask) r;
     priorityHashMap.put(myFutureTask.getPriority(), priorityHashMap.get(myFutureTask.getPriority()) - 1);
        if (myFutureTask.getPriority() > currentMaxPriority) {
            currentMaxPriority = myFutureTask.getPriority();
        }
    }


    /**
     * this method shuts down the executor and waits for all the tasks to finish
     */
    public void gracefullyTerminate() {
        super.shutdown();
        try {
            super.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * function to get the current max priority of the tasks in the queue
     * @return the current max priority of the tasks in the queue
     */
    public int getCurrentMax() { // section 10
        return this.currentMaxPriority;
    }


    /**
     * blocks the current thread until the executor has no more tasks to execute
     */
    public BlockingQueue<Runnable> getPriorityQueue() {
        return super.getQueue();
    }
}
