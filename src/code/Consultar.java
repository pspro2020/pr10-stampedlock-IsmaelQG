package code;

public class Consultar implements Runnable{
	
	private final Almacen almacen;
	private final int product;
	
	public Consultar(Almacen almacen, int product) {
		this.almacen=almacen;
		this.product=product;
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			try {
				almacen.getProducts(product);
				Thread.sleep(500);
			} catch (InterruptedException e) {
			
			}
		}
	}

}
