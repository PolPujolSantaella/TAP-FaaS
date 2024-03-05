package Decorator;

import java.util.function.Function;
import java.util.Map; 

/**
 * Classe que implementa el patró de Decorator per medir el temps d'execució de una funció.
 * Registra el temps abans i després d'aplicar la funció i mostra el temps d'execució.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class TimerDecorator implements Decorator {
    
    private final Function<Map<String, Integer>, Integer> function; // Funció a decorar
    

    /**
     * Constructor de TimerDecorator.
     * 
     * @param function Funció que s'ha de decorar per medir el temps d'execució.
     */
    public TimerDecorator(Function<Map<String, Integer>, Integer> function){
        this.function = function; 
    }

    /**
     * Aplica la decoració per medir el temps d'execució de la funció i mostra el resultat.
     * 
     * @param params Mapa de paràmetres per la funció.
     * @return Resultat de la funció decorada.
     */
    @Override
    public Integer apply(Map<String, Integer> params){

    	// Calculem temps d'execució i retornem
        long start = System.nanoTime(); 
        Integer result = function.apply(params); 
        long end = System.nanoTime(); 

        long tempsExe = end - start; 

        System.out.println("Temps d'execucio: "+ tempsExe + " nanosegons."); 

        return result; 
    }
    
    // Getters i Setters
    
    /**
     * Getter
     * @return function
     */
    public Function<Map<String, Integer>, Integer> getFunction() {
		return function;
	}
    
    
    

}