#include <stdio.h>
#include <pthread.h>
/*
Global variables to obtain(static)
*/
pthread_cond_t sync1  = PTHREAD_COND_INITIALIZER;
pthread_cond_t sync2  = PTHREAD_COND_INITIALIZER;
pthread_cond_t sync3  = PTHREAD_COND_INITIALIZER;
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
int counter = 1;
void *foo(void *n){
        /*
        While loop implemented so we can get the value of the counter,
        hold(wait) lock on the condition variable.
        */
        while(1) {
                pthread_mutex_lock(&lock);
                if (counter != (int)*(int*)n) {
                        if ((int)*(int*)n == 1) {
                                pthread_cond_wait(&sync1, &lock);
                        } else if ((int)*(int*)n == 2) {
                                pthread_cond_wait(&sync2, &lock);
                        }
                        else {
                                pthread_cond_wait(&sync3, &lock);
                        }
                }
                /*
                If counter = n, then we print the n value.
                */
                printf("%d  ", *(int*)n);
                /*
                Next if statements are to schedule the following thread
                using the 'signal' built-in.
                */
                if (counter == 3) {
                        counter = 1;
                        pthread_cond_signal(&sync1);
                }
                else if(counter == 1) {
                        counter = 2;
                        pthread_cond_signal(&sync2);
                } else if (counter == 2) {
                        counter = 3;
                        pthread_cond_signal(&sync3);
                }
                pthread_mutex_unlock(&lock);
        }
        return NULL;
} 
/*
Function to implement the 3 threads 1,2,3
*/
int main(){
        pthread_t thread1;
        pthread_t thread2;
        pthread_t thread3;
        int n1 = 1;
        int n2 = 2;
        int n3 = 3;

        pthread_create(&thread1, NULL, foo, (void *)&n1);
        pthread_create(&thread2, NULL, foo, (void *)&n2);
        pthread_create(&thread3, NULL, foo, (void *)&n3);
        while(1);
        return 0;
}