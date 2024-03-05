package Reflection;

import java.util.Map;
import java.util.List; 

/**
 * Interface que defineix una acció executable.
 * Proporciona un mètode per executar una acció amb paràmetres i obtenir una llista de resultats.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public interface Action {

	/**
	 * Mètode que executa acció.
	 * 
	 * @param params Paràmetres a utilitzar durant l'execució.
	 * @return Llista resultats de l'acció.
	 */
    List<Integer> execute(List<Map<String, Integer>> params); 
    
}