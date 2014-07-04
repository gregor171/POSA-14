//package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore implementation using
 *        Java a ReentrantLock and a ConditionObject. It must implement both
 *        "Fair" and "NonFair" semaphore semantics, just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
	// private ReentrantLock myLock;
	private final ReentrantLock lock;

    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
    // TODO - you fill in here
	private Condition cPermits;
	 
	 
    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	private volatile int iPermits; // available permits

    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
    	lock= new ReentrantLock(fair);
    	
    	iPermits = permits;
    	
    	cPermits= lock.newCondition(); 	
    	
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here.
    	lock.lockInterruptibly();
    	
        try {
            //  wait if no iPermits
            while (iPermits == 0) {
                cPermits.await();
            }
            iPermits--;
        } finally {
            lock.unlock();
        }
    	
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here.
        lock.lock();
	try {
		while (iPermits == 0) {
	        	cPermits.awaitUninterruptibly();
	        }
	        iPermits--;
	 
	} finally {
		lock.unlock();
	}
   
        
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here.
        
        lock.lock();
	try {
	
		iPermits++;
		cPermits.signal();
	 
	} finally {
		lock.unlock();
	}
       
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
 
    	return iPermits;
    }
}
