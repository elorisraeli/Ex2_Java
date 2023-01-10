import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Ex2_1 {

    public  static  void main(String[] args) throws FileNotFoundException {
        //ask the user if he wants to create a file test
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to create a file test? (y/n)");
        String answer = in.nextLine();
        if (answer.equals("y")) {
            compareRunningTime();
        }
        else {
            System.out.println("Goodbye");
        }

    }

    private static void compareRunningTime() {
        long startTime, endTime;
        String[] files;
        System.out.println(" ");
        System.out.println("compareRunningTime first seed 1000 bound 10000 ");
        files = createTextFiles(10, 100, 100);
        System.out.println("numer of lines in all file = " + getNumOfLines(files));
        startTime = System.currentTimeMillis();
        getNumOfLines(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLines running time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        getNumOfLinesThreads(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLinesThreads running time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        getNumOfLinesThreadPool(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLinesThreadPool running time: " + (endTime - startTime) + "ms");


        System.out.println(" ");
        System.out.println("compareRunningTime second 100 files seed 100 bound 100 ");
        files = createTextFiles(100, 100, 100);
        System.out.println("numer of lines in all file = " + getNumOfLines(files));
        startTime = System.currentTimeMillis();
        getNumOfLines(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLines running time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        getNumOfLinesThreads(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLinesThreads running time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        getNumOfLinesThreadPool(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLinesThreadPool running time: " + (endTime - startTime) + "ms");

        System.out.println(" ");
        System.out.println("compareRunningTime third 1000 files seed 1000 bound 1000 ");
        files = createTextFiles(1000, 1000, 1000);
        System.out.println("numer of lines in all file = " + getNumOfLines(files));
        startTime = System.currentTimeMillis();
        getNumOfLines(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLines running time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        getNumOfLinesThreads(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLinesThreads running time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        getNumOfLinesThreadPool(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLinesThreadPool running time: " + (endTime - startTime) + "ms");

        System.out.println(" ");
        System.out.println("compareRunningTime fourth 1000 files seed 10000 bound 10000 ");
        files = createTextFiles(1000, 10000, 10000);
        System.out.println("numer of lines in all file = " + getNumOfLines(files));
        startTime = System.currentTimeMillis();
        getNumOfLines(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLines running time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        getNumOfLinesThreads(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLinesThreads running time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        getNumOfLinesThreadPool(files);
        endTime = System.currentTimeMillis();
        System.out.println("getNumOfLinesThreadPool running time: " + (endTime - startTime) + "ms");

    }

    /**
     * This method creates a number of text files with random numbers
     * @param n - the number of files to create
     * @param seed - the number of lines in each file
     * @param bound - the bound of the random numbers
     * @return - an array of the files names
     * @throws FileNotFoundException
     */
    public static String[] createTextFiles(int n, int seed, int bound){
        String[] fileNames = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            fileNames[i] = "file" + i + ".txt";
            try (PrintWriter out = new PrintWriter(fileNames[i])) {
                for (int j = 0; j < rand.nextInt(bound); j++) {
                    out.println(rand.nextInt(bound)+ " Hello World! ");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return fileNames;
    }

   //this method run on time of o(n) because it run on all files
   // and count lines in each file and sum all lines in all files
   /**
    * This method returns the number of lines in all the files
    * @param fileNames - an array of the files names
    * @return - the number of lines in all the files
    * @throws FileNotFoundException
    */
    public static int getNumOfLines(String[] fileNames){
        int numOfLines = 0;
        for (String fileName : fileNames) {
            try (Scanner in = new Scanner(new File(fileName))) {
                while (in.hasNextLine()) {
                    in.nextLine();
                    numOfLines++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return numOfLines;
    }

    //the getNumOfLinesThreads method run on time of o(n) because it run on all files
    //and count lines in each file and sum all lines in all files
    //but it run on threads and each thread run on one file

    /**
     * This method returns the number of lines in all the files using threads
     * @param fileNames - an array of the files names
     * @return - the number of lines in all the files
     * @throws FileNotFoundException
     */
    public static int getNumOfLinesThreads(String[] fileNames) {
        int numOfLines = 0;
        MyThread[] threads = new MyThread[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            threads[i] = new MyThread(fileNames[i]);
            threads[i].start();
        }
        for (MyThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            numOfLines += thread.NumberOfLines();
        }
        return numOfLines;
    }

    //the getNumOfLinesThreadPool method run on time of o(n) because it run on all files
    //and count lines in each file and sum all lines in all files
    //but it run on threads and each thread run on one file

    /**
     * This method returns the number of lines in all the files using thread pool
     * @param fileNames - an array of the files names
     * @return - the number of lines in all the files
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public static int getNumOfLinesThreadPool(String[] fileNames) {
        int numOfLines = 0;
        ExecutorService thread_pool = Executors.newFixedThreadPool(fileNames.length);
        try {
            for (int i = 0; i < fileNames.length; i++) {
                numOfLines += thread_pool.submit(new MyCallable(fileNames[i])).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread_pool.shutdown();
        return numOfLines;
    }


}

