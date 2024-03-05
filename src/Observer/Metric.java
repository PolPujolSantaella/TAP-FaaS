package Observer;

import Invoker.Invoker;

/**
 * Classe que representa una mètrica d'execució, inclou temps d'execució, memòria utilitzada i el Invoker associat.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class Metric {
    private long tempsExe; // Temps Execució
    private int memoryUsed; // Mem. Utilitzada
    private Invoker invoker; // Invoker
    
    /**
     * Constructor de la classe Metric.
     * 
     * @param tempsExe		Temps d'execució de l'acció.
     * @param memoryUsed	Memòria utilitzada durant la execució.
     * @param invoker		Invoker associat a l'execució.
     */
    public Metric(long tempsExe, int memoryUsed, Invoker invoker){
        this.tempsExe=tempsExe;
        this.memoryUsed = memoryUsed;  
        this.invoker=invoker; 
    }
    
    // Getters i Setters

    /**
     * Setter
     * @param tempsExe Temps Execució
     */
    public void setTempsExe(long tempsExe) {
		this.tempsExe = tempsExe;
	}

    /**
     * Setter
     * @param memoryUsed Memòria Utilitzada
     */
	public void setMemoryUsed(int memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	/**
	 * Setter
	 * @param invoker Invoker
	 */
	public void setInvoker(Invoker invoker) {
		this.invoker = invoker;
	}

	/**
	 * Getter
	 * @return tempsExe
	 */
	public long getTempsExe(){
        return tempsExe; 
    }
	
	/**
	 * Getter
	 * @return invoker
	 */
    public Invoker getInvoker(){
        return invoker; 
    }
    
    /**
     * Getter
     * @return memoryUsed
     */
    public int getMemoryUsed(){
        return memoryUsed; 
    }
}