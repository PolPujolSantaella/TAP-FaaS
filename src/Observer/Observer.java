package Observer;

import Invoker.Invoker;

/**
 * Interface que defineix el contracte per els Observer que desitgen actualitzacions sobre l'execució d'accions.
 * Els Observers implementaran el mètode 'update' per ser notificats de canvis en els Invokers.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */

public interface Observer {

	/**
	 * Mètode que es crida quan hi ha una actualització en l'execució de una acció en un Invoker.
	 * 
	 * @param tempsExecucio	Temps d'execució de l'acció.
	 * @param memoryUsed	Memòria utilitzada durant l'execució.
	 * @param invoker		Invoker associat a l'execució.
	 */
	public void update (long tempsExecucio, int memoryUsed, Invoker invoker); 
}
