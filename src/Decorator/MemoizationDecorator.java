package Decorator;

import java.util.Map;
import java.util.function.Function;
import java.util.HashMap; 

/**
 * Classe que implementa el patró de decorador per la memoizació de resultats.
 * Emmagatzema en caché els resultats de la funció aplicada a un conjunt donat per paràmetre.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class MemoizationDecorator implements Decorator {

    private final Function<Map<String, Integer>, Integer> function;  // Funció 
    private final Map<String, Integer> cache = new HashMap<>(); 	 // Caché

    /**
     * Concstructor de MemoizationDecorator.
     * 
     * @param function	Funció que s'ha de decorar i memoizar. 
     */
    public MemoizationDecorator (Function<Map<String, Integer>, Integer> function){
        this.function = function; 
    }
    
    /**
     * Aplica la memoizació al resultat de la funció, emmagatzemant i recuperant resultats desde la caché.
     * 
     * @param params Mapa de paràmetres per la funció.
     * @return Resultat de la funcio memoizada.
     */
    @Override
    public Integer apply (Map<String, Integer> params){
        String key = params.toString(); // Passa els paràmetres a una cadena per utilitzar-la com clau en la caché.

        // Recuperem de la caché sino executem i retornem resultat.
        if (cache.containsKey(key)){
            System.out.println("Recuperant resultat de la cache."); 
            return cache.get(key); 
        } else {
            Integer result = function.apply(params); 
            cache.put(key, result); 
            return result; 
        }
    }
    
    // Getters i Setters

    /**
     * Getter
     * @return function
     */
	public Function<Map<String, Integer>, Integer> getFunction() {
		return function;
	}
	
	/**
	 * Getter
	 * @return cache
	 */
	public Map<String, Integer> getCache() {
		return cache;
	}
    
    
    
    
}