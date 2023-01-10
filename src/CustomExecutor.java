import java.util.HashMap;
import java.util.TreeSet;
import java.util.concurrent.*;

/**
 * A CustomExecutor is a class that implements the Executor interface and has a priority queue
 * and a constructor that sets the priority queue
 */
public class CustomExecutor extends ThreadPoolExecutor {

    private final int PRIORITY_MIN_OPTION = 10;
    private HashMap<Integer, Integer> priorityHashMap = new HashMap<>();

    public void setPriorityHashMap() {
        for (int i = 1; i <= PRIORITY_MIN_OPTION; i++) {
            this.priorityHashMap.put(i, 0);
        }
    }

    /**
     * Creates a new CustomExecutor that will, upon running, execute the given
     */
    public CustomExecutor() {
        super(Runtime.getRuntime().availableProcessors() / 2,
                Runtime.getRuntime().availableProcessors() - 1,
                300L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(new TreeSet<>(new CustomExecutorComparator())));

        setPriorityHashMap();
    }


    /**
     * submits a task to the executor and adds it to the priority queue
     *
     * @param callable the callable task to be executed
     * @param taskType the type of the task
     * @return the Task that was submitted
     */
    public <T> Future<T> submit(Callable<T> callable, TaskType taskType) {
        if (callable == null) {
            throw new NullPointerException("Callable is null");
        }
        if (taskType == null) {
            throw new NullPointerException("TaskType is null");
        }
        Task<T> task = Task.createTask(callable, taskType);
        this.priorityHashMap.put(task.getPriority(), this.priorityHashMap.get(task.getPriority()) + 1);
        MyFutureTask<T> myFutureTask = new MyFutureTask<T>(callable, taskType.getPriorityValue());
        super.execute(myFutureTask);
        return myFutureTask;
    }

    /**
     * submits a task to the executor and adds it to the priority queue
     *
     * @param callable the task to submit
     * @return the Task that was submitted
     */
    public <T> Future<T> submit(Callable<T> callable) {
        if (callable == null) {
            throw new NullPointerException("Callable is null");
        }
        Task<T> task = Task.createTask(callable);
        this.priorityHashMap.put(task.getPriority(), this.priorityHashMap.get(task.getPriority()) + 1);
        MyFutureTask<T> myFutureTask = new MyFutureTask<T>(callable, task.getPriority());
        super.execute(myFutureTask);
        return myFutureTask;
    }

    @Override
    /**
     * before executing a task, checks if the task's priority is higher than the current max priority
     * if it is, the current max priority is updated
     * @param r the task to be executed
     * @param t the thread that will execute the task
     *
     */
    protected void beforeExecute(Thread t, Runnable r) {
        MyFutureTask myFutureTask = (MyFutureTask) r;
        this.priorityHashMap.put(myFutureTask.getPriority(), this.priorityHashMap.get(myFutureTask.getPriority()) - 1);
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
     *
     * @return the current max priority of the tasks in the queue
     */
    public int getCurrentMax() {
//        System.out.println(priorityHashMap.toString());
        for (int i = 1; i < PRIORITY_MIN_OPTION; i++) {
            if (priorityHashMap.get(i) != null && priorityHashMap.get(i) > 0) {
                return i;
            }
        }
        return PRIORITY_MIN_OPTION;
    }


    /**
     * blocks the current thread until the executor has no more tasks to execute
     */
    public BlockingQueue<Runnable> getPriorityQueue() {
        return super.getQueue();
    }
}
