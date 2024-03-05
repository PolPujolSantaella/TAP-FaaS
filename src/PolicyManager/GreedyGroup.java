package PolicyManager;

import Invoker.Invoker;

/**
 * Classe que implementa una política de gestió de recursos que es diu GreedyGroup.
 * Aquesta política selecciona Invokers de forma "codiciosa", escull sempre el següent Invoker disponible.
 * Si un Invoker està ple, pasa al següent en la llista.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class GreedyGroup implements PolicyManager {
    int invokerEscollit = 0;  // Index Invoker Escollit

    /**
     * Constructor GreedyGroup
     */
    public GreedyGroup() {
    	//Constructor Buit
    }
    /**
     * Mètode per seleccionar un Invoker segons la política GreedyGroup.
     * 
     * @param invokers	Llista Invokers disponibles.
     * @return Invoker selccionat segons la política.
     */
    @Override 
    public Invoker selectInvoker (Invoker[] invokers){
    	// Si Invoker ple, següent
        if (invokers[invokerEscollit].isPle()){
            invokerEscollit = (invokerEscollit + 1) % invokers.length; 
        } 
        return invokers[invokerEscollit];  // Retornem invoker escollit
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
	 * @param invokerEscollit invokerEscollit
	 */
	public void setInvokerEscollit(int invokerEscollit) {
		this.invokerEscollit = invokerEscollit;
	}
    
    
}