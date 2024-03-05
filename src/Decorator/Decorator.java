package Decorator;

import java.util.function.Function;
import java.util.Map; 

/**
 * Interface que representa un decorador que transforma un mapa de paràmetres a un valor enter.
 * Implementa la interface Function per permetre l'aplicació de la transformació.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */

public interface Decorator extends Function<Map<String, Integer>, Integer> {
    
	/**
     * Aplica la transformació definida per el decorador al mapa de paràmetres proporcionat.
     * 
     * @param params Mapa de paràmetres que s'ha de transformar.
     * @return Resultat de la transformació, representat com un valor enter.
     */
	public Integer apply(Map<String, Integer> params); 
}