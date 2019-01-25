package Utils;

import Logica.Movimiento;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Tuberia {
    private Movimiento movimiento;
    private ReentrantLock lock;
    private Condition isFull;
    private Condition isEmpty;

    public Tuberia() {
        lock = new ReentrantLock();
        isFull = lock.newCondition();
        isEmpty = lock.newCondition();
    }

    public Movimiento getMovimiento() {
        Movimiento miMovimiento = new Movimiento(-1, -1);
        lock.lock();
        try {
            while (movimiento == null) {
                isEmpty.await();
            }
            miMovimiento = new Movimiento(movimiento.getX(), movimiento.getY()); //crea una copia
            movimiento = null;
            isFull.await();

        } catch (InterruptedException e) {
            e.getStackTrace();
        } finally {
            lock.unlock();
        }
        return miMovimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        lock.lock();
        try {
            while (movimiento != null) {
                isFull.await();
            }
            movimiento = new Movimiento(movimiento);
            isEmpty.await();

        } catch (InterruptedException e) {
            e.getStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
