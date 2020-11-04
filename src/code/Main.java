package code;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Almacen almacen = new Almacen();
		Thread[] consultar = new Thread[3];
		for(int i = 0; i < 3; i++) {
			consultar[i] = new Thread(new Consultar(almacen, i+1), "Consultor " + i+1);
			consultar[i].start();
		}
		Thread a�adir = new Thread(new A�adir(almacen), "A�adir");
		a�adir.start();
		
		TimeUnit.SECONDS.sleep(4);
		
		for(int i = 0; i < 3; i++) {
			consultar[i].interrupt();
		}
		a�adir.interrupt();
		a�adir.join();
	}

}
