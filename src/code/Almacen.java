package code;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Almacen {
	
	private ArrayList<Integer> products = new ArrayList<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	private final ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
	private final Lock readLock = reentrantReadWriteLock.readLock();
	private final Lock writeLock = reentrantReadWriteLock.writeLock();
	
	public void getProducts(int product) throws InterruptedException {
		readLock.lock();
		try {
			System.out.println(consultProducts(product));
		}
		finally {
			readLock.unlock();
		}
		
	}
	
	private String consultProducts(int product) throws InterruptedException{
		int contador=0;
		for(Integer p : products) {
			if(p == product) {
				contador++;
			}
		}
		return String.format("%s -> Hay %d productos de clase %d", LocalTime.now().format(dateTimeFormatter), contador, product);
	}
	
	public void setProducts() throws InterruptedException {
		writeLock.lock();
		try {
			System.out.println(addProducts(ThreadLocalRandom.current().nextInt(1,4)));
		}
		finally {
			writeLock.unlock();
		}
	}
	
	private String addProducts(int product) throws InterruptedException{
		products.add(product);
		return String.format("%s -> Añadido producto de clase %d", LocalTime.now().format(dateTimeFormatter), product);
	}
	
}
