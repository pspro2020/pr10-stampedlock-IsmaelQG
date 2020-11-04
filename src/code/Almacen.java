package code;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class Almacen {
	
	private ArrayList<Integer> products = new ArrayList<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final StampedLock stampedLock = new StampedLock();
	
	public void getProducts(int product) throws InterruptedException {
        long stamp = stampedLock.tryOptimisticRead();
        System.out.println(consultProducts(product, stamp));
	}
	
	private String consultProducts(int product, long stamp) throws InterruptedException{
		int contador=0;
		for(Integer p : products) {
			if(p == product) {
				contador++;
			}
		}
		if (!stampedLock.validate(stamp)) {
			stamp = stampedLock.readLock();
			try {
                contador = 0;
                for(Integer p : products) {
        			if(p == product) {
        				contador++;
        			}
        		}
            } finally {
                stampedLock.unlockRead(stamp);
            }

		}
		
		return String.format("%s -> Hay %d productos de clase %d", LocalTime.now().format(dateTimeFormatter), contador, product);
	}
	
	public void setProducts() throws InterruptedException {
		long stamp = stampedLock.writeLock();
		try {
			System.out.println(addProducts(ThreadLocalRandom.current().nextInt(1,4)));
		}
		finally {
			stampedLock.unlock(stamp);
		}
	}
	
	private String addProducts(int product) throws InterruptedException{
		products.add(product);
		return String.format("%s -> Añadido producto de clase %d", LocalTime.now().format(dateTimeFormatter), product);
	}
	
}
