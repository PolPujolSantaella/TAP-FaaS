package PolicyManager;

import Invoker.Invoker;

/**
 * Interface que defineix el contracte per els gestors de polítques de recursos.
 * Els implementadors han de proporcionar la lògica per seleccionar un Invoker d'una llista donada.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 *
 */
public interface PolicyManager {
	
	/**
	 * Mètode per seleccionar un Invoker segons la política especificada.
	 * 
	 * @param invokers LLista Invokers disponibles.
	 * @return Invoker seleccionat segons la política.
	 */
    Invoker selectInvoker(Invoker[] invokers); 

}