package PolicyManager;

import Invoker.Invoker;

/**
 * Classe que implementa una política de gestió de recursos que es diu RoundRobin.
 * Aquesta política selecciona Invokers en cicle, assignant cada invocació el següent Invoker disponible.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class RoundRobin implements PolicyManager {

    private int UltimInvoker = -1; // Index Invoker
    
    /**
     * Constructor RoundRobin
     */
    public RoundRobin() {
    	//Constructor buit
    }
    
    /**
     * Mètode per seleccionar un Invoker segons la política RoundRobin.
     * @param invokers Llista Invokers disponibles.
     * @return Invoker seleccionat segons la política.
     */
    @Override 
    public Invoker selectInvoker(Invoker[] invokers){
        UltimInvoker = (UltimInvoker + 1) % invokers.length; // Sempre passem al següent
        return invokers[UltimInvoker]; // Retornem invoker escollit
    }
    
    // Getters i Setters

    /**
     * Getter
     * @return UltimInvoker
     */
	public int getUltimInvoker() {
		return UltimInvoker;
	}

	/**
	 * Setter
	 * @param ultimInvoker UltimInvoker
	 */
	public void setUltimInvoker(int ultimInvoker) {
		UltimInvoker = ultimInvoker;
	}
}