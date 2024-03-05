package PolicyManager;

import Invoker.Invoker;

/**
 * Clase que implementa una política de gestió de recursos que es diu BigGroup.
 * Aquesta política asigna funcions a Invoker en grups grans.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs 
 */
public class BigGroup implements PolicyManager {
    private final int groupSize;  // Tamany Grups
    private int invokerEscollit = 0;  // Index Invoker
    private int funcionsPendents = 0;  // Funcions pendents
    
    /**
     * Constructor de la classe BigGroup.
     * 
     * @param groupSize 	Tamany dels grups.
     * @param numFuncions 	Número de funciona pendents en cada grup.
     */
    public BigGroup(int groupSize, int numFuncions) {
        this.groupSize = groupSize;
        this.funcionsPendents = numFuncions; 
    }

    
    /**
     * Mètode per selecciona un Invoker segons la política BigGroup.
     * 
     * @param invokers Llista Invokers disponibles.
     * @return Invoker seleccionat segons la política.
     */
    @Override
    public Invoker selectInvoker(Invoker[] invokers) {
    	// Si no queden funcions pendents
        if (funcionsPendents == 0){
            funcionsPendents = groupSize; // Reiniciem nou grup
            invokerEscollit = (invokerEscollit + 1) % invokers.length; // Següent Invoker 
        }

        funcionsPendents--; // Decrementem funcions pendents

        return invokers[invokerEscollit]; // Retornem invoker Escollit   
    }

    // Getters i Setters

    /**
     * Getter 
     * @return invokerEscollit
     */
	public int getInvokerEscollit() {
		return invokerEscollit;
	}

	/**
	 * Setter
	 * @param invokerEscollit Invoker escollit
	 */
	public void setInvokerEscollit(int invokerEscollit) {
		this.invokerEscollit = invokerEscollit;
	}


	/**
	 * Getter 
	 * @return funcionPendents
	 */
	public int getFuncionsPendents() {
		return funcionsPendents;
	}

	/**
	 * Setter
	 * @param funcionsPendents  funcionsPendents
	 */
	public void setFuncionsPendents(int funcionsPendents) {
		this.funcionsPendents = funcionsPendents;
	}


	/**
	 * Getter
	 * @return groupSize
	 */
	public int getGroupSize() {
		return groupSize;
	}
    
    
}