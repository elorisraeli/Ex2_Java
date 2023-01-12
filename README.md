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

Lets get to the point: Who is faster? by running the program and execute 1000 tasks of create file and enter data indise,
the ouptput times shows that the function without the threads and the threadpool is the faster,
this is because the system decide which how to divide the execution of the task and optimize the best.
After that the 2nd faster is the threadpool! This is because the cost of the creation of the threads and the destoying of them is expensive,
therefore, with 1000 missions like that, threadpool optimize the threads by the option to reuse thread after finish task, while the threads
are created and destroyed each time.
To sum up: 1st fast is the system, 2nd fast is the threadpool, and the last is threads. 

![Ex2_Part1_Diagram](https://user-images.githubusercontent.com/53333654/212181648-cf4f88fa-87bd-43fa-b116-0368f41ccbd7.png)


## Part B
In this part we need to create Task objects and to execute the by priority with the CustomExecutor class.
Task class implements Callable<T> (because we want it to be a generic call): we create task with the prioroty number and the callable call.
MyFutureTask<T> extands Futute<T> and implemets Comarable of MyFutureTask: this class is the future of the Task class, means we get the results and the executes later in this class, create itself by super to FutureTask.
CustomExecutor class extands ThreadPoolExecutor: in the "pool" we have tasks (MyFutureTask) and we create "pool" by the min and max of the exercise.
We decide to use PriorityBlockingQueue to order the threads by their priority number, using TreeSet and CustomExecutorComparator.
CustomExecutorComparator class implements Comprator: use to compare MyFutureTask objects by their priority.
TaskType is enum class: define the priority of the threads, by 1 to 10 when lower number is high priority.

![Ex2_Part2_Diagram](https://user-images.githubusercontent.com/53333654/212181678-99982651-08db-4277-8b43-3d572ed7a4f7.png)



## Get Started
To run by yourself the code, click on the green table with the text "Code", then click "download ZIP".
Go to your path where you download the file and extract the files to your computer, than open the file as a project in your IDE.

## Usage
To see how the communication between the classes works you can run the Tests class and to understand by the code the details.
## Contributing
Elor Israeli and Roni Micheali
