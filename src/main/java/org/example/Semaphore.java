package org.example;

// This class represents a simple implementation of a semaphore, used to control access to a shared resource.

class Semaphore {
    private int value;

    public Semaphore(int permits) {
        this.value = permits;
    }

    // Acquire a permit
    public synchronized void P() throws InterruptedException {
        while (value == 0) {
            wait(); // Wait until a permit is available
        }

        value--; // Decrease the available permit count
    }

    // Release a permit
    public synchronized void V() {
        value++; // Increase the available permit count
        notify(); // Notify a waiting thread that a permit has become available
    }

    // Method to get current permits for debugging
    public synchronized int getvalue() {
        return value;
    }
}
