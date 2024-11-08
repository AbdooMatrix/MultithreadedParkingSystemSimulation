package org.example;

// This class represents a simple implementation of a semaphore, used to control access to a shared resource.
class Semaphore {
    public int value; // The count of permits available, initialized to a specified value.

    // Constructor to initialize the semaphore with a specific number of permits.
    public Semaphore(int initialValue) {
        value = initialValue; // Set the initial value of the semaphore.
    }

    // P() method (also known as "wait" or "acquire") to acquire a permit.
    // This method will block (wait) if there are no permits available (value <= 0).
    public synchronized void P() throws InterruptedException {
        while (value <= 0) {  // If no permits are available, wait until one is released.
            wait(); // Put the current thread in a waiting state until notify() is called.
        }
        value--; // Once a permit is available, decrease the count (acquire the permit).
    }

    // V() method (also known as "signal" or "release") to release a permit.
    // This method increments the permit count and notifies waiting threads that a permit is available.
    public synchronized void V() {
        value++; // Increase the count of permits (release a permit).
        notify(); // Wake up one waiting thread, if any, to acquire the newly available permit.
    }
}
