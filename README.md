# Ex2_Java

# Exercise 2 - Java OOP

This project is the 3nd exercise in our OOP course, the project is about Threads and extras.
## About The Project

In this project we create a Task class which simulate the tasks that need to be done, and the CustomExecuter class which simulate the ThreadPool.

Task will implemet callable that we will be able to return some object from the task.
CustomExecuter will present the priority queue to execute task by the priority number. 

There is also a Test class to test the classes.

## Part A  
In this part we have 3 classes:
MyThread extands Thread, therfore will be a thread in this program that no return value, do run function.
MyCallable implements Callable<Integer>, so he will be a thread with a return value of integer, do call function.
Ex2_1 is the main of this program, which run all the functions and classes. Also has the exercises functions:
1. To create text files.
2. To get number of lines in a files.
3. To get number of lines in a files by using threads.
4. To get number of lines in a files by using thread pool.

We do the same function (of counting the number of lines in files), all of them "cost" (running time) O(n),
but they are different by the division the things to do to threads.
getNumOfLines function: count lines of files by the simple way, line by line, when finish move to the next.
getNumOfLinesThreads function: create threads of MyThread class and execute them to do their mission.
getNumOfLinesThreadPool function: create threads of MyCallable class and get all in the "pool" until its max size, 
when threads are done, anothers get in the pool and replace them.

![Ex2_Part1_Diagram](https://user-images.githubusercontent.com/53333654/211759929-2cf82be0-e5a9-4461-8daa-89b067e30c1d.png)


## Part B
In this part we need to create Task objects and to execute the by priority with the CustomExecutor class.
Task class implements Callable<T> (because we want it to be a generic call): we create task with the prioroty number and the callable call.
MyFutureTask<T> extands Futute<T> and implemets Comarable of MyFutureTask: this class is the future of the Task class, means we get the results and the executes later in this class, create itself by super to FutureTask.
CustomExecutor class extands ThreadPoolExecutor: in the "pool" we have tasks (MyFutureTask) and we create "pool" by the min and max of the exercise.
We decide to use PriorityBlockingQueue to order the threads by their priority number, using TreeSet and CustomExecutorComparator.
CustomExecutorComparator class implements Comprator: use to compare MyFutureTask objects by their priority.
TaskType is enum class: define the priority of the threads, by 1 to 10 when lower number is high priority.

![Ex2_Part2_Diagrams](https://user-images.githubusercontent.com/53333654/211759965-32dc5422-09aa-4319-b856-0d67bcdbd4c2.png)



## Get Started
To run by yourself the code, click on the green table with the text "Code", then click "download ZIP".
Go to your path where you download the file and extract the files to your computer, than open the file as a project in your IDE.

## Usage
To see how the communication between the classes works you can run the Tests class and to understand by the code the details.
## Contributing
Elor Israeli and Roni Micheali
