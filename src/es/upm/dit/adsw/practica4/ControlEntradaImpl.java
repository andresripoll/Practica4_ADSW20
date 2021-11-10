package es.upm.dit.adsw.practica4;

public class ControlEntradaImpl implements ControlEntrada {

   private boolean vacio;
   private Thread dentro;

   public ControlEntradaImpl() {
      this.vacio=true;
      this.dentro= null;
   }


   @Override
   public synchronized long entrarEnTramo() {
     long t0 = System.currentTimeMillis();

	     if(this.vacio) {
	        this.dentro=Thread.currentThread();
	        this.vacio=false;
	        return -1;
	      } else {
	         if(this.dentro == Thread.currentThread())
	             throw new RuntimeException();
	         else {
	        	 while(!this.vacio) {
	        		 try {
	        			 wait();
	        		 } catch (InterruptedException e) {
	                	// TODO Auto-generated catch block
	        			 e.printStackTrace();
	        		 }
	        	 }
	          }
	      }
	     
	   this.dentro=Thread.currentThread();
	   this.vacio=false;
	   long tfinal=System.currentTimeMillis();

	   return (tfinal - t0);
	}

    @Override
    public synchronized void salirDeTramo() {
	   // TODO Auto-generated method stub
	   if( (this.dentro==null) || (this.dentro!=Thread.currentThread()) )
	        throw new RuntimeException();
	   this.dentro=null;
	   this.vacio=true;
	   notifyAll();

    }
}
