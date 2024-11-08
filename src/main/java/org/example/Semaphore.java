package org.example;

class Semaphore {
    private int value;

    public Semaphore(int initialValue) {
        value = initialValue;
    }

    public synchronized void P() throws InterruptedException {
        while (value <= 0) {
            wait();
        }
        value--;
    }
    public synchronized void V() {
        value++;
        notify();
    }
}
