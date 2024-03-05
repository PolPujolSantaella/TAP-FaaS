package PolicyManager;

import Invoker.Invoker;

/**
 * Classe que implementa una política de gestió de recursos que es diu UniformGroup.
 * Selecciona, en cicle, Invokers en grups uniformes d'un tamany específic.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class UniformGroup implements PolicyManager {
    private final int groupSize;  // Tamany Grup
    int compt = 0; // Ccomptador
    int invokerEscollit = 0; // index Invoker

    /**
     * Constructor de la poítica UniforGroup
     * 
     * @param groupSize Tamany del grup.
     */
    public UniformGroup (int groupSize){
        this.groupSize = groupSize; 
    }
    
    /**
     * Mètode per seleccionar un Invoker segons la política UniformGroup.
     * 
     * @param invokers  Llista invokers disponibles.
     * @return Invoker seleccionat segons la política.
     */
    @Override
    public Invoker selectInvoker(Invoker[] invokers){
        //Si hem invocat el grup passem al següent
        if (compt == groupSize){
            invokerEscollit = (invokerEscollit + 1) % invokers.length; 
            compt = 0;  // Reiniciem comptador
        }
        
        compt++; // Incrementem comptador

        return invokers[invokerEscollit]; // Retornem invoker escollit 
    }
    
    // Getters i Setters

    /** 
     * Getter
     * @return compt
     */
	public int getCompt() {
		return compt;
	}

	/**
	 * Setter
	 * @param compt Comptador
	 */
	public void setCompt(int compt) {
		this.compt = compt;
	}

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
	
	/**
	 * Getter
	 * @return groupSize
	 */
	public int getGroupSize() {
		return groupSize;
	}
    
    
}