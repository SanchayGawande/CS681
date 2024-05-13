package edu.umb.cs681.hw10;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeBankAccountOptimistic extends ThreadSafeBankAccount {
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    @Override
    public void deposit(double amount) {
        rwLock.writeLock().lock();
        try {
            super.deposit(amount);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public void withdraw(double amount) {
        rwLock.writeLock().lock();
        try {
            super.withdraw(amount);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public double getBalance() {
        rwLock.readLock().lock();
        try {
            return super.getBalance();
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
