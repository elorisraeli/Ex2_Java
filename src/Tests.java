import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    @Test
    public void partialTest() throws Exception {
        CustomExecutor customExecutor = new CustomExecutor();
        var task = Task.createTask(()->{
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final Object sum;
        try {
            sum = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = ()-> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        // var is used to infer the declared type automatically
        var priceTask = customExecutor.submit(()-> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        var reverseTask = customExecutor.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = priceTask.get();
            reversed = reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Reversed String = " + reversed);
        logger.info(()-> String.valueOf("Total Price = " + totalPrice));
        logger.info(()-> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();
    }

    @Test
    public void anotherTests() throws ExecutionException, InterruptedException {
        CustomExecutor customExecutor = new CustomExecutor();

        Task<String> taskString = Task.createTask(()->{
            StringBuilder sb = new StringBuilder("Hello ");
            for (char i = 'a'; i < 't'; i++) {
                sb.append(i);
            }
            return sb.toString();
        }, TaskType.IO);
        Future<String> futureString = customExecutor.submit(taskString);

        Task<Integer> task = Task.createTask(()->{
            // calculate the factorial of 5
            int sum = 1;
            for (int i = 1; i <= 5; i++) {
                sum *= i; //1*1=1, 1*2=2, 2*3=6, 6*4=24, 24*5=120
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        Future<Integer> futureTask = customExecutor.submit(task);

        assertEquals(futureTask.get(), 120);
        assertEquals("Hello abcdefghijklmnopqrs", futureString.get());

        try {
            Integer result = futureTask.get();
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            String result = futureString.get();
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }



}
