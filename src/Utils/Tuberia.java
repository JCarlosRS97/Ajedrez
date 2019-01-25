package Utils;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Tuberia<T> {
    private T movimiento;
    private ReentrantLock lock;
    private Condition isFull;
    private Condition isEmpty;

    public Tuberia() {
        lock = new ReentrantLock();
        isFull = lock.newCondition();
        isEmpty = lock.newCondition();
    }

    public T getMovimiento() {
        T miMovimiento = null;
        lock.lock();
        try {
            while (movimiento == null) {
                isEmpty.await();
            }
            miMovimiento = movimiento;
            movimiento = null;
            isFull.signalAll();

        } catch (InterruptedException e) {
            e.getStackTrace();
        } finally {
            lock.unlock();
        }
        return miMovimiento;
    }

    public void setMovimiento(T nuevoMovimiento) {
        lock.lock();
        try {
            while (movimiento != null) {
                isFull.await();
            }
            movimiento = nuevoMovimiento;
            isEmpty.signalAll();

        } catch (InterruptedException e) {
            e.getStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
