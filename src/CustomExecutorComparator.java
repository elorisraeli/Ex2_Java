import java.util.Comparator;
import java.util.concurrent.FutureTask;

class CustomExecutorComparator implements Comparator<MyFutureTask> {

    @Override
    public int compare(MyFutureTask myFutureTask1, MyFutureTask myFutureTask2) {
          return myFutureTask1.compareTo(myFutureTask2);
    }
}