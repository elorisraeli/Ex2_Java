import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class is used to create a thread that will read a file
 * and count the number of words in the file.
 */
public class MyThread extends Thread {
    private String fileName;// The name of the file to be read
    private int numOfLines=0;// The number of lines in the file


    /**
     * This is the constructor for the MyThread class.
     * @param fileName - the name of the file to be read.
     *                 Returns: None.
     */
    public MyThread(String fileName) {
        this.fileName = fileName;
    }



    /**
     * This method returns the name of the file.
     * @return - the name of the file.
     */
    public String getFileName() {
        return fileName;
    }

    @Override
    /**
     * This method is used to read the file and count the number of words in the file.
     * @return None.
     * @throws FileNotFoundException
     */
    public void run() {
        try (Scanner in = new Scanner(new File(fileName))) {
            while (in.hasNextLine()) {
                in.nextLine();
                numOfLines++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * NumberOfLines method returns the number of lines in the file.
     * @return the number of lines in the file.
     */
    public int NumberOfLines() {
        return numOfLines;
    }


}
