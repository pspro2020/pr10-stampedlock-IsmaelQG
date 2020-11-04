package code;

import java.util.concurrent.TimeUnit;

public class Añadir implements Runnable{
	
	private final Almacen almacen;
	
	public Añadir(Almacen almacen) {
		this.almacen=almacen;
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			try {
				almacen.setProducts();
				TimeUnit.SECONDS.sleep(2);	
			
			} catch (InterruptedException e) {
			
			}
		}
	}

}
