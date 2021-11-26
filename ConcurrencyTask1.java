import java.util.Scanner;
public class ConcurrencyTask1 {
    
    /*
     Counter class implemented for declaring the count
     and incrementing that same count.
     */
    static class Counter {
        int count;
        void inc() {
            count = count+1;
        }
        int getCount() {
            return count;
        }
    }
    
    /*
    Global variables to obtain from user
    */
    static Counter counter;
    static int increments;
    
    
    /*
     Incrementer class implemented for declaring the threads
     which will increments the counter based on user's input,
     using a for loop.
     */
    static class Incrementer extends Thread {
        public void run() {
            for (int i = 0; i < increments; i++) {
                counter.inc();
            }
        }
    }
    
    
    /*
     Main method implementation in a while loop that runs one experiment
     at a time. Gives you the final value of the counter and also the 
     expected value, program terminates when the user enters x <=0 as
     number of threads. Main method also creates and starts the threads,
     then runs them till they finish.
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        
        while (true) {
            
            System.out.print("Threads to run?");
            int threadsGiven = scan.nextInt();
            if (threadsGiven <= 0) 
                break;
            
            do {
                System.out.println("How many times should each thread increment the counter? ");
                increments = scan.nextInt();
                if (increments < 1) {
                    System.out.println("The incrementation must be positive.");
                    increments = 1;
                }
            } while (increments <= 0);
            
            System.out.println();
            System.out.println("Using " + threadsGiven + " threads.");
            System.out.println("Each thread will increment the counter " + increments + " times.");
            System.out.println();
            
            /*
            Incrementer[] creates the threads in variable 'pointers'
            then starts those threads using the .start()in for loop.
             */
            
            Incrementer[] pointers = new Incrementer[threadsGiven];
            counter = new Counter();
            for (int i = 0; i < threadsGiven; i++)
                pointers[i] = new Incrementer();
            for (int i = 0; i < threadsGiven; i++)
                pointers[i].start();
            
            /*
             For loop waits for all threads to terminate in this for loop.
             the '.join' method allows one thread to wait until another thread
             completes its own execution.
             */
            
            for (int i = 0; i < threadsGiven; i++) {
                try {
                    pointers[i].join();
                }
                catch (InterruptedException e) {
                }
            }
            
            System.out.println("Counter's prediction value: "+ (increments*threadsGiven));
            System.out.println("Counter's true value: : " + counter.getCount());
            System.out.println();
            
        }
    }
}